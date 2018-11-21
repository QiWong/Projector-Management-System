package com.projector.management.server.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DurationTestUtil {
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static Duration getTestDuration(String startTime, String endTime) {
        Date date1 = getDateFromStr(startTime);
        Date date2 = getDateFromStr(endTime);
        return new Duration(date1, date2);
    }

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
