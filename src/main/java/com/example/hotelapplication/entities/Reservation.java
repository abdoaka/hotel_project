package com.example.hotelapplication.entities;



import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "chambre_id", nullable = false)
    private Chambre chambre;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    // Getters, setters, constructor
    public Reservation() {

    }
    public Reservation(Long id, Client client, Chambre chambre, LocalDate arrivalDate, LocalDate departureDate) {
        this.id = id;
        this.client = client;
        this.chambre = chambre;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
    }
}
