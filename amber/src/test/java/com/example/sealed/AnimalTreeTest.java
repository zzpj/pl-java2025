package com.example.sealed;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class AnimalTreeTest {

    /*
    How Animal tree inheritance should look:
    Animal ---
              |
              |---   Reptile
              |---   Mammal ---
                               |
                               |--- Elephant
                               |--- Human
                               |--- Dog ---
                                           |
                                           |--- GermanShepherd

     Requirements:
        * Only Reptile and Mammal can extend Animal
        * Any class can extend Mammal.
        * There is no way to extend GermanShepherd
     */

    @Test
    void shouldReptileIsChildOfAnimal() {
        Object reptile = new Reptile();

        assertTrue(reptile instanceof Animal);
    }

    @Test
    void shouldMammalIsChildOfAnimal() {
        Object object = new Mammal();
        assertTrue(object instanceof Animal);
    }

    @Test
    void shouldElephantIsChildOfMammal() {
        Object object = new Elephant();
        assertTrue(object instanceof Mammal);
    }

    @Test
    void shouldHumanIsChildOfMammal() {
        Object object = new Human();
        assertTrue(object instanceof Mammal);
    }

    @Test
    void shouldDogIsChildOfMammal() {
        Object object = new Dog();
        assertTrue(object instanceof Mammal);
    }

    @Test
    void shouldGermanShepherdIsChildOfDog() {
        Object object = new GermanShepherd();
        assertTrue(object instanceof Dog);
    }

    @Test
    void shouldGermanShepherdIsFinal() {
        Object object = new GermanShepherd();
        assertTrue(Modifier.isFinal(GermanShepherd.class.getModifiers()));
    }
}






















































/*
 SOLUTION:
    public sealed class Animal permits  Mammal, Reptile
    public class Dog extends Mammal
    public class Elephant extends Mammal
    public final class GermanShepherd extends Dog
    public class Human extends  Mammal
    public non-sealed class Mammal extends Animal
    public non-sealed class Reptile extends Animal
 */
