package com.example.hotelapplication.repositories;

import com.example.hotelapplication.entities.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    // Optional: Add custom query methods here
    // Example: Find a room by room number
    Chambre findByRoomNumber(String roomNumber);
}