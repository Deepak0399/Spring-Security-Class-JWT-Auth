package com.practice.demo3jwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtToken {

    private static final long JWT_VALIDITY = (6 * 60 * 60) * 1000;
    private static String getSecretKey() {
        SecretKey key = Jwts.SIG.HS512.key().build();
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    private static final String SECRET_KEY = getSecretKey();
    public String generateToken(UserDetails userDetails) {

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(Object::toString)
                .toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_VALIDITY))
                .signWith(getKey(), Jwts.SIG.HS512)
                .compact();
    }
    private SecretKey getKey() {
        byte[] bytes = SECRET_KEY.getBytes();
        SecretKey keys = Keys.hmacShaKeyFor(bytes);
        return keys;
    }

    private Jws<Claims> getAllClaimsFromToken(String token) {
        JwtParserBuilder parser = Jwts.parser();
        Jws<Claims> claims = parser.verifyWith(getKey()).build().parseSignedClaims(token);
        return claims;
    }
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getPayload().getSubject();
    }
    public boolean isTokenExpired(String token) {
        Date expiration = getAllClaimsFromToken(token).getPayload().getExpiration();
        return expiration.before(new Date());
    }
}
