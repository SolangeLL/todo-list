package com.example.todolists.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {
    @NotBlank(message = "The email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "The password is required")
    private String password;

    @NotBlank(message = "The name is required")
    private String name;

    @NotBlank(message = "The role is required")
    private String role;
}