package com.projector.management.server.model;

import java.util.Comparator;

public class Reservation {

    private Duration duration;
    private Projector projector;
    private long id;

    public Reservation(long id, Projector projector, Duration duration) {
        this.id = id;
        this.projector = projector;
        this.duration = duration;
    }

    /*Comparator for sorting the Reservation by Reservation StartTime*/
    public static Comparator<Reservation> ReservationStartTimeComparator = new Comparator<Reservation>() {

        public int compare(Reservation reservation1, Reservation reservation2) {
            if ( reservation1.getDuration().getStartTime().compareTo(reservation2.getDuration().getStartTime()) != 0) {
                return reservation1.getDuration().getStartTime().compareTo(reservation2.getDuration().getStartTime());
            } else {
                return reservation1.getDuration().getEndTime().compareTo(reservation2.getDuration().getEndTime());
            }
        }
    };

    public Projector getProjector() {
        return projector;
    }

    public void setProjector(Projector projector) {
        this.projector = projector;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{\n reservation id: "+ this.id + ",\n projector: " + this.projector.getName()
                + ",\n startTime: "+ this.duration.getStartTime().toString()
                + ",\n endTime: "+this.duration.getEndTime().toString() + "\n}";
    }
}
