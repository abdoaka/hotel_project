package com.example.hotelapplication.services;

import com.example.hotelapplication.entities.Chambre;
import com.example.hotelapplication.entities.Client;
import com.example.hotelapplication.entities.Reservation;
import com.example.hotelapplication.exceptions.ReservationConflictException;
import com.example.hotelapplication.exceptions.ResourceNotFoundException;
import com.example.hotelapplication.repositories.ChambreRepository;
import com.example.hotelapplication.repositories.ClientRepository;
import com.example.hotelapplication.repositories.ReservationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository; // Add this
    private final ChambreRepository chambreRepository;
    public ReservationService(
            ReservationRepository reservationRepository,
            ClientRepository clientRepository,
            ChambreRepository chambreRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
        this.chambreRepository = chambreRepository;
    }
    // Create a new reservation
    public Reservation createReservation(Long clientId, Long chambreId, LocalDate arrival, LocalDate departure) {
        // 1. Fetch client and chambre from repositories
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        Chambre chambre = chambreRepository.findById(chambreId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        // 2. Check availability
        if (reservationRepository.existsOverlappingReservations(chambreId, arrival, departure, null)) {
            throw new ReservationConflictException("Room is already booked during these dates");
        }

        // 3. Create and save reservation
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setChambre(chambre);
        reservation.setArrivalDate(arrival);
        reservation.setDepartureDate(departure);

        return reservationRepository.save(reservation);
    }

    // Update an existing reservation
    public Reservation updateReservation(Long id, LocalDate newArrival, LocalDate newDeparture) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        // Check availability (exclude current reservation)
        if (reservationRepository.existsOverlappingReservations(
                reservation.getChambre().getId(),
                newArrival,
                newDeparture,
                id
        )) {
            throw new ReservationConflictException("Room is already booked during the new dates");
        }

        reservation.setArrivalDate(newArrival);
        reservation.setDepartureDate(newDeparture);
        return reservationRepository.save(reservation);
    }

    // Validate arrival/departure dates
    private void validateReservationDates(Reservation reservation) {
        if (reservation.getDepartureDate().isBefore(reservation.getArrivalDate())) {
            throw new IllegalArgumentException("Departure date must be after arrival date");
        }
    }

    // Check for overlapping reservations
    private void checkForOverlaps(Reservation reservation, Long excludeReservationId) {
        boolean hasOverlap = reservationRepository.existsOverlappingReservations( // Correct method name
                reservation.getChambre().getId(),
                reservation.getArrivalDate(),
                reservation.getDepartureDate(),
                excludeReservationId
        );

        if (hasOverlap) {
            throw new ReservationConflictException("Room is already reserved during the selected dates");
        }
    }
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Chambre chambre = reservation.getChambre();
        reservationRepository.deleteById(reservationId);

        // Mark room as available
        chambre.setStatus("AVAILABLE");
        chambreRepository.save(chambre);
    }
    public List<Map<String, Object>> getAllReservations() {
        List<Object[]> rows = reservationRepository.findAllReservationWithClientNames();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", row[0]);
            map.put("arrivalDate", row[1]);
            map.put("departureDate", row[2]);
            map.put("clientFirstName", row[3]);
            map.put("clientLastName", row[4]);
            map.put("roomNumber", row[5]);
            result.add(map);
        }
        if(result.isEmpty()){
            throw new ResourceNotFoundException("No reservations found ");
        }
        return result;
    }
    public List<Map<String, Object>> getReservationsByClientId(Long clientId) {
        List<Object[]> rows = reservationRepository.findReservationsByClientWithRoomNumber(clientId);
        if (rows.isEmpty()) {
            throw new ResourceNotFoundException("No reservations found for this client");
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", row[0]);
            map.put("arrivalDate", row[1]);
            map.put("departureDate", row[2]);
            map.put("roomNumber", row[3]);
            result.add(map);
        }
        return result;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}