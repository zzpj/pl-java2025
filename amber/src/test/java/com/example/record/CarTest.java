package com.example.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void shouldSaveMarkAndCapacity() {
        Car car = new Car("Fiat", 1400);

        assertEquals("Fiat", car.make());
        assertEquals(1400, car.capacity());
    }

    @Test
    void shouldThrowExceptionForNegativeCapacityValue() {

        assertThrows(IllegalArgumentException.class, () -> {
            new Car("Fiat", -2);
        });
    }
}
