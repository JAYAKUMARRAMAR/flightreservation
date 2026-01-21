package com.bharath.flightreservation.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String flightNumber;
    
    @NotBlank
    @Column(nullable = false)
    private String operatingAirlines;
    
    @NotBlank
    @Column(nullable = false)
    private String departureCity;
    
    @NotBlank
    @Column(nullable = false)
    private String arrivalCity;
    
    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfDeparture;
    
    private Timestamp estimatedDepartureTime;
    
}