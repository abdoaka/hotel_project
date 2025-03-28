package com.example.hotelapplication.entities;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Generates default constructor
@AllArgsConstructor // Generates constructor with all fields
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