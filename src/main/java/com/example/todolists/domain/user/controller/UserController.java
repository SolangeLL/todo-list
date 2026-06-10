package com.example.todolists.domain.user.controller;

import com.example.todolists.domain.user.dto.UserResponse;
import com.example.todolists.domain.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserResponse getCurrentUser() throws NoSuchFieldException {
        return userService.getCurrentUser();
    }
}
