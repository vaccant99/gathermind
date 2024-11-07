package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.MemberAndStatusRoleDTO;

import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberAndStatusRoleDTO getMemberAndRoleByMemberId(String memberId, Long studyId) {

        return memberRepository.findMemberAndRoleByMemberId(memberId, studyId)
                .orElseThrow(() -> new MemberNotFoundException("member id : " + memberId +" not found"));
    }
}
