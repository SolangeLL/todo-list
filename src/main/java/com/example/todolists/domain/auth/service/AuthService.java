package com.example.todolists.domain.auth.service;

import com.example.todolists.domain.auth.dto.SignInDto;
import com.example.todolists.domain.auth.dto.SignupDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.Map;

@Service
public class AuthService {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.anon.key}")
    private String supabaseAnonKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<?> signup(SignupDto dto) {
        String url = supabaseUrl + "/auth/v1/signup";

        HttpHeaders headers = getHeaders();

        Map<String, String> userData = Map.of(
                "name", dto.getName(),
                "role", dto.getRole()
        );

        Map<String, Object> body = Map.of(
                "email", dto.getEmail(),
                "password", dto.getPassword(),
                "data", userData
        );

        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            return restTemplate.postForEntity(url, entity, Object.class);
        } catch (HttpClientErrorException exception) {
            return ResponseEntity
                    .status(exception.getStatusCode())
                    .body(exception.getResponseBodyAs(Object.class));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(Map.of("message", exception.getMessage()));
        }
    }

    public ResponseEntity<?> signin(SignInDto dto) {
        String url = supabaseUrl + "/auth/v1/token?grant_type=password";

        HttpHeaders headers = getHeaders();

        Map<String, String> body = Map.of(
                "email", dto.getEmail(),
                "password", dto.getPassword()
        );

        try {
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
            return restTemplate.postForEntity(url, entity, Object.class);
        } catch (HttpClientErrorException exception) {
            return ResponseEntity
                    .status(exception.getStatusCode())
                    .body(exception.getResponseBodyAs(Object.class));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(Map.of("message", exception.getMessage()));
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apiKey", supabaseAnonKey);
        return headers;
    }
}
