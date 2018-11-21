package com.projector.management.server.service;

import com.projector.management.server.model.Duration;
import com.projector.management.server.model.Reservation;
import com.projector.management.server.model.ReservationRequest;
import com.projector.management.server.util.customexceptions.NoAvailableDurationException;
import com.projector.management.server.util.customexceptions.RequestDurationNotAvailableException;
import com.projector.management.server.util.customexceptions.ReservationNotFoundException;

import java.util.List;

/**
 * Provides interface for managing projector Reservations
 * @author  Qi Wang
 */
public interface ProjectorReservationService {
    /**
     * Process the reservation request, make reservation if there is projector available during the requested time.
     * Otherwise will throw exceptions.
     * @param reservationRequest ReservationRequest object. Contains the details of request reservation
     * @return Reservation object, contains all the details of a reservation.
     * @throws RequestDurationNotAvailableException when no projector is available for the requested time period
     */
    Reservation processReservationReq(ReservationRequest reservationRequest) throws RequestDurationNotAvailableException;

    /**
     * When the request duration is not available, then check for the available durations on the same day and next day.
     * @param reservationRequest ReservationRequest object
     * @return List of Duration objects, during which there are projectors available
     * @throws NoAvailableDurationException If no projector is available during the same day and next day.
     */
    List<Duration> findOtherAvailableDurationsForRequest(ReservationRequest reservationRequest) throws NoAvailableDurationException;

    /**
     * Get the reservation details based on reservation id.
     * @param id long id. The id of the reservation
     * @return Reservation object, contains all the details of the reservation.
     * @throws ReservationNotFoundException when no available Duration on two days.
     */
    Reservation getReservationById(long id) throws ReservationNotFoundException;

    /**
     * Cancel the reservation based on reservation details
     * @param id long id. The id of the reservation
     * @throws ReservationNotFoundException when no such reservation exists
     */
    void cancelReservation(long id) throws ReservationNotFoundException;
}