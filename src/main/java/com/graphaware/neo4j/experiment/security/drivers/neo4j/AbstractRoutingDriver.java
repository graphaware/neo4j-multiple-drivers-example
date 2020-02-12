package com.graphaware.neo4j.experiment.security.drivers.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Metrics;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.reactive.RxSession;
import org.neo4j.driver.types.TypeSystem;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRoutingDriver implements Driver {

    private final ConcurrentHashMap<String, Driver> existingDrivers;

    public AbstractRoutingDriver() {
        existingDrivers = new ConcurrentHashMap<>();
    }

    protected abstract String getDriverKey();

    protected abstract Driver createDriver(String key);

    private Driver getDriver() {
        return existingDrivers.computeIfAbsent(getDriverKey(), this::createDriver);
    }

    @Override
    public void close() {
        Driver driver = existingDrivers.remove(getDriverKey());
        if (driver != null) {
            driver.close();
        }
    }

    @Override
    public CompletionStage<Void> closeAsync() {
        Driver driver = existingDrivers.remove(getDriverKey());
        if (driver != null) {
            return driver.closeAsync();
        } else {
            return CompletableFuture.runAsync(() -> {
            });
        }
    }

    @Override
    public boolean isEncrypted() {
        return getDriver().isEncrypted();
    }

    @Override
    public Session session() {
        return getDriver().session();
    }

    @Override
    public Session session(SessionConfig sessionConfig) {
        return getDriver().session(sessionConfig);
    }

    @Override
    public RxSession rxSession() {
        return getDriver().rxSession();
    }

    @Override
    public RxSession rxSession(SessionConfig sessionConfig) {
        return getDriver().rxSession(sessionConfig);
    }

    @Override
    public AsyncSession asyncSession() {
        return getDriver().asyncSession();
    }

    @Override
    public AsyncSession asyncSession(SessionConfig sessionConfig) {
        return getDriver().asyncSession(sessionConfig);
    }

    @Override
    public Metrics metrics() {
        return getDriver().metrics();
    }

    @Override
    public boolean isMetricsEnabled() {
        return getDriver().isMetricsEnabled();
    }

    @Override
    public TypeSystem defaultTypeSystem() {
        return getDriver().defaultTypeSystem();
    }

    @Override
    public void verifyConnectivity() {
        getDriver().verifyConnectivity();
    }

    @Override
    public CompletionStage<Void> verifyConnectivityAsync() {
        return getDriver().verifyConnectivityAsync();
    }

    @Override
    public boolean supportsMultiDb() {
        return getDriver().supportsMultiDb();
    }

    @Override
    public CompletionStage<Boolean> supportsMultiDbAsync() {
        return getDriver().supportsMultiDbAsync();
    }
}
