package com.projector.management.server.database;

import com.projector.management.server.model.Projector;
import com.projector.management.server.model.Reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase {
    private static final List<Projector> projectors = new ArrayList<>();
    private static final HashMap<Projector, ArrayList<Reservation>> projectorReservations = new HashMap<>();

    public static void saveProjectorToDB(Projector projector) {
        if (projector != null) {
            projectors.add(projector);
        }
    }

    public static List<Projector> findAllProjectors() {
        return projectors;
    }
}
