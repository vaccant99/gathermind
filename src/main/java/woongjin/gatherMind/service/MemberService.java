package woongjin.gatherMind.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.*;

import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.repository.MemberRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import woongjin.gatherMind.auth.MemberDetails;
import woongjin.gatherMind.entity.Answer;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.AnswerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AnswerRepository answerRepository;
    private final PasswordEncoder passwordEncoder;


    public MemberAndStatusRoleDTO getMemberAndRoleByMemberId(String memberId, Long studyId) {

        return memberRepository.findMemberAndRoleByMemberId(memberId, studyId)
                .orElseThrow(() -> new MemberNotFoundException("Member id : " + memberId + " not found"));
    }




    // 회원가입
    @Transactional
    public void signup(MemberDTO memberDTO3) {
        validateUniqueMember(memberDTO3);

        Member member = new Member();
        member.setMemberId(memberDTO3.getMemberId());
        member.setPassword(passwordEncoder.encode(memberDTO3.getPassword()));
        member.setEmail(memberDTO3.getEmail());
        member.setNickname(memberDTO3.getNickname());
        member.setCreatedAt(LocalDateTime.now());
        memberRepository.save(member);
    }

    public Member findByNickname(String nickname) {

        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("User not found"));
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

    public MemberDTO getMember(String memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));


        return new MemberDTO(
                member.getMemberId(),
                member.getNickname(),
                member.getEmail(),
                member.getProfileImage(),
                member.getCreatedAt()
        );
    }

    // 닉네임 가져오기
    public String getNicknameById(String memberId) {
        return memberRepository.findById(memberId)
                .map(Member::getNickname)
                .orElseThrow(() -> new MemberNotFoundException("Member ID: " + memberId + " not found"));
    }

    // 닉네임 업데이트
    @Transactional
    public void updateNickname(String memberId, String newNickname) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member ID: " + memberId + " not found"));
        member.setNickname(newNickname);
        memberRepository.save(member);
    }

    // 비밀번호 업데이트
    @Transactional
    public void updatePassword(String memberId, String newPassword) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member ID: " + memberId + " not found"));
        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteAccount(String memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException("Member ID: " + memberId + " not found");
        }
        memberRepository.deleteById(memberId);
    }

    public List<AnswerDTO> findRecentAnswersByMemberId(String memberId) {
        List<Answer> answers = answerRepository.findTop3ByMemberIdOrderByCreatedAtDesc(memberId);
        return answers.stream()
                .map(AnswerDTO::new)
                .collect(Collectors.toList());
    }

    private void validateUniqueMember(MemberDTO memberDTO3) {
        if (!isMemberIdUnique(memberDTO3.getMemberId())) {
            throw new IllegalArgumentException("ID is already in use.");
        }
        if (!isEmailUnique(memberDTO3.getEmail())) {
            throw new IllegalArgumentException("Email is already in use.");
        }
        if (!isNicknameUnique(memberDTO3.getNickname())) {
            throw new IllegalArgumentException("Nickname is already in use.");
        }
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

    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("회원 ID를 찾을 수 없습니다: " + memberId));
        return new MemberDetails(member);
    }

    public MemberDTO convertToDTO(Member member) {
        return new MemberDTO(member.getMemberId(), member.getNickname(), member.getEmail(), member.getProfileImage(), member.getCreatedAt());
    }


}
