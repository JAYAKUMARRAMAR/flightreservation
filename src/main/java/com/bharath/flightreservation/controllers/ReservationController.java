package com.bharath.flightreservation.controllers;

import com.bharath.flightreservation.dtos.ReservationRequest;
import com.bharath.flightreservation.entities.*;
import com.bharath.flightreservation.repositories.*;
import com.bharath.flightreservation.services.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin
@Slf4j
public class ReservationController {

    private final FlightRepository flightRepository;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(FlightRepository flightRepository, ReservationService reservationService) {
        this.flightRepository = flightRepository;
        this.reservationService = reservationService;
    }

    @GetMapping("/showCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightId") Long flightId, Model model) {
        log.info("Displaying reservation form for flightId: {}", flightId);
        
        Optional<Flight> flightOptional = flightRepository.findById(flightId);
        if (flightOptional.isEmpty()) {
            log.warn("Flight not found with id: {}", flightId);
            model.addAttribute("error", "Flight not found");
            return "findFlights";
        }
        
        model.addAttribute("flight", flightOptional.get());
        return "completeReservation";
    }

    @PostMapping("/completeReservation")
    public String completeReservation(ReservationRequest request, Model model) {
        log.info("Processing reservation for flight: {}", request.getFlightId());
        
        try {
            Reservation reservation = reservationService.bookFlight(request);
            log.info("Reservation created successfully with ID: {}", reservation.getId());
            model.addAttribute("msg", "Reservation created successfully with ID: " + reservation.getId());
            return "reservationConfirmation";
        } catch (IllegalArgumentException e) {
            log.error("Failed to create reservation: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "completeReservation";
        }
    }
}
