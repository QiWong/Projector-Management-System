package com.projector.management.server.controller;

import com.projector.management.server.model.Duration;
import com.projector.management.server.model.Reservation;
import com.projector.management.server.model.ReservationRequest;
import com.projector.management.server.service.ProjectorReservationService;
import com.projector.management.server.util.customexceptions.NoAvailableDurationException;
import com.projector.management.server.util.customexceptions.RequestDurationNotAvailableException;
import com.projector.management.server.util.customexceptions.ReservationNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest API Controller for projector reservation requests.
 * For example, reserve a projector during certain period.
 *
 * @author Qi
 */
@RestController
@RequestMapping("/reservation")
public class ProjectorReservationController {
    public static final Logger logger = LoggerFactory.getLogger(ProjectorReservationController.class);

    /**
     * The logic for processing the operations of projector reservations.
     * Such as reserve projectors and cancel reservation.
     */
    @Autowired
    ProjectorReservationService projectorReservationService;

    /**
     * Create a new reservation through a HTTP POST call to /reservation .
     * @param reservationRequest The reservationRequest contains the date, startTimeInDay
     *                           and endTimeInDay, indicating a projector needs to be reserved from
     *                           startTimeInDay to endTimeInDay on the date provided.
     * @return The details of the reservation including reservationID, projector, startTime, endTime
     * if reservation are made successfully. If no projector is available during the requested time
     * duration, then a list contains other available durations on the same day and next day will be
     * returned. And if there is any other error, the error message will be returned.
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            //make reservation based on the request
            Reservation newReservation = projectorReservationService.processReservationReq(reservationRequest);
            return new ResponseEntity<>(newReservation, HttpStatus.OK);
        } catch (RequestDurationNotAvailableException e) {
            /*
            if no projector is available during the requested time duration, then a list of durations that
            there are projectors are available when customer can make reservations on the same day
             and next day are returned.
             */
            try {
                List<Duration> availableDurations =
                        projectorReservationService.findOtherAvailableDurationsForRequest(reservationRequest);
                return new ResponseEntity<>(availableDurations, HttpStatus.OK);
            } catch (NoAvailableDurationException noAvailableDurationException) {
                /*
                If no projector is available (all projectors are reserved) on the same day and next day,
                then return the error message explaining the situation.
                 */
                return new ResponseEntity<>(noAvailableDurationException.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Return the information of a reservation whose id is provided in the HTTP GET request.
     * @param id long type. The id of a reservation
     * @return 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getReservationById(@PathVariable("id") long id) {
        try {
            Reservation reservation = projectorReservationService.getReservationById(id);
            return new ResponseEntity<Reservation>(reservation, HttpStatus.OK);
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteReservation(@PathVariable("id") long id) {
        logger.info("delete reservation {}", id);
        try {
            projectorReservationService.cancelReservation(id);
            return new ResponseEntity<Reservation>(HttpStatus.NO_CONTENT);
        } catch (ReservationNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
