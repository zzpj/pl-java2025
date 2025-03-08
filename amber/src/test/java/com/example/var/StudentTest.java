package com.example.var;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    Student sut = new Student(new Integer[]{2, 2, 3, 3, 3, 4, 4});

    @Test
    void shouldGetNumberOfMarks() {
        Integer result = sut.getNumberOfMarks();

        assertEquals(7, result);
    }

    @Test
    void shouldGetTotalSumOfMarks() {
        Integer result = sut.getTotalSumOfMarks();

        assertEquals(21, result);
    }

    @Test
    void shouldGetAverageOfMarks() {
        BigDecimal result = sut.getAverageOfMarks();

        assertEquals(3, result.intValue());
    }

    @Test
    void shouldGetMaximumMark() {
        Integer result = sut.getMaximumMark();
        assertEquals(4, result.intValue());
    }

    @Test
    void shouldGetMinimumMark() {
        Integer result = sut.getMinimumMark();

        assertEquals(2, result.intValue());
    }

    @Test
    void shouldGetDistinctMarks() {
        List<Integer> result = sut.getDistinctMarks();

        assertEquals(Arrays.asList(2, 3, 4), result);
    }
}
