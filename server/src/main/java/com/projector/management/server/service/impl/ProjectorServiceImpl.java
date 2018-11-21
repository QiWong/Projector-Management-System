package com.projector.management.server.service.impl;

import com.projector.management.server.database.DataBase;
import com.projector.management.server.model.Projector;
import com.projector.management.server.service.ProjectorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The logic for processing projectors resources.
 * @author Qi Wang
 */
@Service("projectorService")
public class ProjectorServiceImpl implements ProjectorService {

    /**
     * Return all projectors' information
     * @return List of Projectors object
     */
    public List<Projector> findAllProjectors() {
        return DataBase.findAllProjectors();
    }
}
