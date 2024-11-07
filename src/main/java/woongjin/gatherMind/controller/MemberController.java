package woongjin.gatherMind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.MemberAndStatusRoleDTO;
import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.service.MemberService;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/role")
    public ResponseEntity<MemberAndStatusRoleDTO> getMemberAndRoleByMemberId(
            @RequestParam String memberId,
            @RequestParam Long studyId
    ) {

        return ResponseEntity.ok( memberService.getMemberAndRoleByMemberId(memberId, studyId));
    }
}
