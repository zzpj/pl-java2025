package com.example.textBlock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HtmlCodeTest {

    @Test
    void getHtml() {
        HtmlCode htmlCode = new HtmlCode();

        String result = htmlCode.getHtml();

        assertEquals("<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>My test page</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <img src=\"images/firefox-icon.png\" alt=\"My test image\">\n" +
                "  </body>\n" +
                "</html>\n", result);
    }
}
