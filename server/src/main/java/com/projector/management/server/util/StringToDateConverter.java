package com.projector.management.server.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class provides interface for converting string to Date object
 * @author Qi Wang
 */
public class StringToDateConverter {
    //The date format which will be used in convert.
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * Convert String date and time in the day to a Date object
     * @param date String that matches the format "yyyy-MM-DD"
     * @param timeInDay String that matches the format "hh-mm-ss"
     * @return Date object represents the time
     */
    public Date convertToDate(String date, String timeInDay) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            return formatter.parse(date + " " + timeInDay);
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s %s to Date fail", date, timeInDay));
        }
    }

    /**
     * Get the start time of the date. For example, if the date is 2018-11-20,
     * Then this function will return a Date object "2018-11-20 00:00:00"
     * @param date String represents the date
     * @return Date object
     */
    public Date getSameDayStartTime(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            return formatter.parse(date + " 00:00:00");
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", date));
        }
    }

    /**
     * Get the end time of the next day of the date. For example, if the date is 2018-11-20,
     * Then this function will return a Date object "2018-11-21 23:59:59"
     * @param date String represents the date
     * @return Date object
     */
    public Date getNextDayEndTime(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            Date curDayEndTime = formatter.parse(date + " 23:59:59");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDayEndTime);
            calendar.add(Calendar.DATE, 1);
            return calendar.getTime();
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", date));
        }
    }
}
