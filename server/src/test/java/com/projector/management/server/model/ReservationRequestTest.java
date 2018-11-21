package com.projector.management.server.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReservationRequestTest {

    ReservationRequest reservationRequest =
            new ReservationRequest("2018-11-20", "08:00:00", "12:00:00");

    @Test
    public void getRequestDuration() {
        Duration expected = DurationTestUtil.getTestDuration("2018-11-20 08:00:00", "2018-11-20 12:00:00");
        Duration reqDuration = reservationRequest.getRequestDuration();

        assertEquals(expected.getStartTime(), reqDuration.getStartTime());
        assertEquals(expected.getEndTime(), reqDuration.getEndTime());
    }

    @Test
    public void getSameDayStartTime() {
        Date testDate = DurationTestUtil.getDateFromStr("2018-11-20 00:00:00");
        assertEquals(testDate, reservationRequest.getSameDayStartTime());
    }

    @Test
    public void getNextDayEndTime() {
        Date testDate = DurationTestUtil.getDateFromStr("2018-11-21 23:59:59");
        assertEquals(testDate, reservationRequest.getNextDayEndTime());
    }
}