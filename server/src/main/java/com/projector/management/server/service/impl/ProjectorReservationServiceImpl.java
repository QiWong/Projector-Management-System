package com.projector.management.server.service.impl;

import com.projector.management.server.model.ReservationRequest;
import com.projector.management.server.service.ProjectorReservationService;
import org.springframework.stereotype.Service;

@Service("projectorReservationService")
public class ProjectorReservationServiceImpl implements ProjectorReservationService {
    public String processReservationReq(ReservationRequest reservationRequest) {
        
        return reservationRequest.toString();
    }
}
