package woongjin.gatherMind.service;

import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    // 회원가입
    public Member registerMember(MemberDTO memberDto) {
        Member member = new Member();
        member.setMemberId(memberDto.getMemberId());
        member.setNickname(memberDto.getNickname());
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword()); // password 저장
        member.setProfileImage(memberDto.getProfileImage());
        member.setCreatedAt(LocalDateTime.now());
        return memberRepository.save(member);
    }

    // 회원 정보 조회
    public Optional<Member> getMemberById(String memberId) {
        return memberRepository.findById(memberId);
    }

    // 회원 정보 수정
    public Member updateMember(String memberId, MemberDTO memberDto) {
        return memberRepository.findById(memberId).map(member -> {
            member.setNickname(memberDto.getNickname());
            member.setProfileImage(memberDto.getProfileImage());
            return memberRepository.save(member);
        }).orElseThrow(() -> new RuntimeException("Member not found"));
    }

    // 엔티티 -> DTO 변환 (password 제외)
    public MemberDTO convertToDTO(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setMemberId(member.getMemberId());
        dto.setNickname(member.getNickname());
        dto.setEmail(member.getEmail());
        dto.setProfileImage(member.getProfileImage());
        dto.setCreatedAt(member.getCreatedAt());
        // password는 DTO에 포함하지 않음
        return dto;
    }
}
