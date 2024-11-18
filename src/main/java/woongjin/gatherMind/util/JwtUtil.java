package woongjin.gatherMind.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import woongjin.gatherMind.exception.MissingTokenException;
import woongjin.gatherMind.exception.invalid.InvalidTokenException;

@Component
public class JwtUtil {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 적절한 키 생성
    private static final long EXPIRATION_TIME = 86400000; // 만료 시간 (1일)

    // 토큰 생성
    public String generateToken(String memberId) {
        return Jwts.builder()
                .setSubject(memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // 토큰 정보: memberId만 토큰에 포함하는 경우,
    // 클라이언트가 사용자 ID를 식별할 수 있습니다. 필요하다면,
    // 추가 정보(예: role이나 username)를 포함하거나, ID 대신 다른 값으로 설정할 수도 있습니다.
    // 추가 정보를 포함한 토큰 생성 (예: role)
    public String generateTokenWithClaims(String memberId, String nickname, String role) {
        return Jwts.builder()
                .setSubject(memberId)
                .claim("nickname", nickname) // 사용자 이름 추가
                .claim("role", role)         // 역할 정보 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // 토큰에서 memberId 추출
    public String extractMemberId(String token) {
        return getClaims(token).getSubject();
    }

    // 토큰에서 Claims 추출
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 유효성 검증
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token has expired.");
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token format.");
        } catch (Exception e) {
            System.out.println("Invalid token.");
        }
        return false;
    }

    // 요청 헤더에서 Bearer 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

//    HTTP 요청에서 토큰 유효성 검사 후 멤버 ID 추출
    public String extractMemberIdFromToken(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token == null) {
            throw new MissingTokenException("요청 헤더에 토큰이 포함되지 않았습니다.");
        }
        if (!isTokenValid(token)) {
            throw new InvalidTokenException("유효하지 않거나 만료된 토큰입니다.");
        }
        return extractMemberId(token);
    }
}
