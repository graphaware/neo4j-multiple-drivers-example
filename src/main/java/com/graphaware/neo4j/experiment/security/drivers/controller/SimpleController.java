package com.graphaware.neo4j.experiment.security.drivers.controller;

import com.graphaware.neo4j.experiment.security.drivers.neo4j.Neo4jRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class SimpleController {

    private final Neo4jRepository neo4jRepository;

    @Autowired
    public SimpleController(Neo4jRepository neo4jRepository) {
        this.neo4jRepository = neo4jRepository;
    }

    @GetMapping("/naive")
    public Set<String> getPersonNamesNaive() {
        return neo4jRepository.getPersonNamesInDbNaive();
    }

    @GetMapping("/cached")
    public Set<String> getPersonNamesCached() {
        return neo4jRepository.getPersonNamesInDb();
    }

    @GetMapping("/routing")
    public Set<String> getPersonNamesCachedRouting() {
        return neo4jRepository.getPersonNamesInDbRouting();
    }
}
