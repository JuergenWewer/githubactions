package com.example.greeter;

import jakarta.inject.Singleton;

/**
 * Minimal library service. Can be used from any Java app.
 * If used in Quarkus, @Singleton will be picked up via CDI.
 */
@Singleton
public class GreetingService {

    public String greet(String name) {
        String n = (name == null || name.isBlank()) ? "World" : name.trim();
        return "Hello, " + n + "!";
    }
}
