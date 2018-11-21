package com.projector.management.server.service;

import com.projector.management.server.database.DataBase;
import com.projector.management.server.model.Duration;
import com.projector.management.server.model.Projector;
import com.projector.management.server.model.Reservation;
import com.projector.management.server.model.ReservationRequest;
import com.projector.management.server.service.impl.ProjectorReservationServiceImpl;
import com.projector.management.server.util.customexceptions.NoAvailableDurationException;
import com.projector.management.server.util.customexceptions.RequestDurationNotAvailableException;
import com.projector.management.server.util.customexceptions.ReservationNotFoundException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProjectorReservationServiceTest {

    private ProjectorReservationService projectorReservationService = new ProjectorReservationServiceImpl();

    private ReservationRequest getTestReservationRequest() {
        return new ReservationRequest("2018-11-20", "08:00:00","16:00:00");
    }

    @Test
    public void processReservationReq() {
        ReservationRequest testRequest = getTestReservationRequest();
        try {
            Reservation reservationRes = projectorReservationService.processReservationReq(testRequest);
            Duration exptectedDuration = testRequest.getRequestDuration();
            Reservation expectedReservation = new Reservation(1, DataBase.getProjectorByIndex(0), exptectedDuration);

            assertEquals(expectedReservation.getProjector(), reservationRes.getProjector());
            assertEquals(expectedReservation.getId(), reservationRes.getId());
            assertEquals(expectedReservation.getDuration(), reservationRes.getDuration());

        } catch (RequestDurationNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findOtherAvailableDurationsForRequest() {
        ReservationRequest testRequest = getTestReservationRequest();
        try {
            List<Duration> resDurations = projectorReservationService.findOtherAvailableDurationsForRequest(testRequest);
            assertEquals(resDurations.size(), 1);
            assertEquals(resDurations.get(0).getStartTime(), testRequest.getSameDayStartTime());
            assertEquals(resDurations.get(0).getEndTime(), testRequest.getNextDayEndTime());
        } catch (NoAvailableDurationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getReservationById() {
        ReservationRequest testRequest = getTestReservationRequest();
        try {
            projectorReservationService.processReservationReq(testRequest);
            Reservation reservation = projectorReservationService.getReservationById((long)1);
            Duration exptectedDuration = testRequest.getRequestDuration();
            Reservation expectedReservation = new Reservation(1, DataBase.getProjectorByIndex(0), exptectedDuration);

            assertEquals(expectedReservation.getProjector(), reservation.getProjector());
            assertEquals(expectedReservation.getId(), reservation.getId());
            assertEquals(exptectedDuration.getStartTime(), reservation.getDuration().getStartTime());
            assertEquals(exptectedDuration.getEndTime(), reservation.getDuration().getEndTime());

        } catch (RequestDurationNotAvailableException e) {
            e.printStackTrace();
        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void cancelReservation() {
        ReservationRequest testRequest = getTestReservationRequest();
        try {
            Reservation reservation = projectorReservationService.processReservationReq(testRequest);
            assertEquals(DataBase.getAllReservations().size(), 1);

            projectorReservationService.cancelReservation(reservation.getId());
            assertTrue(DataBase.getAllReservations().isEmpty());
        } catch (RequestDurationNotAvailableException e) {
            e.printStackTrace();
        } catch (ReservationNotFoundException e) {
            e.printStackTrace();
        }
    }
}