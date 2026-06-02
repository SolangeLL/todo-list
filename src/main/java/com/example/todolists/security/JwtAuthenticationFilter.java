package com.example.todolists.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    private Map<String, String> extractDataFromAuthorizationHeader(String authorizationHeader) {
        Map<String, String> data = new HashMap<>();

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            data.put("token", token);
            data.put("userRole", jwtUtils.extractRole(token));
            data.put("userId", jwtUtils.extractUserId(token));
            data.put("userName", jwtUtils.extractName(token));
        } else {
            System.out.println("Pas de header Authorization ou format incorrect");
        }
        return data;
    }

    private void assertTokenDataAreValid(Map<String, String> data) throws Exception {
        if (data.get("userId") == null || data.get("userName") == null || data.get("userRole") == null)
            throw new Exception("Token data incomplètes");
    }

    private void assertAuthenticationIsEmpty() throws Exception {
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            throw new Exception("Déjà authentifié");
    }

    private void setAuthentication(Map<String, String> data) {
        String userRole = data.get("userRole");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(data, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole.toUpperCase())));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        try {
            Map<String, String> data = extractDataFromAuthorizationHeader(authHeader);
            assertTokenDataAreValid(data);
            assertAuthenticationIsEmpty();
            setAuthentication(data);
        } catch (Exception e) {
            System.out.println("Erreur JWT: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}