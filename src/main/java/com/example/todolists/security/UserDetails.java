package com.example.todolists.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class UserDetails {
    public Map<String, String> getCurrentUser() {
        return (Map<String, String>) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public UUID getCurrentUserId() {
        String idStr = getCurrentUser().get("userId");
        return UUID.fromString(idStr);
    }

    public String getCurrentUserRole() {
        return getCurrentUser().get("userRole");
    }

    public String getCurrentUserName() {
        return getCurrentUser().get("userName");

    }
}
