package com.projector.management.server.model;

public class Reservation {

    private Duration duration;
    private Projector projector;
    private long id;

    public Reservation(long id, Projector projector, Duration duration) {
        this.id = id;
        this.projector = projector;
        this.duration = duration;
    }

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
}
