package com.practice.demo3jwt.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
