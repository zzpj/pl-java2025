package com.example.unmodifiableCollections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyServiceTest {
    MyService myService = new MyService();


    @Test
    void getUnmodifiableListByCollector() {
        List<Integer> result = myService.getUnmodifiableListByCollector();

        assertThrows(UnsupportedOperationException.class, () -> {
            result.add(5);
        });
    }

    @Test
    void getUnmodifiableListByCopy() {
        List<Integer> result = myService.getUnmodifiableListByCopy();

        assertThrows(UnsupportedOperationException.class, () -> {
            result.add(5);
        });

    }
}
