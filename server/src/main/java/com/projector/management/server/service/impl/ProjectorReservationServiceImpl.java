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

@Service("projectorReservationService")
public class ProjectorReservationServiceImpl implements ProjectorReservationService {

    private static final AtomicLong reservationCounter = new AtomicLong();

    public Reservation getReservationById(Long id) throws ReservationNotFoundException {
        return DataBase.findReservationById(id);
    }

    public void cancelReservation(Long id) throws ReservationNotFoundException {
        DataBase.deleteProjector(id);
    }

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

    public List<Duration> findOtherAvailableDurationsForRequest(ReservationRequest reservationRequest) throws NoAvailableDurationException {
        Date sameDayStartTime = reservationRequest.getSameDayStartTime();
        Date nextDayEndTime = reservationRequest.getNextDayEndTime();
        return findAvailableDurations(sameDayStartTime, nextDayEndTime);
    }

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
