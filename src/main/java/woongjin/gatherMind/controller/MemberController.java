package woongjin.gatherMind.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woongjin.gatherMind.dto.MemberDto;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto> getMember(@PathVariable String memberId) {

        MemberDto memberDto = memberService.getMember(memberId);
        return ResponseEntity.ok(memberDto);


        }

    @GetMapping("/me")
    public MemberDto getCurrentUserInfo() {
        // SecurityContextHolder에서 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            String nickname = authentication.getName();


            Member member = memberRepository.findByNickname(nickname)
                    .orElseThrow(() -> new RuntimeException("User not found"));


            return new MemberDto(member.getMemberId(), member.getNickname(), member.getEmail(), member.getCreatedAt());
        } else {

            return null;
        }
    }


    }



