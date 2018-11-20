package com.projector.management.server.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Collections;


public class Duration {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Date startTime;
    private Date endTime;
    private String localStartTime;
    private String localEndTime;

    public Duration(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.localStartTime = convertISODateToLocalTimeString(startTime);
        this.localEndTime = convertISODateToLocalTimeString(endTime);
    }

    public String getLocalStartTime() {
        return localStartTime;
    }

    public void setLocalStartTime(String localStartTime) {
        this.localStartTime = localStartTime;
    }

    public String getLocalEndTime() {
        return localEndTime;
    }

    public void setLocalEndTime(String localEndTime) {
        this.localEndTime = localEndTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    private ZonedDateTime convertISODatetoLocal(Date date) {
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private String convertISODateToLocalTimeString(Date date) {
        return dateFormat.format(convertISODatetoLocal(date));
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        this.localStartTime = convertISODateToLocalTimeString(startTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        this.localEndTime = convertISODateToLocalTimeString(endTime);
    }

    public boolean checkDurationOverlap(Duration duration) {
        //check if two duration overlaps
        if (duration.getStartTime().compareTo(this.endTime) >= 0) {
            return false;
        } else if (duration.getEndTime().compareTo(this.startTime) <= 0){
            return false;
        }
        return true;
    }

    public static Comparator<Duration> DurationStartTimeComparator = new Comparator<Duration>() {
        public int compare(Duration duration1, Duration duration2) {
            return duration1.getStartTime().compareTo(duration2.getStartTime());
        }
    };

    public static List<Duration> mergeDurationLists(List<Duration> durationsList1, List<Duration> durationsList2) {
        List<Duration> newDurationList = new ArrayList<>(durationsList1);
        newDurationList.addAll(durationsList2);

        if (newDurationList.isEmpty()) {
            return newDurationList;
        }

        Collections.sort(newDurationList, Duration.DurationStartTimeComparator);

        List<Duration> mergedDurations = new ArrayList<>();

        Date start = newDurationList.get(0).getStartTime();
        Date end = newDurationList.get(0).getEndTime();

        for (Duration duration: newDurationList) {
            if (duration.getStartTime().compareTo(end) <=0) {
                if (duration.getEndTime().compareTo(end) > 0) {
                    end = duration.getEndTime();
                }
            }else {
                mergedDurations.add(new Duration(start, end));
                start = duration.getStartTime();
                end = duration.getEndTime();
            }
        }

        mergedDurations.add(new Duration(start, end));
        return mergedDurations;
    }

    @Override
    public String toString() {
        return "StartTime: "+ this.localStartTime + ",\nEndTime: "+this.localEndTime;
    }
}
