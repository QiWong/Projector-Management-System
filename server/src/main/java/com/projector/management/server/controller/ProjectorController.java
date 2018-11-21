package com.projector.management.server.controller;

import com.projector.management.server.model.Projector;
import com.projector.management.server.service.ProjectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Rest API Controller for projectors data services.
 * For example, get all the projectors information from Database.
 *
 * @author Qi
 */
@RestController
@RequestMapping("/projectors")
public class ProjectorController {
    public static final Logger logger = LoggerFactory.getLogger(ProjectorController.class);

    /**
     * The logic for processing the operations of projectors.
     */
    @Autowired
    ProjectorService projectorService;

    /**
     * rest controller to get list of projectors using GET API call '/projectors'.
     *
     * @return List of projectors.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Projector>> listAllProjectors() {
        List<Projector> projectors = projectorService.findAllProjectors();
        if (projectors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Projector>>(projectors, HttpStatus.OK);
    }
}
