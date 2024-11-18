package woongjin.gatherMind.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import woongjin.gatherMind.config.JwtTokenProvider;
import woongjin.gatherMind.service.MemberService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, MemberService memberService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            System.out.println("Token received: " + token);
        } else {
            System.out.println("No token received");
        }

        if (token != null && jwtTokenProvider.validateToken(token)) {
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
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
