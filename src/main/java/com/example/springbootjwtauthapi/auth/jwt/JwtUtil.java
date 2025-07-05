package com.example.springbootjwtauthapi.auth.jwt;

import com.example.springbootjwtauthapi.user.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.rmi.ServerException;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_TIME = 2* 60 * 60 * 1000L; // 2시간

    @Value("${jwt.secret.key}")
    private String secretKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public String createAccessToken(Long userId, String username, Set<UserRole> userRoles) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
        claims.put("userRoles", userRoles.stream().map(Enum::name).toList());
        claims.put("username", username);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME))
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), signatureAlgorithm)
                .compact();
    }

    public String substringToken(String tokenValue) throws ServerException {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        throw new ServerException("토큰이 올바르지 않습니다");
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}