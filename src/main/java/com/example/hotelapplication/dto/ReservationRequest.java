package com.example.hotelapplication.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotNull(message = "Room ID is required")
    private Long chambreId;

    @Future(message = "Arrival date must be in the future")
    private LocalDate arrivalDate;

    @Future(message = "Departure date must be in the future")
    private LocalDate departureDate;

    // Getters and setters
}