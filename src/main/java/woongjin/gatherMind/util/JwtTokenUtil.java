package woongjin.gatherMind.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "yourSecretKey";

    public String extractMemberIdFromToken(String token) {
        try {
            // JWT 토큰에서 Claims 객체 추출
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            // Claims 객체에서 "memberId" 필드 추출
            return claims.get("memberId", String.class);
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid JWT token: Signature verification failed");
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }
}

