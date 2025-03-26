package com.example.hotelapplication.repositories;

import com.example.hotelapplication.entities.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChambreRepository extends JpaRepository<Chambre, Long> {
}