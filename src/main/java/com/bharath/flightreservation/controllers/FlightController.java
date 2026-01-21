package com.bharath.flightreservation.controllers;

import com.bharath.flightreservation.entities.Flight;
import com.bharath.flightreservation.repositories.FlightRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@Slf4j
public class FlightController {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightController(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @GetMapping("/findFlights")
    public String displayFindFlights() {
        return "findFlights";
    }

    @PostMapping("/findFlights")
    public String findFlights(@RequestParam("from") String from,
                              @RequestParam("to") String to,
                              @RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy") Date departureDate,
                              Model model) {
        log.info("Searching flights from {} to {} on {}", from, to, departureDate);
        
        if (from == null || from.trim().isEmpty() || to == null || to.trim().isEmpty()) {
            log.warn("Invalid search parameters: from={}, to={}", from, to);
            model.addAttribute("error", "Departure and arrival cities are required");
            return "findFlights";
        }
        
        LocalDate localDate = departureDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        List<Flight> flights = flightRepository.findFlights(from, to, localDate);
        log.info("Found {} flights", flights.size());
        model.addAttribute("flights", flights);
        return "displayFlights";
    }
}