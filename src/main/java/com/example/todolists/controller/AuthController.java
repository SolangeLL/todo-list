package com.example.todolists.controller;

import com.example.todolists.dto.SignInDto;
import com.example.todolists.dto.SignupDto;
import com.example.todolists.service.AuthService;
import com.example.todolists.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    /**
     * Validate the JWT token from frontend
     * Frontend will handle Supabase authentication and send JWT to backend
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // Remove "Bearer "

            if (jwtService.validateToken(token)) {
                String role = jwtService.extractRole(token);
                String userId = jwtService.extractUserId(token);
                String userName = jwtService.extractName(token);

                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("role", role);
                response.put("userId", userId);
                response.put("userName", userName);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(401).body(Map.of("valid", false, "message", "Invalid token"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("valid", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("valid", false, "message", "Token validation failed"));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SignInDto dto) {
        return authService.signin(dto);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupDto dto) {
        return authService.signup(dto);
    }

}