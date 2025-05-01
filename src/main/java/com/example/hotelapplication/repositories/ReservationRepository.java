package com.example.hotelapplication.repositories;

import com.example.hotelapplication.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Find all reservations for a specific client
    @Query("""
    SELECT r.id, r.arrivalDate, r.departureDate, r.chambre.roomNumber
    FROM Reservation r
    WHERE r.client.id = :clientId
""")
    List<Object[]> findReservationsByClientWithRoomNumber(@Param("clientId") Long clientId);

    // Find all reservations for a specific room
    List<Reservation> findByChambreId(Long chambreId);

    @Query("""
        SELECT COUNT(r) > 0 
        FROM Reservation r 
        WHERE r.chambre.id = :chambreId 
        AND (
            (r.arrivalDate < :departureDate AND r.departureDate > :arrivalDate)
        )
        AND (:excludeReservationId IS NULL OR r.id != :excludeReservationId)
        """)
    boolean existsOverlappingReservations(
            @Param("chambreId") Long chambreId,
            @Param("arrivalDate") LocalDate arrivalDate,
            @Param("departureDate") LocalDate departureDate,
            @Param("excludeReservationId") Long excludeReservationId
    );
    @Query("""
    SELECT r.id, r.arrivalDate, r.departureDate, r.client.firstName, r.client.lastName, r.chambre.roomNumber
    FROM Reservation r
""")
    List<Object[]> findAllReservationWithClientNames();
}
