package com.bharath.flightreservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bharath.flightreservation.dtos.ReservationUpdateRequest;
import com.bharath.flightreservation.entities.Reservation;
import com.bharath.flightreservation.repositories.ReservationRepository;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@RestController
@CrossOrigin
@Slf4j
public class ReservationRestController {

	private final ReservationRepository reservationRepository;

	@Autowired
	public ReservationRestController(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	@GetMapping("/reservations/{id}")
	public ResponseEntity<Reservation> findReservation(@PathVariable("id") Long id) {
		log.info("Finding reservation with id: {}", id);
		
		if (id == null || id <= 0) {
			log.warn("Invalid reservation id: {}", id);
			return ResponseEntity.badRequest().build();
		}
		
		Optional<Reservation> reservation = reservationRepository.findById(id);
		if (reservation.isPresent()) {
			log.info("Reservation found: {}", id);
			return ResponseEntity.ok(reservation.get());
		} else {
			log.warn("Reservation not found with id: {}", id);
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/reservations")
	public ResponseEntity<Reservation> updateReservation(@RequestBody ReservationUpdateRequest request) {
		log.info("Updating reservation: {}", request);
		
		if (request == null || request.getId() == null || request.getId() <= 0) {
			log.warn("Invalid update request: {}", request);
			return ResponseEntity.badRequest().build();
		}
		
		if (request.getNumberOfBags() < 0) {
			log.warn("Invalid number of bags: {}", request.getNumberOfBags());
			return ResponseEntity.badRequest().build();
		}
		
		Optional<Reservation> optionalReservation = reservationRepository.findById(request.getId());
		
		if (optionalReservation.isEmpty()) {
			log.warn("Reservation not found for update: {}", request.getId());
			return ResponseEntity.notFound().build();
		}
		
		Reservation reservation = optionalReservation.get();
		reservation.setNumberOfBags(request.getNumberOfBags());
		reservation.setCheckedIn(request.getCheckedIn());
		Reservation savedReservation = reservationRepository.save(reservation);
		log.info("Reservation updated successfully: {}", savedReservation.getId());
		
		return ResponseEntity.ok(savedReservation);
	}

}
