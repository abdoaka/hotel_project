package com.example.hotelapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private String role; // "ADMIN" or "USER"

    // Client fields (required if role is USER)
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    // Getters and setters
}