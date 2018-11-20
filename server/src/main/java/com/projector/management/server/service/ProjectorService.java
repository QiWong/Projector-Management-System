package com.projector.management.server.service;

import com.projector.management.server.model.Projector;

import java.util.List;

public interface ProjectorService {
    List<Projector> findAllProjectors();
}
