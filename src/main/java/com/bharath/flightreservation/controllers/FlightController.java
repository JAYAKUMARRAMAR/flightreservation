package com.bharath.flightreservation.controllers;

import com.bharath.flightreservation.entities.Flight;
import com.bharath.flightreservation.repositories.FlightRepository;

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
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/findFlights")
    public String displayFindFlights() {
        return "findFlights";
    }

    @PostMapping("/findFlights")
    public String findFlights(@RequestParam("from") String from,
                              @RequestParam("to") String to,
                              @RequestParam("departureDate") @DateTimeFormat(pattern = "MM-dd-yyyy") Date departureDate,
                              Model model) {
        LocalDate localDate = departureDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        List<Flight> flights = flightRepository.findFlights(from, to, localDate);
        model.addAttribute("flights",flights);
        return "displayFlights";
    }
}