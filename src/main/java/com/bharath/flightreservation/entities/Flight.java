package com.bharath.flightreservation.entities;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String flightNumber;
    private String operatingAirlines;
    private String departureCity;
    private String arrivalCity;
    private LocalDate dateOfDeparture;
    private Timestamp estimatedDepartureTime;
    
}