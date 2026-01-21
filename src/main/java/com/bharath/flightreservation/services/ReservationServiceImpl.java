package com.bharath.flightreservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bharath.flightreservation.dtos.ReservationRequest;
import com.bharath.flightreservation.entities.*;
import com.bharath.flightreservation.repositories.*;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Service
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(FlightRepository flightRepository,
                                  PassengerRepository passengerRepository,
                                  ReservationRepository reservationRepository) {
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.reservationRepository = reservationRepository;
    }
    
    @Override
    @Transactional
    public Reservation bookFlight(ReservationRequest request) {
        log.info("Booking flight for passenger: {} {}", request.getFirstName(), request.getLastName());
        
        // Validate request
        if (request == null) {
            throw new IllegalArgumentException("Reservation request cannot be null");
        }
        
        Long flightId = request.getFlightId();
        if (flightId == null) {
            throw new IllegalArgumentException("Flight ID cannot be null");
        }
        
        // Validate passenger details
        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        
        // Find flight
        Optional<Flight> flightOptional = flightRepository.findById(flightId);
        if (flightOptional.isEmpty()) {
            throw new IllegalArgumentException("Flight not found with ID: " + flightId);
        }
        Flight flight = flightOptional.get();
        
        // Create passenger
        Passenger passenger = new Passenger();
        passenger.setFirstName(request.getFirstName().trim());
        passenger.setLastName(request.getLastName().trim());
        passenger.setEmail(request.getEmail().trim());
        passenger.setPhone(request.getPhone() != null ? request.getPhone().trim() : null);
        Passenger savedPassenger = passengerRepository.save(passenger);
        log.info("Passenger saved with ID: {}", savedPassenger.getId());

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(savedPassenger);
        reservation.setCheckedIn(false);
        reservation.setNumberOfBags(0);

        Reservation savedReservation = reservationRepository.save(reservation);
        log.info("Reservation created successfully with ID: {}", savedReservation.getId());
        return savedReservation;
    }
    
}
