package com.projector.management.server.model;

import java.util.Comparator;

/**
 * This class represents a reservation of a projector
 * @author Qi Wang
 */
public class Reservation {

    private Duration duration; //A Duration object which is the duration of the reservation
    private Projector projector; //The projector that is reserved.
    private long id; //The id of the reservation

    /**
     * Constructor
     * @param id Reservation id
     * @param projector Projector object
     * @param duration Duration object
     */
    public Reservation(long id, Projector projector, Duration duration) {
        this.id = id;
        this.projector = projector;
        this.duration = duration;
    }

    /**
     * Comparator for sorting the Reservation by Reservation StartTime
     * */
    public static Comparator<Reservation> ReservationStartTimeComparator = new Comparator<Reservation>() {

        public int compare(Reservation reservation1, Reservation reservation2) {
            if ( reservation1.getDuration().getStartTime().compareTo(reservation2.getDuration().getStartTime()) != 0) {
                return reservation1.getDuration().getStartTime().compareTo(reservation2.getDuration().getStartTime());
            } else {
                return reservation1.getDuration().getEndTime().compareTo(reservation2.getDuration().getEndTime());
            }
        }
    };

    /**
     *
     * @return Projector
     */
    public Projector getProjector() {
        return projector;
    }

    /**
     *
     * @param projector Projector of the reservation
     */
    public void setProjector(Projector projector) {
        this.projector = projector;
    }

    /**
     *
     * @return Duration of the reservation
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     *
     * @param duration Duration of the reservation
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     *
     * @return id of the reservation
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id id of the reservation
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return String representation of the reservation
     */
    @Override
    public String toString() {
        return "{\n reservation id: "+ this.id + ",\n projector: " + this.projector.getName()
                + ",\n startTime: "+ this.duration.getStartTime().toString()
                + ",\n endTime: "+this.duration.getEndTime().toString() + "\n}";
    }
}
