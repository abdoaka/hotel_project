package com.example.hotelapplication.repositories;

import com.example.hotelapplication.entities.Client;
import com.example.hotelapplication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}

