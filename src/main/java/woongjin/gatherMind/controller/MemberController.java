package woongjin.gatherMind.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import woongjin.gatherMind.DTO.*;
import woongjin.gatherMind.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.util.JwtUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private StudyService studyService;

    @Autowired
    private StudyMemberService studyMemberService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private JwtUtil jwtUtil;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberDTO memberDto) {
        try {
            memberService.signup(memberDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입에 실패했습니다.");
        }
    }

    // 아이디 중복 확인
    @PostMapping("/check-memberId")
    public ResponseEntity<Map<String, Boolean>> checkMemberId(@RequestBody Map<String, String> request) {
        String memberId = request.get("memberId");
        boolean isUnique = memberService.isMemberIdUnique(memberId);
        return ResponseEntity.ok(Collections.singletonMap("isUnique", isUnique));
    }

    // 이메일 중복 확인
    @PostMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean isUnique = memberService.isEmailUnique(email);
        return ResponseEntity.ok(Collections.singletonMap("isUnique", isUnique));
    }

    // 닉네임 중복 확인
    @PostMapping("/check-nickname")
    public ResponseEntity<Map<String, Boolean>> checkNickname(@RequestBody Map<String, String> request) {
        String nickname = request.get("nickname");
        boolean isUnique = memberService.isNicknameUnique(nickname);
        return ResponseEntity.ok(Collections.singletonMap("isUnique", isUnique));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            boolean isAuthenticated = memberService.authenticate(loginDTO);
            if (isAuthenticated) {
                String token = jwtUtil.generateToken(loginDTO.getMemberId());
                Map<String, String> responseBody = Map.of("token", token);
                return ResponseEntity.ok(responseBody); // 200 OK와 함께 토큰 반환
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패"); // 401 Unauthorized
            }
        } catch (Exception e) {
            System.out.println("서버 오류: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생"); // 500 Internal Server Error
        }
    }

    // 회원 정보 조회
    @GetMapping("/me")
    public ResponseEntity<MemberDTO> getMemberInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        token = token.substring(7);

        if (jwtUtil.isTokenValid(token)) {
            String memberId = jwtUtil.extractMemberId(token);
            return memberService.getMemberById(memberId)
                    .map(member -> ResponseEntity.ok(new MemberDTO(member)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // 회원 정보 수정 (닉네임과 비밀번호)
    @PutMapping("/update")
    public ResponseEntity<String> updateMemberInfo(HttpServletRequest request, @RequestBody Map<String, String> requestBody) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 없습니다.");
        }

        token = token.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }

        String memberId = jwtUtil.extractMemberId(token);
        String newNickname = requestBody.get("nickname");
        String newPassword = requestBody.get("password");

        StringBuilder successMessage = new StringBuilder();

        // 현재 사용자의 기존 닉네임 가져오기
        String originalNickname;
        try {
            originalNickname = memberService.getNicknameById(memberId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보를 가져오는 중 오류가 발생했습니다.");
        }

        // 닉네임 유효성 검사 및 업데이트
        if (newNickname != null && !newNickname.equals(originalNickname)) {
            if (!isNicknameValid(newNickname)) {
                return ResponseEntity.badRequest().body("닉네임은 2자에서 20자 사이여야 하며 특수 문자를 포함할 수 없습니다.");
            }
            if (!memberService.isNicknameUnique(newNickname)) {
                return ResponseEntity.badRequest().body("이미 사용 중인 닉네임입니다.");
            }
            try {
                memberService.updateNickname(memberId, newNickname);
                successMessage.append(originalNickname).append("님의 닉네임이 ").append(newNickname).append("으로 변경되었습니다.\n");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("닉네임 변경 중 오류가 발생했습니다.");
            }
        }

        // 비밀번호 유효성 검사 및 업데이트
        if (newPassword != null && !newPassword.isEmpty()) {
            if (!isPasswordValid(newPassword)) {
                return ResponseEntity.badRequest().body("비밀번호는 8자 이상 255자 이하로 입력해야 하며 공백을 포함할 수 없습니다.");
            }
            try {
                memberService.updatePassword(memberId, newPassword);
                successMessage.append((newNickname != null ? newNickname : originalNickname))
                        .append("님의 비밀번호가 안전하게 변경되었습니다.\n");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 변경 중 오류가 발생했습니다.");
            }
        }

        return successMessage.length() > 0 ? ResponseEntity.ok(successMessage.toString().trim())
                : ResponseEntity.ok("수정된 내용이 없습니다.");
    }

    // 닉네임 유효성 검사 메서드
    private boolean isNicknameValid(String nickname) {
        return nickname.length() >= 2 && nickname.length() <= 20 && nickname.matches("^[a-zA-Z0-9가-힣]+$");
    }

    // 비밀번호 유효성 검사 메서드
    private boolean isPasswordValid(String password) {
        return password.length() >= 8 && password.length() <= 255 && !password.contains(" ");
    }

    // 회원이 가입한 그룹(스터디) 목록 가져오기
    @GetMapping("/joined-groups")
    public ResponseEntity<List<StudyDTO>> getJoinedGroups(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        token = token.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String memberId = jwtUtil.extractMemberId(token);
        List<StudyDTO> joinedGroups = studyMemberService.findStudiesByMemberId(memberId);

        return ResponseEntity.ok(joinedGroups);
    }

    // 사용자가 최근에 작성한 게시글(질문) 목록 조회
    @GetMapping("/recent-questions")
    public ResponseEntity<List<QuestionDTO>> getRecentQuestions(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String memberId = jwtUtil.extractMemberId(token);
        List<QuestionDTO> recentQuestions = memberService.findRecentQuestionsByMemberId(memberId);
        return ResponseEntity.ok(recentQuestions);
    }

    // 사용자가 최근에 작성한 답글 목록 조회
    @GetMapping("/recent-answers")
    public ResponseEntity<List<AnswerDTO>> getRecentAnswers(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String memberId = jwtUtil.extractMemberId(token);
        List<AnswerDTO> recentAnswers = memberService.findRecentAnswersByMemberId(memberId);
        return ResponseEntity.ok(recentAnswers);
    }

    // 회원 탈퇴
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 없습니다.");
        }

        token = token.substring(7);

        if (jwtUtil.isTokenValid(token)) {
            String memberId = jwtUtil.extractMemberId(token);
            try {
                memberService.deleteAccount(memberId);
                return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 중 오류가 발생했습니다.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        }
    }
}
