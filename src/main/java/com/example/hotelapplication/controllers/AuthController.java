package com.example.hotelapplication.controllers;

import com.example.hotelapplication.dto.RegistrationRequest;
import com.example.hotelapplication.entities.User;
import com.example.hotelapplication.repositories.UserRepository;
import com.example.hotelapplication.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    public AuthController(AuthService authService,UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    // Register a new user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@Valid @RequestBody RegistrationRequest request) {
        return authService.registerUser(request);
    }

    // Get current authenticated user
    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}