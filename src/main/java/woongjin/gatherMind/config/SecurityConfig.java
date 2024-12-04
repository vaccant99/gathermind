package woongjin.gatherMind.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import woongjin.gatherMind.auth.JwtAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // H2 Console에 대해 CSRF 비활성화
                        .disable()
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/api/member/login",
                                "/api/member/signup",
                                "/api/public/**",
                                "/h2-console/**", // H2 Console 경로 접근 허용
                                "/**",
                                "/error",
                                "/h2-console/**",
                                "/study/**",
                                "/basicauth",
                                "/login",
                                "studymember/**",
                                "member/**",
                                "/check",
                                "/env",
                                "/api/auth/**",
                                "api/member/check-email",
                                "api/member/check-nickname",
                                "api/member/check-memberId",
                                "/api/study/**",
                                "/h2-console/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .defaultsDisabled() // 기본 헤더 설정을 비활성화하고 필요한 헤더만 추가
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // 같은 출처의 frame 허용
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // 새로운 CORS 설정 방식;

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // 프론트엔드 주소
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}