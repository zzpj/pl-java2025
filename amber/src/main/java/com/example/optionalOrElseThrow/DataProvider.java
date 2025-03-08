package com.example.optionalOrElseThrow;

import java.util.Optional;

public class DataProvider {

    // do not change
    Optional<String> getUsername(int id) {
        return id < 3
                ? Optional.empty()
                : Optional.of("user-" + id);
    }

}
