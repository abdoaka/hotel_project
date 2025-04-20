package com.example.hotelapplication.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference // Fix for bidirectional relationship
    private Client client;

    @ManyToOne
    @JoinColumn(name = "chambre_id", nullable = false)
    @JsonBackReference // Fix for bidirectional relationship
    private Chambre chambre;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @AssertTrue(message = "Departure date must be after arrival date")
    private boolean isValidDates() {
        return departureDate.isAfter(arrivalDate);
    }
}