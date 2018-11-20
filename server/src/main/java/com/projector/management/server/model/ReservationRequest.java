package com.projector.management.server.model;

import com.projector.management.server.util.StringToDateConverter;

import java.util.Date;

public class ReservationRequest {
    //date - "yyyy-mm-dd
    private String date;
    private String startTimeInDay;
    private String endTimeInDay;

    public ReservationRequest(String date, String startTimeInDay, String endTimeInDay) {
        this.date = date;
        this.startTimeInDay = startTimeInDay;
        this.endTimeInDay = endTimeInDay;
    }

    public Duration getRequestDuration() {
        StringToDateConverter converter = new StringToDateConverter();
        Date startTime = converter.convertToDate(this.date, this.startTimeInDay);
        Date endTime = converter.convertToDate(this.date, this.endTimeInDay);
        return new Duration(startTime, endTime);
    }
}
