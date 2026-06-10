package com.example.todolists.domain.user.controller;

import com.example.todolists.domain.user.dto.UpdateUserDto;
import com.example.todolists.domain.user.dto.UserResponse;
import com.example.todolists.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserResponse getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PatchMapping
    public UserResponse updateCurrentUser(@RequestBody @Valid UpdateUserDto dto) {
        return userService.updateCurrentUser(dto);
    }
}
