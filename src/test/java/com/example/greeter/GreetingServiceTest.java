package com.example.greeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GreetingServiceTest {

    @Test
    void greetsByName() {
        GreetingService svc = new GreetingService();
        assertEquals("Hello, Ada!", svc.greet("Ada"));
    }

    @Test
    void greetsWorldOnBlank() {
        GreetingService svc = new GreetingService();
        assertEquals("Hello, World!", svc.greet(""));
    }
}
