package com.example.todolists.domain.auth.service;

import com.example.todolists.domain.auth.dto.SignInDto;
import com.example.todolists.domain.auth.dto.SignupDto;
import com.example.todolists.domain.supabase.service.SupabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final SupabaseService supabaseService;

    public AuthServiceImpl(SupabaseService supabaseService) {
        this.supabaseService = supabaseService;
    }

    public ResponseEntity<?> signUp(SignupDto dto) {
        return supabaseService.signUp(dto);
    }

    public ResponseEntity<?> signIn(SignInDto dto) {
        return supabaseService.signIn(dto);
    }
}
