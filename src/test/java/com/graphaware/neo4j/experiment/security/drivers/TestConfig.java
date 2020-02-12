package com.graphaware.neo4j.experiment.security.drivers;

import com.graphaware.neo4j.experiment.security.drivers.neo4j.CredentialsProvider;
import com.graphaware.neo4j.experiment.security.drivers.neo4j.DriverProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.Neo4jContainer;

@Configuration
public class TestConfig {

    private static Neo4jContainer neo4j = new Neo4jContainer<>("neo4j:4.0.0-enterprise")
            .withEnv("NEO4J_ACCEPT_LICENSE_AGREEMENT", "yes")
            .withAdminPassword("admin");

    @Bean
    public DriverProvider getDriverProvider(CredentialsProvider credentialsProvider) {
        if (!neo4j.isCreated()) {
            neo4j.start();
        }
        return new DriverProvider(credentialsProvider, neo4j.getBoltUrl());
    }
}
