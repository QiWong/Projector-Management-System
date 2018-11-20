package com.projector.management.server.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringToDateConverter {
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public Date convertToDate(String date, String timeInDay) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            return formatter.parse(date + " " + timeInDay);
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s %s to Date fail", date, timeInDay));
        }
    }

    public Date getSameDayStartTime(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            return formatter.parse(date + " 00:00:00");
        } catch (Exception e) {
            throw new RuntimeException(String.format("parser %s to Date fail", date));
        }
    }

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
