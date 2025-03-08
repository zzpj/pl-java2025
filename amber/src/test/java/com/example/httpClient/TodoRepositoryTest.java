package com.example.httpClient;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class TodoRepositoryTest {

    @Test
    void getTodo() throws URISyntaxException, IOException, InterruptedException {
        TodoRepository repository = new TodoRepository();

        String result = repository.getTodo();
        assertEquals("{\n" +
                "  \"userId\": 1,\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"delectus aut autem\",\n" +
                "  \"completed\": false\n" +
                "}", result);
    }
}
