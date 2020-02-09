package com.graphaware.neo4j.experiment.security.drivers.neo4j;

import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CredentialsProvider {
    //hardcoded example
    private static Map<String, AuthToken> credentials = Map.of("admin", AuthTokens.basic("neo4j", "admin"),
            "documentReader", AuthTokens.basic("documentReader", "pwd"));

    //dummy mapping from real username to db username
    public String getDbUserForUsername(String name) {
        return name;
    }

    public AuthToken getCredentialsForDbName(String dbName) {
        if (!credentials.containsKey(dbName)) {
            throw new IllegalArgumentException("Credentials do not exist for " + dbName);
        }
        return credentials.get(dbName);
    }
}
