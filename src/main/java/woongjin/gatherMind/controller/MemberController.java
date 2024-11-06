package woongjin.gatherMind.controller;

import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public MemberDTO registerMember(@RequestBody MemberDTO memberDto) {
        Member member = memberService.registerMember(memberDto);
        return memberService.convertToDto(member);
    }

    // 회원 정보 조회
    @GetMapping("/{memberId}")
    public MemberDTO getMemberById(@PathVariable String memberId) {
        Member member = memberService.getMemberById(memberId).orElse(null);
        return member != null ? memberService.convertToDTO(member) : null;
    }

    // 회원 정보 수정
    @PutMapping("/{memberId}")
    public MemberDTO updateMember(@PathVariable String memberId, @RequestBody MemberDTO memberDto) {
        Member member = memberService.updateMember(memberId, memberDto);
        return memberService.convertToDTO(member);
    }
}
