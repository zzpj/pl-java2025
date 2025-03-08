package com.example.optionalOrElseThrow;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DataServiceTest {
    DataService dataService = new DataService();


    @Test
    void shouldReturnUsernameForIdGreaterThan3() {
        String username = dataService.getUsername(4);

        assertEquals("user-4", username);
    }

    @Test
    void shouldThrowExceptionForIdLessThan3() {

        assertThrows(NoSuchElementException.class, () -> {
            dataService.getUsername(1);
        });
    }
}
