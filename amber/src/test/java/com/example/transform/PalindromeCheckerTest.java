package com.example.transform;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PalindromeCheckerTest {

    PalindromeChecker checker = new PalindromeChecker();

    @ParameterizedTest
    @ValueSource(strings = {"zaraz","kajak","anna","sedes","mam", "ala","radar"})
    void shouldReturnTrueForPalindrome(String text) {
        boolean result = checker.isPalindrome(text);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForNonPalindrome() {
        boolean result = checker.isPalindrome("Programowanie");

        assertFalse(result);
    }
}
