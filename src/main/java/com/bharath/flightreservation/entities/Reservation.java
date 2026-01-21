package com.bharath.flightreservation.entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Boolean checkedIn;
    
    private Integer numberOfBags;
    
    @OneToOne
    private Passenger passenger;
    
    @OneToOne
    private Flight flight;
    
}