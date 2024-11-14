package woongjin.gatherMind.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class SecurityConfig {



       @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/error",
                                "/h2-console/**",
                                "/study/**",
                                "/basicauth",
                                "/login",
                                 "studymember/**",
                                "member/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                        .disable()
                )

                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/basicauth", true)
                        .permitAll())

                .headers(headers -> headers
                        .defaultsDisabled() // 기본 헤더 설정을 비활성화하고 필요한 헤더만 추가
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // 같은 출처의 frame 허용
                );


        return http.build();
    }
}