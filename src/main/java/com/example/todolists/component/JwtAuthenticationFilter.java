package com.example.todolists.component;

import com.example.todolists.service.JwtService;
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
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String personRole = null;
        String personId = null;
        String personName = null;

        // Extract JWT token from Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                personRole = jwtService.extractRole(token);
                personId = jwtService.extractPersonId(token);
                personName = jwtService.extractName(token);
            } catch (Exception e) {
                System.out.println("Erreur extraction JWT: " + e.getMessage()); // ← ajoute ça
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            System.out.println("Pas de header Authorization ou format incorrect"); // ← et ça
        }

        // Validate token and set authentication
        if (personId != null && personName != null && personRole != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Map<String, String> principal = Map.of("personId", personId, "personName", personName);
            if (jwtService.validateToken(token)) {
                // Create authentication object with role
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(principal, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + personRole.toUpperCase())));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}