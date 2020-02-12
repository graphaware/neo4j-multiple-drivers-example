package com.graphaware.neo4j.experiment.security.drivers.neo4j;

import com.graphaware.neo4j.experiment.security.drivers.utils.CredentialsProvider;
import com.graphaware.neo4j.experiment.security.drivers.utils.DbContextHolder;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("routingDriver")
public class RoutingDriver extends AbstractRoutingDriver {

    private final CredentialsProvider credentialsProvider;
    private final String uri;

    @Autowired
    public RoutingDriver(CredentialsProvider credentialsProvider, @Value("${neo4j.uri:bolt://localhost}") String uri) {
        this.credentialsProvider = credentialsProvider;
        this.uri = uri;
    }

    @Override
    protected String getDriverKey() {
        String userName = DbContextHolder.getUsername().orElseThrow(() -> new IllegalStateException("This should not happen"));
        return credentialsProvider.getDbUserForUsername(userName);
    }

    @Override
    protected Driver createDriver(String key) {
        return GraphDatabase.driver(uri, credentialsProvider.getCredentialsForDbName(key));
    }
}
