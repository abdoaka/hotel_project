package com.example.hotelapplication.entities;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "user")
public class User implements UserDetails { // Implements Spring Security's UserDetails

    /**
     */
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Getter
    @Column(nullable = false)
    private String role; // e.g., "ADMIN", "USER"

    @Getter
    @OneToOne
    @JoinColumn(name = "client_id") // Links to Client entity via foreign key
    private Client client; // Relationship to Client (not a raw ID)

    // Constructor (default + fields)
    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    ///////////// UserDetails Overrides /////////////
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert role string to Spring Security authority (e.g., "ROLE_ADMIN")
        return Collections.singleton((GrantedAuthority) () -> "ROLE_" + role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}