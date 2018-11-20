package com.projector.management.server.service.impl;

import com.projector.management.server.database.DataBase;
import com.projector.management.server.model.Duration;
import com.projector.management.server.model.Projector;
import com.projector.management.server.model.Reservation;
import com.projector.management.server.model.ReservationRequest;
import com.projector.management.server.service.ProjectorReservationService;
import com.projector.management.server.util.CustomRespMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("projectorReservationService")
public class ProjectorReservationServiceImpl implements ProjectorReservationService {

    private static final AtomicLong reservationCounter = new AtomicLong();

    public String processReservationReq(ReservationRequest reservationRequest) {
        List<Projector> projectors = DataBase.findAllProjectors();
        Duration reqDuration = reservationRequest.getRequestDuration();
        for (Projector projector : projectors) {
            if (DataBase.checkProjectoravailability(projector, reqDuration)) {
                Reservation newReservation =
                        new Reservation(reservationCounter.incrementAndGet(), projector, reqDuration);
                DataBase.addRservationInDB(newReservation);
                return newReservation.toString();
            }
        }

        //there is no projector available, return available time Intervals today and nextday
        Date sameDayStartTime = reservationRequest.getSameDayStartTime();
        Date nextDayEndTime = reservationRequest.getNextDayEndTime();

        List<Duration> availableDurations = new ArrayList<>();
        for (Projector projector: projectors) {
            List<Duration> availableDurationForProjector =
                    DataBase.findAvailableDurationForProjector(projector, sameDayStartTime, nextDayEndTime);

            availableDurations = Duration.mergeDurationLists(availableDurations, availableDurationForProjector);
        }

        return CustomRespMessage.availableTimesMessage(availableDurations);
    }
}
