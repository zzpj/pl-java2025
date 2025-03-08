package com.example.indent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoIndentTextFormatterTest {

    NoIndentTextFormatter noIndentTextFormatter = new NoIndentTextFormatter();

    @Test
    void noIndentText() {
        String text = "                               some code with big\n          indent\n";

        String result = noIndentTextFormatter.noIndentText(text);

        assertEquals("some code with big\nindent\n", result);
    }
}
