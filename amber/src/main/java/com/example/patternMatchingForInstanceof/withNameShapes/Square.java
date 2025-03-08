package com.example.patternMatchingForInstanceof.withNameShapes;

import com.example.patternMatchingForInstanceof.Shape;

public class Square extends Shape implements Named {
    @Override
    public String getName() {
        return "square";
    }
}
