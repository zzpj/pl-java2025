package com.example.indent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextBlockFormatterTest {
    TextBlockFormatter textBlockFormatter = new TextBlockFormatter();

    @Test
    void formatLinesToCodeBlockIndent() {
        String text = "Code line 1\nCode line 2\nCode line 3\n";


        String result = textBlockFormatter.formatLinesToCodeBlockIndent(text);

        assertEquals(
                "    Code line 1\n" +
                        "    Code line 2\n" +
                        "    Code line 3\n",
                result);
    }
}
