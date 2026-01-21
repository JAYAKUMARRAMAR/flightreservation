package com.bharath.flightreservation.controllers;

import com.bharath.flightreservation.dtos.ReservationRequest;
import com.bharath.flightreservation.entities.*;
import com.bharath.flightreservation.repositories.*;
import com.bharath.flightreservation.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class ReservationController {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/showCompleteReservation")
    public String showCompleteReservation(@RequestParam("flightId") Long flightId, Model model) {
        Flight flight = flightRepository.findById(flightId).orElse(null);
        model.addAttribute("flight", flight);
        return "completeReservation";
    }

    @PostMapping("/completeReservation")
    public String completeReservation(ReservationRequest request, Model model) {
        Reservation reservation = reservationService.bookFlight(request);
        model.addAttribute("msg", "Reservation created successfully with ID: " + reservation.getId());
        return "reservationConfirmation";
      //   return "findFlightsDemo";
    }
}
