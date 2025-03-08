package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvironmentSetupTest {

    @Test
    void shouldRunOnJava22() {
        int feature = Runtime.version().feature();
        assertEquals(22, feature);
    }
}
