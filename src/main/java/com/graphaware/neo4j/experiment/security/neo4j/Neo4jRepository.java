package com.graphaware.neo4j.experiment.security.neo4j;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class Neo4jRepository {

    private final DriverProvider driverProvider;
    private final Driver driver;

    @Autowired
    public Neo4jRepository(DriverProvider driverProvider, Driver driver) {
        this.driverProvider = driverProvider;
        this.driver = driver;
    }

    public Set<String> getPersonNamesInDb() {
        return driver.session()
                .run("MATCH (n:Person) RETURN n.name")
                .stream()
                .map(r -> r.get("n.name").asString())
                .collect(Collectors.toSet());
    }

    public Set<String> getPersonNamesInDbNaive() {
        try (Driver driver = driverProvider.createNewDriver()) {
            return driver.session()
                    .run("MATCH (n:Person) RETURN n.name")
                    .stream()
                    .map(r -> r.get("n.name").asString())
                    .collect(Collectors.toSet());
        }
    }
}
