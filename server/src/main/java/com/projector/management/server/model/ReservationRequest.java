package com.projector.management.server.model;

import com.projector.management.server.util.StringToDateConverter;

import java.util.Date;

/**
 * The information of a reservation request.
 * @author Qi Wang
 */
public class ReservationRequest {
    /**
     *  String date - must in format "yyyy-mm-dd"
     */
    private String date;
    /**
     * The start time in the day of the request, the string must match the pattern "hh:mm:ss"
     */
    private String startTimeInDay;
    /**
     * The end time in the day of the request, the string must match the pattern "hh:mm:ss"
     */
    private String endTimeInDay;

    /**
     * Constructor
     * @param date String represents the date, must match the pattern "yyyy-mm-dd"
     * @param startTimeInDay String represents the start time, must match the pattern "hh:mm:ss"
     * @param endTimeInDay String represents the end time, must match the pattern "hh:mm:ss"
     */
    public ReservationRequest(String date, String startTimeInDay, String endTimeInDay) {
        this.date = date;
        this.startTimeInDay = startTimeInDay;
        this.endTimeInDay = endTimeInDay;
    }

    /**
     * Get the Duration object of the reservation request
     * @return The Duration object of the request
     */
    public Duration getRequestDuration() {
        StringToDateConverter converter = new StringToDateConverter();
        Date startTime = converter.convertToDate(this.date, this.startTimeInDay);
        Date endTime = converter.convertToDate(this.date, this.endTimeInDay);
        return new Duration(startTime, endTime);
    }

    /**
     * Get the start time of the date in the request. For example, if user request a projector on Day 2018-11-20,
     * Then this function will return a Date object "2018-11-20 00:00:00"
     * @return Date object
     */
    public Date getSameDayStartTime() {
        StringToDateConverter converter = new StringToDateConverter();
        return converter.getSameDayStartTime(this.date);
    }

    /**
     * Get the end time of the next day of the date in the request. For example,
     * if user request a projector on Day 2018-11-20,
     * Then this function will return a Date object "2018-11-21 23:59:59"
     * @return Date object
     */
    public Date getNextDayEndTime() {
        StringToDateConverter converter = new StringToDateConverter();
        return converter.getNextDayEndTime(this.date);
    }
}
