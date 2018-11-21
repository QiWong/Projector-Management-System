package com.projector.management.server.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provide util functions that will be used in Duration test.
 * @author Qi Wang
 */
public class DurationTestUtil {
    //date format which will be used to parse the string to time
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * Parse the string and get a new duration object
     * @param startTime String matches the format "yyyy-MM-dd HH:mm:ss"
     * @param endTime String matches the format "yyyy-MM-dd HH:mm:ss"
     * @return Duration object
     */
    public static Duration getTestDuration(String startTime, String endTime) {
        Date date1 = getDateFromStr(startTime);
        Date date2 = getDateFromStr(endTime);
        return new Duration(date1, date2);
    }

    /**
     * Parse the string and get a new date object
     * @param timeStr String matches the format "yyyy-MM-dd HH:mm:ss"
     * @return Date object
     */
    public static Date getDateFromStr(String timeStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(timeStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
