package com.example.patternMatchingForInstanceof.withNameShapes;

import com.example.patternMatchingForInstanceof.Shape;

public class Triangle extends Shape implements Named{
    @Override
    public String getName() {
        return "triangle";
    }
}
