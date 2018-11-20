package com.projector.management.server.service;

import com.projector.management.server.model.ReservationRequest;

public interface ProjectorReservationService {
    String processReservationReq(ReservationRequest reservationRequest);
}
