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

@RestController
@RequestMapping("/reservation")
public class ProjectorReservationController {
    public static final Logger logger = LoggerFactory.getLogger(ProjectorReservationController.class);

    @Autowired
    ProjectorReservationService projectorReservationService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addReservation(@RequestBody ReservationRequest reservationRequest) {
        try {
            Reservation newReservation = projectorReservationService.processReservationReq(reservationRequest);
            return new ResponseEntity<>(newReservation, HttpStatus.OK);
        } catch (RequestDurationNotAvailableException e) {
            try {
                List<Duration> availableDurations =
                        projectorReservationService.findOtherAvailableDurationsForRequest(reservationRequest);
                return new ResponseEntity<>(availableDurations, HttpStatus.OK);
            } catch (NoAvailableDurationException noAvailableDurationException) {
                return new ResponseEntity<>(noAvailableDurationException.getMessage(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

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
