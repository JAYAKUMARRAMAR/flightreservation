package com.bharath.flightreservation.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationUpdateRequest {
	
	@NotNull(message = "Reservation ID is required")
	private Long id;
	
	@NotNull(message = "Check-in status is required")
	private Boolean checkedIn;
	
	@Min(value = 0, message = "Number of bags cannot be negative")
	private int numberOfBags;	

}
