package com.example.transform;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportedCSVTextProcessorTest {

    @Test
    void process() {
        ImportedCSVTextProcessor sut = new ImportedCSVTextProcessor();

        String result = sut.process("\n \nLorem,Ipsum,is, simply,dummy,text none,of, the ,printing,and,typesetting,industry., Lorem,Ipsum,has,");

        assertEquals("LOREM IPSUM IS  SIMPLY DUMMY TEXT  OF  THE  PRINTING AND TYPESETTING INDUSTRY.  LOREM IPSUM HAS-DONE", result
        );

    }
}
