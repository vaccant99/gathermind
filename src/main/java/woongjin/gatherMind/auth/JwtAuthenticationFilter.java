package woongjin.gatherMind.auth;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.service.MemberService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null) {
            System.out.println("Token received: " + token);
            try {
                if (jwtTokenProvider.validateToken(token)) {
                    String memberId = jwtTokenProvider.getMemberIdFromToken(token);
                    System.out.println("Authenticated member ID: " + memberId);
                    var memberDetails = memberService.loadUserByUsername(memberId);
                    System.out.println("Extracted Member ID from JWT: " + memberDetails);

                    var authentication = new UsernamePasswordAuthenticationToken(
                            memberDetails, null, memberDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    System.out.println("Authentication successful for member: " + memberId);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                // JWT가 만료된 경우 401 Unauthorized 응답을 반환
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                response.getWriter().write("Token has expired");
                response.getWriter().flush();
                return;
            }
        } else {
            System.out.println("No token received");
        }
        chain.doFilter(request, response);
    }

}
