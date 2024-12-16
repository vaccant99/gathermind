package woongjin.gatherMind.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
@Configuration
public class MailConfig {


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // 구글 SMTP 서버 설정
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("test"); // 구글 이메일 계정
        mailSender.setPassword("ldefurjjanpjgjri"); // 구글 앱 비밀번호 (2단계 인증 후 생성)

        // SMTP 속성 설정
        mailSender.setJavaMailProperties(getMailProperties());

        return mailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true"); // 인증 필요
        properties.put("mail.smtp.starttls.enable", "true"); // STARTTLS 활성화
        properties.put("mail.debug", "true"); // 디버깅 활성화 (로그 확인용)
        return properties;
    }
}
