package com.projector.management.server.service;

import com.projector.management.server.model.Projector;

import java.util.List;

/**
 * Provides interface for processing projectors resources.
 * @author  Qi Wang
 */
public interface ProjectorService {
    /**
     * Return all projectors' information
     * @return List of Projectors object
     */
    List<Projector> findAllProjectors();
}
