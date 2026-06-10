package com.example.todolists.domain.user.service;

import com.example.todolists.domain.user.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponse getCurrentUser();
}
