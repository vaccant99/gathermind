package woongjin.gatherMind.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {


    private final SecretKey secretKey;


    public JwtTokenProvider() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // 강력한 SecretKey 생성
    }


    @Value("${jwt.expiration}")
    private long validityInMilliseconds;


    public String createToken(String memberId) {
        Claims claims = Jwts.claims().setSubject(memberId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public String getMemberIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();


    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            if (e instanceof io.jsonwebtoken.ExpiredJwtException) {
                System.out.println("Token has expired");
            } else if (e instanceof io.jsonwebtoken.SignatureException) {
                System.out.println("Signature does not match");
            } else {
                System.out.println("Token validation failed: " + e.getMessage());
            }
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Token is null or empty");
            return false;
        }
    }
}
