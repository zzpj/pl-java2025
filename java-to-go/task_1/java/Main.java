import java.util.*;

public class Main {
    public static void main(String[] args) {
      List<Advertisement> adverts = List.of(
            new Billboard(12, 168, 120),
            new Radio(15, 1000),
            new Newspaper(450, 79)
        );

      for (Advertisement advert : adverts) {
            System.out.println("Price: " + advert.calculatePrice());
      }

      int finalPrice = adverts.stream()
              .mapToInt(Advertisement::calculatePrice)
              .sum();

      System.out.println("Final price: " + finalPrice);
    }
}