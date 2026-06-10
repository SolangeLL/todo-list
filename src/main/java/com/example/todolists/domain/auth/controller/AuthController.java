package com.example.todolists.domain.auth.controller;

import com.example.todolists.domain.auth.dto.SignInDto;
import com.example.todolists.domain.auth.dto.SignupDto;
import com.example.todolists.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid SignInDto dto) {
        return authService.signIn(dto);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupDto dto) {
        return authService.signUp(dto);
    }

}