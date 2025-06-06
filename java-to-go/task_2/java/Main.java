import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            new Person("Jan", "Kowalski", 39);
        } catch (ValidationException ve) {
            System.out.println("Validation error occured: " + ve.getMessage());
        }

        try {
            new Person("Krzysztof", "Nowak", -1);
        } catch (ValidationException ve) {
            System.out.println("Validation error occured: " + ve.getMessage());
        }
    }
}