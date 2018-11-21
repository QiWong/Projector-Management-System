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

/**
 * This class is for describing a time period which has a startTime and endTime.
 * @author Qi Wang
 */
public class Duration {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Date startTime; //Date type. The startTime of the period.
    private Date endTime;  //Date type. The endTime of the period.
    private String localStartTime; //The string format of the start time in the local time zone.
    private String localEndTime; //The string format of the start time in the local time zone.

    /**
     * Constructor
     * @param startTime Date object, the startTime of the time period.
     * @param endTime Date object, the endTime of the time period.
     */
    public Duration(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.localStartTime = convertISODateToLocalTimeString(startTime);
        this.localEndTime = convertISODateToLocalTimeString(endTime);
    }

    /**
     * @return The string format of the start time in the local time zone.
     */
    public String getLocalStartTime() {
        return localStartTime;
    }

    /**
     *
     * @param localStartTime The string format of the start time in the local time zone.
     */
    public void setLocalStartTime(String localStartTime) {
        this.localStartTime = localStartTime;
    }

    /**
     *
     * @return The string format of the start time in the local time zone.
     */
    public String getLocalEndTime() {
        return localEndTime;
    }

    /**
     *
     * @param localEndTime The string format of the start time in the local time zone.
     */
    public void setLocalEndTime(String localEndTime) {
        this.localEndTime = localEndTime;
    }

    /**
     *
     * @return Date object. Return the startTime of duration
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Convert ISO Date to the the date in system default timezone.
     * @param date Date object.
     * @return date in system default timezone.
     */
    private ZonedDateTime convertISODatetoLocal(Date date) {
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Convert ISO Date to the string format of the date in system timezone.
     * @param date Date object.
     * @return string format of the date in system timezone.
     */
    private String convertISODateToLocalTimeString(Date date) {
        return dateFormat.format(convertISODatetoLocal(date));
    }

    /**
     *
     * @param startTime Date object: start time of the duration
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        this.localStartTime = convertISODateToLocalTimeString(startTime);
    }

    /**
     *
     * @return the start time of the duration
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime Date object: end time of the duration
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        this.localEndTime = convertISODateToLocalTimeString(endTime);
    }

    /**
     * Check if current duration has overlap with another duration object
     * @param duration Duration object
     * @return true if two duration overlaps otherwise return false
     */
    public boolean checkDurationOverlap(Duration duration) {
        //check if two duration overlaps
        if (duration.getStartTime().compareTo(this.endTime) >= 0) {
            return false;
        } else if (duration.getEndTime().compareTo(this.startTime) <= 0){
            return false;
        }
        return true;
    }

    /**
     * Comparator that can be used to sort durations based on their start time.
     */
    public static Comparator<Duration> DurationStartTimeComparator = new Comparator<Duration>() {
        public int compare(Duration duration1, Duration duration2) {
            return duration1.getStartTime().compareTo(duration2.getStartTime());
        }
    };

    /**
     * Merge two list of durations into one list. And if two durations have overlap, then merge the two durations.
     * @param durationsList1 List of Durations
     * @param durationsList2 List of Durations
     * @return
     */
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

    /**
     *
     * @return String representation of the duration.
     */
    @Override
    public String toString() {
        return "StartTime: "+ this.localStartTime + ",\nEndTime: "+this.localEndTime;
    }
}
