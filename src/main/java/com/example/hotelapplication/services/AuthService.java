package com.example.hotelapplication.services;

import com.example.hotelapplication.dto.RegistrationRequest;

import com.example.hotelapplication.entities.Client;
import com.example.hotelapplication.entities.User;
import com.example.hotelapplication.exceptions.BadRequestException;
import com.example.hotelapplication.repositories.ClientRepository;
import com.example.hotelapplication.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            ClientRepository clientRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegistrationRequest request) {
        // Check if username is taken
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }

        // Validate role
        if (!request.getRole().equals("ADMIN") && !request.getRole().equals("USER")) {
            throw new BadRequestException("Invalid role");
        }

        // Create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        // For USER role, create client
        if (request.getRole().equals("USER")) {
            validateClientFields(request); // Ensure client fields are provided
            Client client = new Client();
            client.setFirstName(request.getFirstName());
            client.setLastName(request.getLastName());
            client.setEmail(request.getEmail());
            client.setPhone(request.getPhone());
            clientRepository.save(client);
            user.setClient(client);
        }

        return userRepository.save(user);
    }

    private void validateClientFields(RegistrationRequest request) {
        if (request.getFirstName() == null || request.getLastName() == null || request.getEmail() == null) {
            throw new BadRequestException("First name, last name, and email are required for USER role");
        }
    }
}