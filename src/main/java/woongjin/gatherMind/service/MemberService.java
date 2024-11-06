package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.exception.member.MemberNotFoundException;
import woongjin.gatherMind.mapper.MemberMapper;
import woongjin.gatherMind.repository.MemberRepository;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getMemberByMeetingId(Long id) {
        return memberRepository.findMemberByMeetingId(id);
    }

    public MemberDTO getMemberById(String id) {

        return memberRepository.findById(id)
                        .map(MemberMapper::convertToMemberDto)
                .orElseThrow(() -> new MemberNotFoundException("member id : " + id + " not found"));
    }
}
