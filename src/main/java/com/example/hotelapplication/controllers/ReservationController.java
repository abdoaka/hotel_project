package com.example.hotelapplication.controllers;

import com.example.hotelapplication.dto.ReservationRequest;
import com.example.hotelapplication.entities.Reservation;
import com.example.hotelapplication.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    // Create a new reservation
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation createReservation(@Valid @RequestBody ReservationRequest request) {
        return reservationService.createReservation(
                request.getClientId(),
                request.getChambreId(),
                request.getArrivalDate(),
                request.getDepartureDate()
        );
    }

    // Get all reservations
    @GetMapping
    public List<Map<String, Object>> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Get reservation by ID
    @GetMapping("/client/{clientId}")
    public List<Map<String, Object>> getReservationsByClient(@PathVariable Long clientId) {
        return reservationService.getReservationsByClientId(clientId);
    }

    // Update reservation dates
    @PutMapping("/{id}")
    public Reservation updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationRequest request
    ) {
        return reservationService.updateReservation(
                id,
                request.getArrivalDate(),
                request.getDepartureDate()
        );
    }

    // Delete reservation
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}