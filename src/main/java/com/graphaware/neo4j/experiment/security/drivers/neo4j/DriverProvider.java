package com.graphaware.neo4j.experiment.security.drivers.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class DriverProvider {

    private final CredentialsProvider credentialsProvider;
    private final ConcurrentHashMap<String, Driver> existingDrivers;

    @Autowired
    public DriverProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
        existingDrivers = new ConcurrentHashMap<>();
    }

    public Driver getDriver() {
        String userName = DbContextHolder.getUsername().orElseThrow(() -> new IllegalStateException("This should not happen"));
        String dbUser = credentialsProvider.getDbUserForUsername(userName);
        return existingDrivers.computeIfAbsent(dbUser,
                (dbUserName) -> GraphDatabase.driver("bolt://localhost", credentialsProvider.getCredentialsForDbName(dbUserName)));
    }

    //just for load test comparison
    public Driver createNewDriver() {
        String userName = DbContextHolder.getUsername().orElseThrow(() -> new IllegalStateException("This should not happen"));
        String dbUser = credentialsProvider.getDbUserForUsername(userName);
        return GraphDatabase.driver("bolt://localhost", credentialsProvider.getCredentialsForDbName(dbUser));
    }
}
