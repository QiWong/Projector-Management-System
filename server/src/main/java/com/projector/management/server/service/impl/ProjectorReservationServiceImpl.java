package com.projector.management.server.service.impl;

import com.projector.management.server.database.DataBase;
import com.projector.management.server.model.Duration;
import com.projector.management.server.model.Projector;
import com.projector.management.server.model.Reservation;
import com.projector.management.server.model.ReservationRequest;
import com.projector.management.server.service.ProjectorReservationService;
import com.projector.management.server.util.customexceptions.NoAvailableDurationException;
import com.projector.management.server.util.customexceptions.RequestDurationNotAvailableException;
import com.projector.management.server.util.customexceptions.ReservationNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Provides services for managing projector reservations
 * @author  Qi Wang
 */
@Service("projectorReservationService")
public class ProjectorReservationServiceImpl implements ProjectorReservationService {
    //Reservation counter, which will be used as reservation id.
    private static final AtomicLong reservationCounter = new AtomicLong();

    /**
     * Get the reservation details based on reservation id.
     * @param id long id. The id of the reservation
     * @return Reservation object, contains all the details of the reservation.
     * @throws ReservationNotFoundException when no available Duration on two days.
     */
    public Reservation getReservationById(long id) throws ReservationNotFoundException {
        return DataBase.findReservationById(id);
    }

    /**
     * Cancel the reservation based on reservation details
     * @param id long id. The id of the reservation
     * @throws ReservationNotFoundException when no such reservation exists
     */
    public void cancelReservation(long id) throws ReservationNotFoundException {
        DataBase.deleteProjector(id);
    }

    /**
     * Process the reservation request, make reservation if there is projector available during the requested time.
     * Otherwise will throw exceptions.
     * @param reservationRequest ReservationRequest object. Contains the details of request reservation
     * @return Reservation object, contains all the details of a reservation.
     * @throws RequestDurationNotAvailableException when no projector is available for the requested time period
     */
    public Reservation processReservationReq(ReservationRequest reservationRequest)
            throws RequestDurationNotAvailableException {
        List<Projector> projectors = DataBase.findAllProjectors();
        Duration reqDuration = reservationRequest.getRequestDuration();
        for (Projector projector : projectors) {
            if (DataBase.checkProjectoravailability(projector, reqDuration)) {
                Reservation newReservation =
                        new Reservation(reservationCounter.incrementAndGet(), projector, reqDuration);
                DataBase.addRservationInDB(newReservation);
                return newReservation;
            }
        }
        throw new RequestDurationNotAvailableException(reservationRequest.getRequestDuration());
    }


    /**
     * When the request duration is not available, then check for the available durations on the same day and next day.
     * @param reservationRequest ReservationRequest object
     * @return List of Duration objects, during which there are projectors available
     * @throws NoAvailableDurationException If no projector is available during the same day and next day.
     */
    public List<Duration> findOtherAvailableDurationsForRequest(ReservationRequest reservationRequest) throws NoAvailableDurationException {
        Date sameDayStartTime = reservationRequest.getSameDayStartTime();
        Date nextDayEndTime = reservationRequest.getNextDayEndTime();
        return findAvailableDurations(sameDayStartTime, nextDayEndTime);
    }

    /**
     * Find available time durations where projectors are available between the start time and end time.
     * @param fromTime Date object, start time
     * @param toTime Date object, end time
     * @return List of Duration objects, during which there are projectors available
     * @throws NoAvailableDurationException
     */
    private List<Duration> findAvailableDurations(Date fromTime, Date toTime) throws NoAvailableDurationException {
        List<Projector> projectors = DataBase.findAllProjectors();
        List<Duration> availableDurations = new ArrayList<>();
        for (Projector projector: projectors) {
            List<Duration> availableDurationForProjector =
                    DataBase.findAvailableDurationForProjector(projector, fromTime, toTime);

            availableDurations = Duration.mergeDurationLists(availableDurations, availableDurationForProjector);
        }

        if (availableDurations.isEmpty()) {
            throw new NoAvailableDurationException(fromTime.toString(), toTime.toString());
        }

        return availableDurations;
    }
}
