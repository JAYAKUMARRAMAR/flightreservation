package com.bharath.flightreservation.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;

}