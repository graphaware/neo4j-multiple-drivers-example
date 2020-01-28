package com.graphaware.neo4j.experiment.security.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DriverProvider {

    private final CredentialsProvider credentialsProvider;
    private final Map<String, Driver> existingDrivers;

    @Autowired
    public DriverProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
        existingDrivers = new HashMap<>();
    }

    public Driver getDriver() {
        String userName = DbContextHolder.getUsername().orElseThrow(() -> new RuntimeException("impossible"));
        String dbUser = credentialsProvider.getDbUserForUsername(userName);
        if (existingDrivers.containsKey(dbUser)) {
            return existingDrivers.get(userName);
        } else {
            Driver driver = GraphDatabase.driver("bolt://localhost", credentialsProvider.getCredentialsForDbName(dbUser));
            existingDrivers.put(dbUser, driver);
            return driver;
        }
    }

    //just for load test comparison
    public Driver createNewDriver() {
        String userName = DbContextHolder.getUsername().orElseThrow(() -> new RuntimeException("impossible"));
        String dbUser = credentialsProvider.getDbUserForUsername(userName);
        return GraphDatabase.driver("bolt://localhost", credentialsProvider.getCredentialsForDbName(dbUser));
    }
}
