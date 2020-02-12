package com.graphaware.neo4j.experiment.security.drivers.neo4j;

import com.graphaware.neo4j.experiment.security.drivers.utils.CredentialsProvider;
import com.graphaware.neo4j.experiment.security.drivers.utils.DbContextHolder;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class DriverProvider {

    private final CredentialsProvider credentialsProvider;
    private final ConcurrentHashMap<String, Driver> existingDrivers;
    private final String uri;

    @Autowired
    public DriverProvider(CredentialsProvider credentialsProvider, @Value("${neo4j.uri:bolt://localhost}") String uri) {
        this.credentialsProvider = credentialsProvider;
        existingDrivers = new ConcurrentHashMap<>();
        this.uri = uri;
    }

    public Driver getDriver() {
        String userName = DbContextHolder.getUsername().orElseThrow(() -> new IllegalStateException("This should not happen"));
        String dbUser = credentialsProvider.getDbUserForUsername(userName);
        return existingDrivers.computeIfAbsent(dbUser,
                (dbUserName) -> GraphDatabase.driver(uri, credentialsProvider.getCredentialsForDbName(dbUserName)));
    }

    //just for load test comparison
    public Driver createNewDriver() {
        String userName = DbContextHolder.getUsername().orElseThrow(() -> new IllegalStateException("This should not happen"));
        String dbUser = credentialsProvider.getDbUserForUsername(userName);
        return GraphDatabase.driver(uri, credentialsProvider.getCredentialsForDbName(dbUser));
    }
}
