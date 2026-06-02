package com.example.todolists.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInDto {
    @NotBlank(message = "The email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "The password is required")
    private String password;
}
