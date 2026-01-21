package com.bharath.flightreservation.repositories;

import com.bharath.flightreservation.entities.Flight;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

     @Query("FROM Flight WHERE departureCity = :from AND arrivalCity = :to AND dateOfDeparture = :departureDate")
    List<Flight> findFlights(@Param("from") String from, @Param("to") String to, @Param("departureDate") LocalDate departureDate);
}