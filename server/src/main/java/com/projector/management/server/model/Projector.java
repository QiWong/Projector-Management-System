package com.projector.management.server.model;

import java.util.Objects;

public class Projector {

    private long id;
    private String name;

    public Projector(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Projector projector = (Projector) obj;
        if (this.id != projector.getId() || !this.name.equals(projector.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}
