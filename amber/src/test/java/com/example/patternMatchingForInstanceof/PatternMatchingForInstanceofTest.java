package com.example.patternMatchingForInstanceof;

import com.example.patternMatchingForInstanceof.withDisplayName.Circle;
import com.example.patternMatchingForInstanceof.withDisplayName.Pentagon;
import com.example.patternMatchingForInstanceof.withNameShapes.Square;
import com.example.patternMatchingForInstanceof.withNameShapes.Triangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatternMatchingForInstanceofTest {
    private ShapeDisplayNameResolver sut = new ShapeDisplayNameResolver();

    @Test
    void shouldProvideCircleShapeDisplayName() {
        ShapeDisplayNameResolver sut = new ShapeDisplayNameResolver();
        Circle circle = new Circle();

        String result = sut.resolveShape(circle);

        assertEquals("This is circle name!", result);
    }

    @Test
    void shouldProvideSquareShapeDisplayName() {
        ShapeDisplayNameResolver sut = new ShapeDisplayNameResolver();
        Square square = new Square();

        String result = sut.resolveShape(square);

        assertEquals("This is square name!", result);
    }

    @Test
    void shouldProvideTriangleShapeDisplayName() {
        ShapeDisplayNameResolver sut = new ShapeDisplayNameResolver();
        Triangle triangle = new Triangle();

        String result = sut.resolveShape(triangle);

        assertEquals("This is triangle name!", result);
    }
    @Test
    void shouldProvidePentagonShapeDisplayName() {
        ShapeDisplayNameResolver sut = new ShapeDisplayNameResolver();
        Pentagon pentagon = new Pentagon();

        String result = sut.resolveShape(pentagon);

        assertEquals("This is pentagon name!", result);
    }
}
