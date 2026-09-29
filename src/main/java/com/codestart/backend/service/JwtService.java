package com.codestart.backend.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long EXPIRATION_TIME;

    public JwtService(@Value("${jwt.secret-key}") String secret,
            @Value("${jwt.expiration-time}") long expirationTime) {
    	this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
        this.EXPIRATION_TIME = expirationTime;
    }

    public String generateToken(String email, Long id) {

        return Jwts.builder()
                .subject(email)
                .claim("id", id)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME
                        )
                )
                .signWith(secretKey)
                .compact();
    }
    
    public boolean isTokenValid(String token) {

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
    public String extractEmail(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}