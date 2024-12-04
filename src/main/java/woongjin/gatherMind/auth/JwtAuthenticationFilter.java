package woongjin.gatherMind.auth;

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
import woongjin.gatherMind.exception.unauthorized.InvalidTokenException;
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

        try {
            String token = jwtTokenProvider.resolveToken(request);

            if (token != null) {
                jwtTokenProvider.validateToken(token); // 예외가 발생하면 여기서 catch로 넘어감

                String memberId = jwtTokenProvider.getMemberIdFromToken(token);
                var memberDetails = memberService.loadUserByUsername(memberId);

                var authentication = new UsernamePasswordAuthenticationToken(
                        memberDetails, null, memberDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            chain.doFilter(request, response); // 정상적으로 체인을 진행

        } catch (InvalidTokenException e) {
            handleException(response, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            handleException(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    // 헬퍼 메서드 (이전과 동일)
    private void handleException(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.getWriter().write(message);
        response.getWriter().flush();
    }

}
