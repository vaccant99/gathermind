package woongjin.gatherMind.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.dto.MemberDto;

import woongjin.gatherMind.entity.Member;

import woongjin.gatherMind.repository.MemberRepository;




@Service
public class MemberService {


    @Autowired
    MemberRepository memberRepository;


    public MemberDto getMember(String memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));


        return new MemberDto(
                member.getMemberId(),
                member.getNickname(),
                member.getEmail(),
                member.getCreatedAt()
        );
    }

}