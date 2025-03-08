package com.example.streamToList;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvenNumberFilterTest {
    @Test
    void shouldReturnOnlyEvenNumbers() {

        EvenNumberFilter sut = new EvenNumberFilter();

        List<Integer> result = sut.getEvenNumbers(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

        assertEquals(List.of(2, 4, 6, 8), result);
    }
}
