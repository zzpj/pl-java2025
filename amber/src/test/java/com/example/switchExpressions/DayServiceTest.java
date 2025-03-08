package com.example.switchExpressions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DayServiceTest {
    DayService dayService = new DayService();

    @Test
    void shouldReturnProperValueForMonday() {
        int result = dayService.getDayNumberOfLettersForWorkday(Day.MONDAY);

        assertEquals(6, result);
    }

    @Test
    void shouldReturnProperValueForTuesday() {
        int result = dayService.getDayNumberOfLettersForWorkday(Day.TUESDAY);

        assertEquals(7, result);
    }

    @Test
    void shouldReturnProperValueForThursday() {
        int result = dayService.getDayNumberOfLettersForWorkday(Day.THURSDAY);

        assertEquals(8, result);
    }

    @Test
    void shouldReturnProperValueForWednesday() {
        int result = dayService.getDayNumberOfLettersForWorkday(Day.WEDNESDAY);

        assertEquals(9, result);
    }

    @Test
    void shouldThrowExceptionForNonWorkingDay() {

        assertThrows(IllegalStateException.class, () -> {
              dayService.getDayNumberOfLettersForWorkday(Day.SUNDAY);
        });
    }
}
