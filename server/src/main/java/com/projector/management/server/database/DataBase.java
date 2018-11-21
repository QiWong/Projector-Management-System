package com.projector.management.server.database;

import com.projector.management.server.model.Duration;
import com.projector.management.server.model.Projector;
import com.projector.management.server.model.Reservation;
import com.projector.management.server.util.customexceptions.ReservationNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

/**
 * Because the database is not used in this project, so this class is used for storing the data
 * in memory for test purpose only, instead of real database. We will implement the DAO in future.
 * @author Qi Wang
 */
public class DataBase {
    /**
     * The list of projectors stored all the projectors information.
     */
    private static final List<Projector> projectors = new ArrayList<>();
    /**
     * The HashMap stores all the reservations. The key is reservation id, the value is the reservation details.
     * This is for retrieving the reservation information in O(1).
     */
    private static final HashMap<Long, Reservation> reservationsMap = new HashMap<>();
    /**
     * The HashMap stores all the reservations for all the projectors. The key is the projector, the value is
     * the reservation details. This map is for checking the available time of projectors.
     */
    private static final HashMap<Projector, ArrayList<Reservation>> projectorReservationsMap = new HashMap<>();

    /**
     * This is to populate some dummy data into the db (memory). Currently we assume we have 10 projectors.
     */
    static {
        populateDummyProjectors();
    }

    /**
     * This function is for adding test projectors data into the database.
     * In this function, we create 10 projectors, each has a name and id, such as
     * projector { id :0, name: P0 }.
     */
    private static void populateDummyProjectors() {
        //populate 10 projectors into the database.
        for (int i = 0; i < 10; i++) {
            Projector projector = new Projector((long)i, "P" + i);
            saveProjectorToDB(projector);
        }
    }

    /**
     * Save the projector to the list of projectors in memory.
     * @param projector Projector type which contains projector id and name.
     */
    public static void saveProjectorToDB(Projector projector) {
        if (projector != null) {
            projectors.add(projector);
        }
    }

    /**
     * Return the index-th projector of the projector list.
     * @param index int type.
     * @return the index-th projector in the list.
     */
    public static Projector getProjectorByIndex(int index) {
        return projectors.get(index);
    }

    /**
     * Save the reservation to the database, that is, add the reservation to the hashmap in memory.
     * @param reservation Reservation type, which contains reservation id, projector and
     *                    reservation duration.
     */
    public static void addRservationInDB(Reservation reservation) {
        reservationsMap.put(reservation.getId(), reservation);

        Projector projector = reservation.getProjector();
        if (projectorReservationsMap.containsKey(projector)) {
            projectorReservationsMap.get(projector).add(reservation);
        } else {
          ArrayList<Reservation> reservations = new ArrayList<>();
          reservations.add(reservation);
            projectorReservationsMap.put(projector, reservations);
        }
    }

    /**
     * Get all the reservations for all projectors in the database (memory).
     * @return List of Reservations.
     */
    public static List<Reservation> getAllReservations() {
        List<Reservation> allReservations = new ArrayList<>();

        for(Map.Entry<Long, Reservation> map : reservationsMap.entrySet()){
            allReservations.add(map.getValue());
        }

        return allReservations;
    }

    /**
     * Check if a reservation with corresponding exists.
     * @param id the id of the reservation
     * @return true if reservation exists, otherwise false.
     */
    private static boolean checkReservationExists(long id) {
        if (reservationsMap.containsKey(id)) {
            return true;
        }
        return false;
    }

    /**
     * Delete the reservation which has the corresponding id.
     * @param id The id of the reservation that needs to be deleted.
     * @throws ReservationNotFoundException when there is no corresponding reservation exists
     */
    public static void deleteProjector(long id) throws ReservationNotFoundException {
        if (checkReservationExists(id)) {
            Reservation reservation = findReservationById(id);
            reservationsMap.remove(id);
            ArrayList<Reservation> reservations = projectorReservationsMap.get(reservation.getProjector());
            for (int i = 0; i<reservations.size();i++) {
                if (reservations.get(i).getId() == id) {
                    reservations.remove(i);
                    break;
                }
            }
            projectorReservationsMap.put(reservation.getProjector(), reservations);
        } else {
            throw new ReservationNotFoundException("Reservation " + id + " Not Found");
        }
    }

    /**
     * find the reservation which has the corresponding id.
     * @param id the id of the reservation
     * @return A Reservation object which contains the reservation details.
     * @throws ReservationNotFoundException when there is no corresponding reservation exists
     */
    public static Reservation findReservationById(Long id) throws ReservationNotFoundException {
        if (checkReservationExists(id)) {
            return reservationsMap.get(id);
        } else {
            throw new ReservationNotFoundException("Reservation "+id+" Not Found");
        }
    }

    /**
     * Check if the projector is available during the duration
     * @param projector Projector object which contains the projector name and id.
     * @param duration a Duration object which contains the start time and the end time.
     * @return true if projector can be reserved during the time period.
     */
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

    /**
     * Return the available durations between the startTime and the endTime. So user can
     * make reservation between these durations.
     * @param projector Projector object which contains the projector name and id.
     * @param startTime Date object
     * @param endTime Date object
     * @return A list of Duration objects.
     */
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

    /**
     * Return all the projectors in database.
     * @return List of Projector object.
     */
    public static List<Projector> findAllProjectors() {
        return projectors;
    }
}
