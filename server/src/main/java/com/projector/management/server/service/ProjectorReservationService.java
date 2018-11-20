package com.projector.management.server.service;

import com.projector.management.server.model.Duration;
import com.projector.management.server.model.Reservation;
import com.projector.management.server.model.ReservationRequest;
import com.projector.management.server.util.customexceptions.NoAvailableDurationException;
import com.projector.management.server.util.customexceptions.RequestDurationNotAvailableException;
import com.projector.management.server.util.customexceptions.ReservationNotFoundException;

import java.util.Date;
import java.util.List;

public interface ProjectorReservationService {
    Reservation processReservationReq(ReservationRequest reservationRequest) throws RequestDurationNotAvailableException;

    List<Duration> findOtherAvailableDurationsForRequest(ReservationRequest reservationRequest) throws NoAvailableDurationException;

    Reservation getReservationById(Long id) throws ReservationNotFoundException;

    void cancelReservation(Long id) throws ReservationNotFoundException;
}