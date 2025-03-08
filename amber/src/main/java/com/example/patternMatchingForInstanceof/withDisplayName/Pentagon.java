package com.example.patternMatchingForInstanceof.withDisplayName;

import com.example.patternMatchingForInstanceof.Shape;

public class Pentagon extends Shape implements DisplayNamed {
    @Override
    public String getDisplayName() {
        return "This is pentagon name!";
    }
}
