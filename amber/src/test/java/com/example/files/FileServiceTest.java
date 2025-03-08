package com.example.files;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    @Test
    void shouldReturnFalseIfNotFound() throws IOException {
        FileService service = new FileService();
        Path filePath = Files.writeString(Files.createTempFile("file", ".txt"), "Sample text");

        boolean result = service.isFileContainsText(filePath, "not found");

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueIfFound() throws IOException {
        FileService service = new FileService();
        Path filePath = Files.writeString(Files.createTempFile("file", ".txt"), "Sample text ");

        boolean result = service.isFileContainsText(filePath, "Sample text");

        assertTrue(result);
    }
}
