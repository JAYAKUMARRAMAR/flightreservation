package com.bharath.flightreservation.entities;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Data
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false)
    private Boolean checkedIn = false;
    
    @Min(0)
    @Column(nullable = false)
    private Integer numberOfBags = 0;
    
    @OneToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    @NotNull
    private Passenger passenger;
    
    @OneToOne
    @JoinColumn(name = "flight_id", nullable = false)
    @NotNull
    private Flight flight;
    
}