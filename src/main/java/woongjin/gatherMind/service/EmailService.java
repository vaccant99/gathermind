package woongjin.gatherMind.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.repository.MemberRepository;


@Service
@RequiredArgsConstructor
public class EmailService {

//    private EmailVerificationTokenRepository tokenRepository;
    private MemberRepository memberRepository;

//    public void  createVerificationToken(Member member, String token) {
//        EmailVerificationToken verificationToken = new EmailVerificationToken();
//        verificationToken.setToken(token);
//        verificationToken.setMember(member);
//        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
//        tokenRepository.save(verificationToken);
//    }
}
