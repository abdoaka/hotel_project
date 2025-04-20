package com.example.hotelapplication.services;

import com.example.hotelapplication.entities.Chambre;
import com.example.hotelapplication.exceptions.ResourceNotFoundException;
import com.example.hotelapplication.repositories.ChambreRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChambreService {
    private final ChambreRepository chambreRepository;

    public ChambreService(ChambreRepository chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    // Basic CRUD operations
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }

    public Chambre saveChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    public Chambre getChambreById(Long id) {
        return chambreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + id));
    }

    public void deleteChambre(Long id) {
        chambreRepository.deleteById(id);
    }

    // Inside ChambreService.java
    public Chambre updateChambre(Long id, Chambre updatedChambre) {
        // 1. Find existing room
        Chambre existingChambre = chambreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + id));

        // 2. Update fields from the incoming object
        existingChambre.setRoomNumber(updatedChambre.getRoomNumber());
        existingChambre.setType(updatedChambre.getType());
        existingChambre.setPrice(updatedChambre.getPrice());
        existingChambre.setStatus(updatedChambre.getStatus());

        // 3. Save and return updated room
        return chambreRepository.save(existingChambre);
    }
}
