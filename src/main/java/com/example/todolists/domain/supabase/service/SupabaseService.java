package com.example.todolists.domain.supabase.service;

import com.example.todolists.domain.auth.dto.SignInDto;
import com.example.todolists.domain.auth.dto.SignupDto;
import com.example.todolists.domain.supabase.dto.SupabaseUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SupabaseService {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.anon.key}")
    private String supabaseAnonKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<?> post(String path, Object body, HttpHeaders headers) {
        try {
            HttpEntity<?> entity = new HttpEntity<>(body, headers);
            return restTemplate.postForEntity(supabaseUrl + path, entity, Object.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAs(Object.class));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    private <T> ResponseEntity<T> get(String path, HttpHeaders headers, Class<T> responseType) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            return restTemplate.exchange(supabaseUrl + path, HttpMethod.GET, entity, responseType);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAs(responseType));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body((T) Map.of("message", e.getMessage()));
        }
    }

    private <T> ResponseEntity<T> put(String path, Object body, HttpHeaders headers, Class<T> responseType) {
        try {
            HttpEntity<?> entity = new HttpEntity<>(body, headers);
            return restTemplate.exchange(supabaseUrl + path, HttpMethod.PUT, entity, responseType);
        } catch (HttpClientErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal error while calling Supabase API", e);
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", supabaseAnonKey);
        return headers;
    }

    public ResponseEntity<?> signUp(SignupDto dto) {
        Map<String, Object> body = Map.of(
                "email", dto.getEmail(),
                "password", dto.getPassword(),
                "data", Map.of("name", dto.getName(), "role", dto.getRole())
        );
        return post("/auth/v1/signup", body, buildHeaders());
    }

    public ResponseEntity<?> signIn(SignInDto dto) {
        Map<String, String> body = Map.of(
                "email", dto.getEmail(),
                "password", dto.getPassword()
        );
        return post("/auth/v1/token?grant_type=password", body, buildHeaders());
    }

    public ResponseEntity<SupabaseUserResponse> getCurrentUser(String token) {
        HttpHeaders headers = buildHeaders();
        headers.set("Authorization", "Bearer " + token);
        return get("/auth/v1/user", headers, SupabaseUserResponse.class);
    }

    public ResponseEntity<SupabaseUserResponse> updateCurrentUser(
            String token,
            Optional<String> email,
            Optional<String> name
    ) {
        HttpHeaders headers = buildHeaders();
        headers.set("Authorization", "Bearer " + token);

        Map<String, Object> body = new HashMap<>();
        email.ifPresent(e -> body.put("email", e));
        name.ifPresent(n -> body.put("data", Map.of("name", n)));

        return put("/auth/v1/user", body, headers, SupabaseUserResponse.class);
    }
}
