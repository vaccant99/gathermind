package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.MemberAndStatusRoleDTO;

import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.repository.MemberRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.DTO.AnswerDTO;
import woongjin.gatherMind.DTO.LoginDTO;
import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.auth.MemberDetails;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.AnswerRepository;

import woongjin.gatherMind.repository.QuestionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberAndStatusRoleDTO getMemberAndRoleByMemberId(String memberId, Long studyId) {

        return memberRepository.findMemberAndRoleByMemberId(memberId, studyId)
                .orElseThrow(() -> new MemberNotFoundException("member id : " + memberId + " not found"));
    }


    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("회원 ID를 찾을 수 없습니다: " + memberId));
        return new MemberDetails(member);
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

    // 회원가입
    public void signup(MemberDTO memberDTO) throws Exception {
        if (!isMemberIdUnique(memberDTO.getMemberId())) {
            throw new Exception("이미 사용 중인 아이디입니다.");
        }
        if (!isEmailUnique(memberDTO.getEmail())) {
            throw new Exception("이미 사용 중인 이메일입니다.");
        }
        if (!isNicknameUnique(memberDTO.getNickname())) {
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
        return memberRepository.findById(loginDTO.getMemberId())
                .map(member -> passwordEncoder.matches(loginDTO.getPassword(), member.getPassword()))
                .orElse(false);
    }

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
    @Transactional
    public void updateNickname(String memberId, String newNickname) {
        memberRepository.findById(memberId).ifPresent(member -> {
            member.setNickname(newNickname);
            memberRepository.save(member);
        });
    }

    // 비밀번호 업데이트
    @Transactional
    public void updatePassword(String memberId, String newPassword) {
        memberRepository.findById(memberId).ifPresent(member -> {
            member.setPassword(passwordEncoder.encode(newPassword));
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

    public List<AnswerDTO> findRecentAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findTop3ByMemberIdOrderByCreatedAtDesc(memberId);
        return answers.stream()
                .map(AnswerDTO::new)
                .collect(Collectors.toList());
    }

    public MemberDTO convertToDTO(Member member) {
        return new MemberDTO(member.getMemberId(), member.getNickname(), member.getEmail(), member.getProfileImage(), member.getCreatedAt());
    }
}
