package com.example.hotelapplication.repositories;

import com.example.hotelapplication.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    // Find a client by email (unique field)
    Optional<Client> findByEmail(String email);
}
