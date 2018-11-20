package com.projector.management.server.util;

import java.text.SimpleDateFormat;
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
}
