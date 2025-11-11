package com.fullStack.to_do.list.config;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JWTGenerator {

    // نفس المفتاح كما هو عندك
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // لوج: يطبع iat/exp ومدة الصلاحية
        log.info("[JWT-GEN] iat={}, exp={}, ttl={}s",
                Instant.ofEpochMilli(now.getTime()),
                Instant.ofEpochMilli(expireDate.getTime()),
                (expireDate.getTime() - now.getTime()) / 1000);

        System.out.println("New token :");
        System.out.println(token);
        return token;
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // لوج: يطبع iat/exp عند القراءة
        log.debug("[JWT-CLAIMS] iat={}, exp={}",
                Instant.ofEpochMilli(claims.getIssuedAt().getTime()),
                Instant.ofEpochMilli(claims.getExpiration().getTime()));

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims c = jws.getBody();
            long now = System.currentTimeMillis();
            long diffMs = now - c.getExpiration().getTime(); // لو موجب = متأخر عن الانتهاء

            log.info("[JWT-VAL] now={}, iat={}, exp={}, diff(now-exp)={}ms",
                    Instant.ofEpochMilli(now),
                    Instant.ofEpochMilli(c.getIssuedAt().getTime()),
                    Instant.ofEpochMilli(c.getExpiration().getTime()),
                    diffMs);

            return true;
        } catch (ExpiredJwtException e) {
            Claims c = e.getClaims();
            long now = System.currentTimeMillis();
            long lateMs = now - c.getExpiration().getTime();

            // هنا يبان لك بالمللي ثانية كم تأخرت عن الانتهاء (مثال: ~4000ms)
            log.warn("[JWT-VAL] EXPIRED -> now={}, iat={}, exp={}, lateBy={}ms",
                    Instant.ofEpochMilli(now),
                    c.getIssuedAt() != null ? Instant.ofEpochMilli(c.getIssuedAt().getTime()) : null,
                    c.getExpiration() != null ? Instant.ofEpochMilli(c.getExpiration().getTime()) : null,
                    lateMs);

            // نفس منطقك الحالي: نرمي الاستثناء
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect", e.fillInStackTrace());
        } catch (JwtException | IllegalArgumentException ex) {
            log.warn("[JWT-VAL] INVALID TOKEN: {}", ex.getMessage());
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect", ex.fillInStackTrace());
        }
    }
}
