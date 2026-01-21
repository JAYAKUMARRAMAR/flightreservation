package com.bharath.flightreservation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bharath.flightreservation.dtos.ReservationRequest;
import com.bharath.flightreservation.entities.*;
import com.bharath.flightreservation.repositories.*;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    ReservationRepository reservationRepository;
    
    @Override
    public Reservation bookFlight(ReservationRequest request) {
     Long flightId = request.getFlightId();
     if (flightId == null) {
         throw new IllegalArgumentException("Flight ID cannot be null");
     }
     Flight flight = flightRepository.findById(flightId).get();
     Passenger passenger = new Passenger();
     passenger.setFirstName(request.getFirstName());
     passenger.setLastName(request.getLastName());
     passenger.setEmail(request.getEmail());
     passenger.setPhone(request.getPhone()); 
     Passenger savedPassenger = passengerRepository.save(passenger);

        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(savedPassenger);

        Reservation savedReservation = reservationRepository.save(reservation);
        return savedReservation;
    }
    
}
