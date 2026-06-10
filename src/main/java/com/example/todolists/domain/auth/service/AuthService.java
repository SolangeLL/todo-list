package com.example.todolists.domain.auth.service;

import com.example.todolists.domain.auth.dto.SignInDto;
import com.example.todolists.domain.auth.dto.SignupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseEntity<?> signUp(SignupDto dto);
    ResponseEntity<?> signIn(SignInDto dto);
}
