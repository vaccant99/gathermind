package woongjin.gatherMind.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.DTO.LoginDTO;
import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.DTO.QuestionDTO;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.entity.Question;
import woongjin.gatherMind.repository.AnswerRepository;
import woongjin.gatherMind.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean isMemberIdUnique(String memberId) {
        return !memberRepository.existsByMemberId(memberId);
    }

    public boolean isEmailUnique(String email) {
        return !memberRepository.existsByEmail(email);
    }

    public boolean isNicknameUnique(String nickname) {
        return !memberRepository.existsByNickname(nickname);
    }

    public Member loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("회원 ID를 찾을 수 없습니다: " + memberId));
    }

    // 회원가입
    public void signup(MemberDTO memberDTO) throws Exception {
        if (memberRepository.existsByMemberId(memberDTO.getMemberId())) {
            throw new Exception("이미 사용 중인 아이디입니다.");
        }
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new Exception("이미 사용 중인 이메일입니다.");
        }
        if (memberRepository.existsByNickname(memberDTO.getNickname())) {
            throw new Exception("이미 사용 중인 닉네임입니다.");
        }

        Member member = new Member();
        member.setMemberId(memberDTO.getMemberId());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setEmail(memberDTO.getEmail());
        member.setNickname(memberDTO.getNickname());
        member.setCreatedAt(LocalDateTime.now());
        memberRepository.save(member);
    }

    // 로그인
    public boolean authenticate(LoginDTO loginDTO) {
        Optional<Member> memberOpt = memberRepository.findById(loginDTO.getMemberId());
        return memberOpt.isPresent() && passwordEncoder.matches(loginDTO.getPassword(), memberOpt.get().getPassword());
    }

    // 회원 정보 조회
    public Optional<Member> getMemberById(String memberId) {
        return memberRepository.findById(memberId);
    }

    // 닉네임 가져오기
    public String getNicknameById(String memberId) {
        return memberRepository.findById(memberId)
                .map(Member::getNickname)
                .orElse(null);
    }

    // 닉네임 업데이트
    public void updateNickname(String memberId, String newNickname) {
        memberRepository.findById(memberId).ifPresent(member -> {
            member.setNickname(newNickname);
            memberRepository.save(member);
        });
    }

    // 비밀번호 업데이트
    public void updatePassword(String memberId, String newPassword) {
        memberRepository.findById(memberId).ifPresent(member -> {
            member.setPassword(passwordEncoder.encode(newPassword)); // 암호화하여 저장
            memberRepository.save(member);
        });
    }

    // 회원 탈퇴
    @Transactional
    public void deleteAccount(String memberId) {
        if (memberRepository.existsById(memberId)) {
            memberRepository.deleteById(memberId);
        } else {
            throw new RuntimeException("회원 정보를 찾을 수 없습니다.");
        }
    }

    public List<QuestionDTO> findRecentQuestionsByMemberId(String memberId) {
        List<Question> questions = questionRepository.findTop3ByMemberIdOrderByCreatedAtDesc(memberId);
        return questions.stream()
                .map(QuestionDTO::new)
                .collect(Collectors.toList());
    }

    public List<AnswerDTO> findRecentAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findTop3ByMemberIdOrderByCreatedAtDesc(memberId);
        return answers.stream()
                .map(AnswerDTO::new)
                .collect(Collectors.toList());
    }

    // 엔티티 -> DTO 변환 (password 제외)
    public MemberDTO convertToDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setMemberId(member.getMemberId());
        dto.setNickname(member.getNickname());
        dto.setEmail(member.getEmail());
        dto.setProfileImage(member.getProfileImage());
        dto.setCreatedAt(member.getCreatedAt());
        return dto;
    }
}
