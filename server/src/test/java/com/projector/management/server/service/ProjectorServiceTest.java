package com.projector.management.server.service;

import com.projector.management.server.model.Projector;
import com.projector.management.server.service.impl.ProjectorServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectorServiceTest {

    private List<Projector> projectors = new ArrayList<>();

    private ProjectorService projectorService = new ProjectorServiceImpl();

    private void initializeProjectors() {
        for (int i = 0; i < 10; i++) {
            Projector projector = new Projector((long)(i), "P" + i);
            this.projectors.add(projector);
        }
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("Set Up test");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Running: tearDown");
    }

    @Test
    public void findAllProjectors() {
        initializeProjectors();
        List<Projector> allProjectors = projectorService.findAllProjectors();
        assertArrayEquals(allProjectors.toArray(), projectors.toArray());
    }
}