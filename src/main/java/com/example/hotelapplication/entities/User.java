package com.example.hotelapplication.entities;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // e.g., "ADMIN", "USER"

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client; // Optional link to client

    // Getters, setters
}