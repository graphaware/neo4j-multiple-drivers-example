package com.graphaware.neo4j.experiment.security.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class Neo4jRepository {

    private final DriverProvider driverProvider;

    @Autowired
    public Neo4jRepository(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    public Set<String> getPersonNamesInDb() {
        return driverProvider.getDriver().session()
                .run("MATCH (n:Person) RETURN n.name")
                .stream()
                .map(r -> r.get("n.name").asString())
                .collect(Collectors.toSet());
    }

    public Set<String> getPersonNamesInDbNaive() {
        return driverProvider.createNewDriver().session()
                .run("MATCH (n:Person) RETURN n.name")
                .stream()
                .map(r -> r.get("n.name").asString())
                .collect(Collectors.toSet());
    }
}
