package com.graphaware.neo4j.experiment.security.neo4j;

import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CredentialsProvider {
    private static Map<String, AuthToken> credentials = Map.of("admin", AuthTokens.basic("neo4j", "admin"),
            "documentReader", AuthTokens.basic("documentReader", "pwd"));

    public String getDbUserForUsername(String name) {
        return name;
    }

    public AuthToken getCredentialsForDbName(String dbName) {
        if (!credentials.containsKey(dbName)) {
            throw new IllegalArgumentException("Your argument sux!");
        }
        return credentials.get(dbName);
    }
}
