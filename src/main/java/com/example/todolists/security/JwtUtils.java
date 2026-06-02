package com.example.todolists.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.ECPublicKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtUtils {

    @Value("${supabase.url}/auth/v1")
    private String supabaseUrl;

    private JwkProvider jwkProvider;

    @PostConstruct
    public void init() {
        jwkProvider = new UrlJwkProvider(supabaseUrl);
        System.out.println("JwtService initialized with supabaseUrl: " + supabaseUrl);
    }

    private DecodedJWT decodeAndVerify(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Jwk jwk = jwkProvider.get(jwt.getKeyId());
            ECPublicKey publicKey = (ECPublicKey) jwk.getPublicKey();
            Algorithm algorithm = Algorithm.ECDSA256(publicKey, null);
            return JWT.require(algorithm).acceptLeeway(60).build().verify(token);
        } catch (Exception e) {
            throw new RuntimeException("Token invalide : " + e.getMessage());
        }
    }

    public String extractName(String token) {
        DecodedJWT jwt = decodeAndVerify(token);
        Map<String, Object> userMetadata = jwt.getClaim("user_metadata").asMap();
        if (userMetadata != null) {
            return userMetadata.get("name").toString();
        }
        return null;
    }

    public String extractRole(String token) {
        DecodedJWT jwt = decodeAndVerify(token);
        Map<String, Object> userMetadata = jwt.getClaim("user_metadata").asMap();
        if (userMetadata != null) {
            return userMetadata.get("role").toString();
        }
        return null;
    }

    public String extractUserId(String token) {
        return decodeAndVerify(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return Date.from(decodeAndVerify(token).getExpiresAtAsInstant());
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
}