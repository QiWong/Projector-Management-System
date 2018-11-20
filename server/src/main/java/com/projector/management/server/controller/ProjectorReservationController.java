package com.projector.management.server.controller;

import com.projector.management.server.model.ReservationRequest;
import com.projector.management.server.service.ProjectorReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ProjectorReservationController {
    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    ProjectorReservationService projectorReservationService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity addReservation(@RequestBody ReservationRequest reservationRequest) {
        System.out.println(reservationRequest.toString());
        String reservationRsult = projectorReservationService.processReservationReq(reservationRequest);
        return new ResponseEntity<String>(reservationRsult, HttpStatus.OK);
    }
}
