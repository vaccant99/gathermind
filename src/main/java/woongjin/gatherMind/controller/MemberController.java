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
            // 인증된 사용자의 닉네임 가져오기
            String nickname = authentication.getName();

            // DB에서 사용자 정보를 가져옴
            Member member = memberRepository.findByNickname(nickname)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // MemberDto로 변환하여 반환
            return new MemberDto(member.getMemberId(), member.getNickname(), member.getEmail(), member.getCreatedAt());
        } else {
            // 인증되지 않은 사용자에 대해서는 null 또는 빈 객체 반환
            return null;
        }
    }


    }



