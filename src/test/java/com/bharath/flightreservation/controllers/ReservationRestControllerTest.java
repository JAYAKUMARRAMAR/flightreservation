package com.bharath.flightreservation.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bharath.flightreservation.dtos.ReservationUpdateRequest;
import com.bharath.flightreservation.entities.Reservation;
import com.bharath.flightreservation.repositories.ReservationRepository;

@ExtendWith(MockitoExtension.class)
class ReservationRestControllerTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationRestController reservationRestController;

    private Reservation mockReservation;
    private ReservationUpdateRequest updateRequest;

    @BeforeEach
    void setUp() {
        mockReservation = new Reservation();
        mockReservation.setId(1L);
        mockReservation.setNumberOfBags(2);
        mockReservation.setCheckedIn(false);

        updateRequest = new ReservationUpdateRequest();
        updateRequest.setId(1L);
        updateRequest.setNumberOfBags(3);
        updateRequest.setCheckedIn(true);
    }

    // Tests for findReservation method

    @Test
    void findReservation_WithValidId_ReturnsOkWithReservation() {
        // Arrange
        Long reservationId = 1L;
        when(reservationRepository.findById(reservationId))
            .thenReturn(Optional.of(mockReservation));

        // Act
        ResponseEntity<Reservation> result = reservationRestController.findReservation(reservationId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(reservationId, result.getBody().getId());
        assertEquals(2, result.getBody().getNumberOfBags());
        assertFalse(result.getBody().getCheckedIn());
        verify(reservationRepository, times(1)).findById(reservationId);
    }

    @Test
    void findReservation_WithNonExistentId_ReturnsNotFound() {
        // Arrange
        Long nonExistentId = 999L;
        when(reservationRepository.findById(nonExistentId))
            .thenReturn(Optional.empty());

        // Act
        ResponseEntity<Reservation> result = reservationRestController.findReservation(nonExistentId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
        verify(reservationRepository, times(1)).findById(nonExistentId);
    }


    // Tests for updateReservation method

    @Test
    void updateReservation_WithValidRequest_ReturnsOkWithUpdatedReservation() {
        // Arrange
        when(reservationRepository.findById(updateRequest.getId()))
            .thenReturn(Optional.of(mockReservation));
        when(reservationRepository.save(any(Reservation.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<Reservation> result = reservationRestController.updateReservation(updateRequest);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
        assertEquals(3, result.getBody().getNumberOfBags());
        assertTrue(result.getBody().getCheckedIn());
        verify(reservationRepository, times(1)).findById(updateRequest.getId());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void updateReservation_WithNonExistentReservation_ReturnsNotFound() {
        // Arrange
        Long nonExistentId = 999L;
        updateRequest.setId(nonExistentId);
        when(reservationRepository.findById(nonExistentId))
            .thenReturn(Optional.empty());

        // Act
        ResponseEntity<Reservation> result = reservationRestController.updateReservation(updateRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
        verify(reservationRepository, times(1)).findById(nonExistentId);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

}