package com.graphaware.neo4j.experiment.security.drivers.neo4j;

import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class Neo4jRepository {

    private final Driver driverProvider;
    private final Driver naive;
    private final Driver routingDriver;

    @Autowired
    public Neo4jRepository(@Qualifier("provider") Driver driverProvider,
                           @Qualifier("routingDriver") Driver routing,
                           @Qualifier("naive") Driver naive) {
        this.driverProvider = driverProvider;
        this.routingDriver = routing;
        this.naive = naive;
    }

    public Set<String> getPersonNamesInDbNaive() {
        return naive.session()
                .run("MATCH (n:Person) RETURN n.name")
                .stream()
                .map(r -> r.get("n.name").asString())
                .collect(Collectors.toSet());
    }

    public Set<String> getPersonNamesInDb() {
        return driverProvider.session()
                .run("MATCH (n:Person) RETURN n.name")
                .stream()
                .map(r -> r.get("n.name").asString())
                .collect(Collectors.toSet());
    }

    public Set<String> getPersonNamesInDbRouting() {
        return routingDriver.session()
                .run("MATCH (n:Person) RETURN n.name")
                .stream()
                .map(r -> r.get("n.name").asString())
                .collect(Collectors.toSet());
    }
}
