package com.example.todolists.domain.user.service;

import com.example.todolists.domain.supabase.dto.SupabaseUserResponse;
import com.example.todolists.domain.supabase.service.SupabaseService;
import com.example.todolists.domain.user.dto.UserResponse;
import com.example.todolists.security.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final SupabaseService supabaseService;
    private final UserDetails userDetails;

    UserService(SupabaseService supabaseService, UserDetails userDetails) {
        this.supabaseService = supabaseService;
        this.userDetails = userDetails;
    }

    public UserResponse getCurrentUser() {
        String token = userDetails.getToken();
        ResponseEntity<SupabaseUserResponse> supabaseUserResponse = supabaseService.getCurrentUser(token);
        SupabaseUserResponse user = supabaseUserResponse.getBody();

        System.out.println("user: " + user);

        assert user != null;
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getMetadata().getName())
                .role(user.getMetadata().getRole())
                .build();
    }
}
