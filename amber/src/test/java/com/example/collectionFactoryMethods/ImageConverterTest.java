package com.example.collectionFactoryMethods;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageConverterTest {

    @Test
    void shouldReturnImmutableFileKeysList() {
        ImageConverter sut = new ImageConverter();

        Set<String> result = sut.getAvailableFileKeys();

        assertEquals(3, result.size());
        assertThrows(UnsupportedOperationException.class, () -> {
            result.add("gif");
        });
    }
}
