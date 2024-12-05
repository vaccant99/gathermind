package woongjin.gatherMind.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import woongjin.gatherMind.entity.EmailVerificationToken;
import woongjin.gatherMind.entity.Member;
import woongjin.gatherMind.repository.EmailVerificationTokenRepository;
import woongjin.gatherMind.repository.MemberRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailVerificationTokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    @Autowired
    private final JavaMailSender mailSender;

    public void  createVerificationToken(Member member, String token) {
        EmailVerificationToken verificationToken = new EmailVerificationToken();
        verificationToken.setToken(token);
        verificationToken.setMember(member);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);
    }

    public void sendVerificationEmail(String email, String token) {
        String link = "http://3.37.250.123/verify-email?token=" + token;

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(email); // Recipient email
            helper.setSubject("Email Verification"); // Email subject
            helper.setText("Please click the link below to verify your email:\n" + link, true); // Email body (true for HTML content)

            // Set custom "From" address
            helper.setFrom("no-reply@gmail.com", "gatherMind"); // Custom sender

            mailSender.send(mimeMessage);

            log.info("Verification email sent to {}", email);
        } catch (Exception e) {
            log.error("Failed to send email to {}", email, e);
            throw new IllegalStateException("Failed to send email", e);
        }
    }

    public boolean verifyToken(String token) {
        EmailVerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new IllegalStateException("Invalid token");
        }
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token has expired");
        }
        Member member = verificationToken.getMember();
        if (member == null) {
            throw new IllegalStateException("No member associated with this token");
        }

        member.setEmailVerified(true);
        memberRepository.save(member);
        return true;
    }
}
