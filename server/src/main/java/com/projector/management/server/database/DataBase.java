package com.projector.management.server.database;

import com.projector.management.server.model.Duration;
import com.projector.management.server.model.Projector;
import com.projector.management.server.model.Reservation;

import java.util.*;

public class DataBase {
    private static final List<Projector> projectors = new ArrayList<>();
    private static final HashMap<Projector, ArrayList<Reservation>> projectorReservationsMap = new HashMap<>();

    public static void saveProjectorToDB(Projector projector) {
        if (projector != null) {
            projectors.add(projector);
        }
    }

    public static void addRservationInDB(Reservation reservation) {
        Projector projector = reservation.getProjector();
        if (projectorReservationsMap.containsKey(projector)) {
            projectorReservationsMap.get(projector).add(reservation);
        } else {
          ArrayList<Reservation> reservations = new ArrayList<>();
          reservations.add(reservation);
            projectorReservationsMap.put(projector, reservations);
        }
    }

    public static boolean checkProjectoravailability(Projector projector, Duration duration) {
        if (!projectorReservationsMap.containsKey(projector)) {
            return true;
        }

        List<Reservation> reservations = projectorReservationsMap.get(projector);
        Collections.sort(reservations, Reservation.ReservationStartTimeComparator);

        for (Reservation reservation : reservations) {
            if (duration.checkDurationOverlap(reservation.getDuration())) {
                return false;
            } else if (reservation.getDuration().getStartTime().compareTo(duration.getEndTime()) >= 0) {
                break;
            }
        }
        return true;
    }

    public static List<Duration> findAvailableDurationForProjector(Projector projector, Date startTime, Date endTime) {
        List<Duration> availableDurations = new ArrayList<>();
        if (!projectorReservationsMap.containsKey(projector)) {
            availableDurations.add(new Duration(startTime, endTime));
            return availableDurations;
        }

        List<Reservation> reservations = projectorReservationsMap.get(projector);
        Collections.sort(reservations, Reservation.ReservationStartTimeComparator);
        Date prevStartTime = startTime;
        for (Reservation reservation : reservations) {
            if (reservation.getDuration().getStartTime().compareTo(endTime) >=0) {
                break;
            }
            if (reservation.getDuration().getEndTime().compareTo(startTime) <=0) {
                continue;
            }
            if (prevStartTime.compareTo(reservation.getDuration().getStartTime()) != 0) {
                Duration availableDuration = new Duration(prevStartTime, reservation.getDuration().getStartTime());
                availableDurations.add(availableDuration);
            }
            prevStartTime = reservation.getDuration().getEndTime();
        }

        if (prevStartTime.compareTo(endTime) < 0) {
            Duration availableDuration = new Duration(prevStartTime, endTime);
            availableDurations.add(availableDuration);
        }
        return availableDurations;
    }

    public static List<Projector> findAllProjectors() {
        return projectors;
    }
}
