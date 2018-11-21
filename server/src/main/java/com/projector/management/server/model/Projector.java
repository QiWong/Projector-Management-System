package com.projector.management.server.model;

import java.util.Objects;

/**
 * This class represents a projector, which contains an id and a name of the projector.
 * @author Qi Wang
 */
public class Projector {

    private long id; // the id of the projector
    private String name; //the name of the projector

    /**
     * Constructor
     * @param id the id of the projector
     * @param name the name of the projector
     */
    public Projector(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     *
     * @return the id of the projector
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id the id of the projector
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return the name of the projector
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name of the projector
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Check if two projector objects are same
     * @param obj Projector object
     * @return true if same.
     */
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

    /**
     * get hashcode based on projector's name and id
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}
