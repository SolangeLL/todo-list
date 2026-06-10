package com.example.todolists.domain.user.service;

import com.example.todolists.domain.supabase.dto.SupabaseUserResponse;
import com.example.todolists.domain.supabase.service.SupabaseService;
import com.example.todolists.domain.user.dto.UpdateUserDto;
import com.example.todolists.domain.user.dto.UserResponse;
import com.example.todolists.security.UserDetails;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final SupabaseService supabaseService;
    private final UserDetails userDetails;
    private final HandlerMapping resourceHandlerMapping;

    UserServiceImpl(SupabaseService supabaseService, UserDetails userDetails, @Nullable HandlerMapping resourceHandlerMapping) {
        this.supabaseService = supabaseService;
        this.userDetails = userDetails;
        this.resourceHandlerMapping = resourceHandlerMapping;
    }

    @Override
    public UserResponse getCurrentUser() {
        String token = userDetails.getToken();
        ResponseEntity<SupabaseUserResponse> supabaseUserResponse = supabaseService.getCurrentUser(token);
        SupabaseUserResponse user = supabaseUserResponse.getBody();

        assert user != null;

        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getMetadata().getName())
                .role(user.getMetadata().getRole())
                .build();
    }

    @Override
    public UserResponse updateCurrentUser(UpdateUserDto dto) {
        String token = userDetails.getToken();
        ResponseEntity<SupabaseUserResponse> supabaseUserResponse = supabaseService.updateCurrentUser(
                token,
                dto.getName(),
                dto.getEmail()
        );

        SupabaseUserResponse user = supabaseUserResponse.getBody();
        assert user != null;

        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getMetadata().getName())
                .role(user.getMetadata().getRole())
                .build();
    }
}
