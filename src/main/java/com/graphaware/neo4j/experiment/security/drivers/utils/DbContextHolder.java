package com.graphaware.neo4j.experiment.security.drivers.utils;

import java.util.Optional;

//Just placeholder, in real application user from Spring Security would be used
public class DbContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static Optional<String> getUsername() {
        return Optional.ofNullable(contextHolder.get());
    }

    public static void setUsername(String user) {
        if (user == null) {
            throw new NullPointerException("User name not provided!");
        }
        contextHolder.set(user);
    }

    public static void clearDbUser() {
        contextHolder.remove();
    }
}
