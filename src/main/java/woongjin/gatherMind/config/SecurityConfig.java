package woongjin.gatherMind.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private  JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic((basic) -> basic.disable())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/**", "/api/members/**").permitAll()
                )
                .csrf((csrf) -> csrf.disable())
//                .csrf((csrf) -> csrf
//                        .ignoringRequestMatchers(new AntPathRequestMatcher
//                                ("/h2-console/**")))
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
                        )))
                .formLogin((form) -> form
                        .loginProcessingUrl("/api/members/login") // 커스텀 로그인 엔드포인트 설정
                        .usernameParameter("memberId") // 아이디 파라미터 이름 설정 (필요 시 변경)
                        .passwordParameter("password") // 비밀번호 파라미터 이름 설정
                        .defaultSuccessUrl("/mypage") // 로그인 성공 시 리다이렉트할 URL
                        .failureUrl("/login?error") // 로그인 실패 시 리다이렉트할 URL
                        .permitAll()
                );
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
