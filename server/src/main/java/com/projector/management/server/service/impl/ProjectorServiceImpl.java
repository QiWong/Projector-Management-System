package com.projector.management.server.service.impl;

import com.projector.management.server.database.DataBase;
import com.projector.management.server.model.Projector;
import com.projector.management.server.service.ProjectorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("projectorService")
public class ProjectorServiceImpl implements ProjectorService {

    private static final AtomicLong projectorCounter = new AtomicLong();

    static {
        //populate dummy data into database
        populateDummyProjectors();
    }

    public List<Projector> findAllProjectors() {
        return DataBase.findAllProjectors();
    }

    private static void populateDummyProjectors(){
        //populate 10 projectors into the database.
        for (int i = 0; i < 10; i++) {
            Projector projector = new Projector(projectorCounter.incrementAndGet(), "P"+i);
            DataBase.saveProjectorToDB(projector);
        }
    }
}
