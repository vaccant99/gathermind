package woongjin.gatherMind.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.*;

import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.service.AnswerService;
import woongjin.gatherMind.service.MemberService;
import woongjin.gatherMind.service.QuestionService;
import woongjin.gatherMind.service.StudyMemberService;
import java.util.*;


@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final QuestionService questionService;

    private final StudyMemberService studyMemberService;

    private final AnswerService answerService;

    private final JwtTokenProvider jwtTokenProvider;

    // 멤버 역활 같이 조회
    @GetMapping("/role")
    public ResponseEntity<MemberAndStatusRoleDTO> getMemberAndRoleByMemberId(
            @RequestParam Long studyId,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(memberService.getMemberAndRoleByMemberId(request, studyId));
    }

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




    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        boolean isAuthenticated = memberService.authenticate(loginDTO);

        if (isAuthenticated) {
//            String token = jwtUtil.generateToken(loginDTO.getMemberId());
            String token = jwtTokenProvider.createToken(loginDTO.getMemberId());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<MemberDTO> getMemberInfo(HttpServletRequest request) {

//        String memberId = jwtUtil.extractMemberIdFromToken(request);
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        return memberService.getMemberById(memberId)
                .map(member -> ResponseEntity.ok(new MemberDTO(member)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

    }

    // security 적용한 회원 정보 조회
//    @GetMapping("/me")
//    public ResponseEntity<MemberDTO> getCurrentUserInfo(Authentication authentication) {
//
//        if (authentication == null || authentication.getPrincipal() == null) {
//            return ResponseEntity.status(401).body(null); // 인증되지 않은 경우
//        }
//
//        var memberDetails = (MemberDetails) authentication.getPrincipal();
//        String memberId = memberDetails.getUsername(); // memberId를 얻습니다.
//
//        try {
//
//            MemberDTO memberDTO = memberService.getMember(memberId);
//            return ResponseEntity.ok(memberDTO);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(null); // 서비스 처리 중 에러 발생
//        }
//    }

    // 회원 정보 수정 (닉네임과 비밀번호)
    //    @PutMapping("/me")
    @PutMapping("/update")
    public ResponseEntity<String> updateMemberInfo(HttpServletRequest request, @RequestBody Map<String, String> requestBody) {

//        String memberId = jwtUtil.extractMemberIdFromToken(request);
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        String newNickname = requestBody.get("nickname");
        String newPassword = requestBody.get("password");

        return ResponseEntity.ok(memberService.updateMemberInfo(memberId, newNickname, newPassword));
    }

    // 최근에 작성한 게시글(질문) 목록 조회
//    @GetMapping("/me/questions")
    @GetMapping("/recent-questions")
    public ResponseEntity<List<QuestionDTO>> getRecentQuestions(HttpServletRequest request) {
        try {
//            String memberId = jwtUtil.extractMemberIdFromToken(request);
            String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);

            List<QuestionDTO> recentQuestions = questionService.findRecentQuestionsByMemberId(memberId);
            return ResponseEntity.ok(recentQuestions);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // 최근에 작성한 답글 목록 조회
    //    @GetMapping("/me/answers")
    @GetMapping("/recent-answers")
    public ResponseEntity<List<AnswerDTO>> getRecentAnswers(HttpServletRequest request) {
        try {
//            String memberId = jwtUtil.extractMemberIdFromToken(request);
            String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);

            List<AnswerDTO> recentAnswers = memberService.findRecentAnswersByMemberId(memberId);
            return ResponseEntity.ok(recentAnswers);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // 가입한 스터디 수
    @GetMapping("/study-count")
    public ResponseEntity<Long> getStudyCount(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        long count = studyMemberService.countStudiesByMemberId(memberId);
        return ResponseEntity.ok(count);
    }

    // 작성한 질문 수
    @GetMapping("/question-count")
    public ResponseEntity<Long> getQuestionCount(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        long count = questionService.countQuestionsByMemberId(memberId);
        return ResponseEntity.ok(count);
    }

    // 작성한 답변 수
    @GetMapping("/answer-count")
    public ResponseEntity<Long> getAnswerCount(HttpServletRequest request) {
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
        long count = answerService.countAnswersByMemberId(memberId);
        return ResponseEntity.ok(count);
    }


    // 회원 탈퇴
    //    @DeleteMapping("/me")
    @DeleteMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(HttpServletRequest request) {
        try {
            String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);
            memberService.deleteAccount(memberId);
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 탈퇴 중 오류가 발생했습니다.");
        }
    }



    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable String memberId) {
        MemberDTO memberDto = memberService.getMember(memberId);
        return ResponseEntity.ok(memberDto);
    }


    // 회원이 가입한 그룹(스터디) 목록 가져오기
//    @GetMapping("/me/groups")
    @GetMapping("/joined-groups")
    public ResponseEntity<List<StudyDTO>> getJoinedGroups(HttpServletRequest request) {

//        String memberId = jwtUtil.extractMemberIdFromToken(request);
        String memberId = jwtTokenProvider.extractMemberIdFromRequest(request);

        List<StudyDTO> joinedGroups = studyMemberService.findStudiesByMemberId(memberId);
        return ResponseEntity.ok(joinedGroups);

    }

    //회원이 가입한 그룹(스터디) 목록 가져오기
    @GetMapping("/my-studies")
    public ResponseEntity<List<StudyDTO>> getMyGroups() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String memberId = authentication.getName(); // 인증된 사용자 ID (memberId)

            List<StudyDTO> joinedGroups = studyMemberService.getStudiesbyMemberId(memberId);

            return ResponseEntity.ok(joinedGroups);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
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

    // 아이디 중복 확인
    @PostMapping("/check-memberId")
    public ResponseEntity<Map<String, Boolean>> checkMemberId(@RequestBody Map<String, String> request) {
        String memberId = request.get("memberId");
        boolean isUnique = memberService.isMemberIdUnique(memberId);
        return ResponseEntity.ok(Collections.singletonMap("isUnique", isUnique));
    }


    // 닉네임 유효성 검사 메서드
    private boolean isNicknameValid(String nickname) {
        return nickname.length() >= 2 && nickname.length() <= 20 && nickname.matches("^[a-zA-Z0-9가-힣]+$");
    }


}
