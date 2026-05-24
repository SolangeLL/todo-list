package com.example.todolists.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${supabase.jwt.secret}")
    private String jwtSecret;

    @PostConstruct
    public void init() {
        System.out.println("JwtService initialized with secret: " + (jwtSecret != null ? "LOADED" : "NULL"));
        System.out.println("JWT Secret value: " + jwtSecret);
    }


    public String extractName(String token){
        return extractClaim(token, claims -> {
            HashMap<?, ?> userMetadata = claims.get("user_metadata", HashMap.class);
            if (userMetadata != null) {
                return userMetadata.get("name").toString();
            }
            return null;
        });
    }

    public String extractRole(String token){
        return extractClaim(token, claims -> {
            HashMap<?, ?> userMetadata = claims.get("user_metadata", HashMap.class);
            if (userMetadata != null) {
                return userMetadata.get("role").toString();
            }
            return null;
        });
    }

    public String extractPersonId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
//                .verifyWith((javax.crypto.SecretKey) getSignKey())
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Key getSignKey() {
//        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
//        return Keys.hmacShaKeyFor(keyBytes);
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}