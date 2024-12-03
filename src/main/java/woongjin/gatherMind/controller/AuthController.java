package woongjin.gatherMind.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woongjin.gatherMind.DTO.LoginDTO;
import woongjin.gatherMind.DTO.MemberDTO;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.MemberRepository;
import woongjin.gatherMind.service.EmailService;
import woongjin.gatherMind.service.MemberService;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final EmailService emailService;

    /**
     * 로그인 처리
     */
    @Operation(summary = "로그인", description = "회원 로그인을 처리하고 JWT 토큰을 반환합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(Collections.singletonMap("token", memberService.authenticate(loginDTO)));
    }


    /**
     * 회원가입 처리
     */
    @Operation(summary = "회원가입", description = "회원가입을 처리합니다.")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO memberDTO) {
        memberService.signup(memberDTO); // 회원가입 처리
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("회원가입이 완료되었습니다."); // 성공 메시지 반환

    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String token = jwtTokenProvider.createToken(authentication.getName());
//        return ResponseEntity.ok(new JwtResponse(token));
//    }

//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody Member member) {
//        memberService.signup(memberDTO);
//        Member savedMember = memberService.signup(member);
//
//        String token = UUID.randomUUID().toString();
//        emailService.createVerificationToken(savedMember, token);
//
//        return ResponseEntity.ok("Registration successful. Please check your email for verification.");
//    }
//
//    @GetMapping("/verify-email")
//    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
//        boolean isVerified = emailService.verifyToken(token);
//        if (isVerified) {
//            return ResponseEntity.ok("Email verified successfully!");
//        }
//        return ResponseEntity.badRequest().body("Invalid or expired token.");
//    }
}


