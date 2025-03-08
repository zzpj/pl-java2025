package com.example.fileMismatch;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileEqualsCheckerTest {
    FileEqualsChecker checker = new FileEqualsChecker();

    @Test
    void shouldReturnTrueIfFilesAreEquals() throws IOException {
        Path path1 = Files.createTempFile("text1", ".txt");
        Files.writeString(path1,"value1");

        Path path2 = Files.createTempFile("text2", ".txt");
        Files.writeString(path2,"value1");


        boolean result = checker.isFileEquals(path1, path2);

        assertTrue(result);
    }
    @Test
    void shouldReturnFalseIfFilesAreNotEquals() throws IOException {
        Path path1 = Files.createTempFile("text1", ".txt");
        Files.writeString(path1,"value1");

        Path path2 = Files.createTempFile("text2", ".txt");
        Files.writeString(path2,"value2");


        boolean result = checker.isFileEquals(path1, path2);

        assertFalse(result);
    }
}
