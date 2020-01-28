package com.graphaware.neo4j.experiment.security.neo4j;

import java.util.Optional;

public class DbContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static Optional<String> getUsername() {
        return Optional.ofNullable(contextHolder.get());
    }

    public static void setUsername(String user) {
        if (user == null) {
            throw new NullPointerException("Screw you!");
        }
        contextHolder.set(user);
    }

    public static void clearDbUser() {
        contextHolder.remove();
    }
}
