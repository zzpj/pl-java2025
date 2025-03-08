package com.example.stringMethods;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringServiceTest {
    StringService sut = new StringService();

    @Test
    void shouldGetOnlyNotBlankStrings() {
        List<String> input = Arrays.
                asList(
                        "text1",
                        "----",
                        "",
                        "  ",
                        " A ",
                        "\n   ",
                        "  \r"
                );
        List<String> result = sut.getOnlyNotBlankStrings(input);

        assertEquals(Arrays.asList("text1", "----", " A "), result);
    }

    @Test
    void shouldGetStrippedTextLines() {
        String lorem = " Lorem ipsum dolor sit amet \n" +
                "   consectetur \n a\ndipiscing elit.\r" +
                "Mauris sed diam eleifend rhoncus sem non rhoncus velit.\n";
        List<String> result = sut.getStrippedTextLines(lorem);

        assertEquals(Arrays.asList("Lorem ipsum dolor sit amet","consectetur","a", "dipiscing elit.", "Mauris sed diam eleifend rhoncus sem non rhoncus velit."), result);
    }

    @Test
    void shouldExtendByRepeatFoundStringSomeTimes() {

        List<String> input = Arrays.asList("some", "example", "string", "list");

        List<String> result = sut.extendFoundStringByRepeatSomeTimes(input,"list", 3);

        assertEquals(Arrays.asList("some", "example", "string", "listlistlist"), result);
    }
}
