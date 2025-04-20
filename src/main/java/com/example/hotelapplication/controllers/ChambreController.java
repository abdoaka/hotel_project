package com.example.hotelapplication.controllers;

import com.example.hotelapplication.entities.Chambre;
import com.example.hotelapplication.services.ChambreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/chambres")
public class ChambreController {

    private final ChambreService chambreService;

    public ChambreController(ChambreService chambreService) {
        this.chambreService = chambreService;
    }

    // Get all rooms
    @GetMapping
    public List<Chambre> getAllChambres() {
        return chambreService.getAllChambres();
    }

    // Get a room by ID
    @GetMapping("/{id}")
    public Chambre getChambreById(@PathVariable Long id) {
        return chambreService.getChambreById(id);
    }

    // Create a new room
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Chambre createChambre(@Valid @RequestBody Chambre chambre) {
        return chambreService.saveChambre(chambre);
    }

    // Update a room
    @PutMapping("/{id}")
    public Chambre updateChambre(
            @PathVariable Long id,
            @Valid @RequestBody Chambre updatedChambre
    ) {
        return chambreService.updateChambre(id, updatedChambre);
    }

    // Delete a room
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChambre(@PathVariable Long id) {
        chambreService.deleteChambre(id);
    }
}