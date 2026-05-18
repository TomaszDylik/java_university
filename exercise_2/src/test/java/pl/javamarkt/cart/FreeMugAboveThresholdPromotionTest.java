package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FreeMugAboveThresholdPromotionTest {

    private final FreeMugAboveThresholdPromotion promotion = new FreeMugAboveThresholdPromotion();

    @Test
    void shouldAddFreeMugWhenCurrentOrderValueIsGreaterThan200() {
        Product coffee = new Product("CODE-001", "Kawa", 150.00);
        Product tea = new Product("CODE-002", "Herbata", 60.00);

        List<Product> result = promotion.apply(List.of(coffee, tea));

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(150.00, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(60.00, result.get(1).getDiscountPrice(), 0.0001),
                () -> assertEquals(FreeMugAboveThresholdPromotion.FREE_MUG_CODE, result.get(2).getCode()),
                () -> assertEquals(FreeMugAboveThresholdPromotion.FREE_MUG_NAME, result.get(2).getName()),
                () -> assertEquals(0.0, result.get(2).getPrice(), 0.0001),
                () -> assertEquals(0.0, result.get(2).getDiscountPrice(), 0.0001),
                () -> assertEquals(210.00, calculateDiscountedTotal(result), 0.0001)
        );
    }

    @Test
    void shouldNotAddFreeMugWhenCurrentOrderValueDoesNotExceed200() {
        Product coffee = new Product("CODE-001", "Kawa", 150.00);
        Product tea = new Product("CODE-002", "Herbata", 50.00);

        List<Product> result = promotion.apply(List.of(coffee, tea));

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(150.00, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(50.00, result.get(1).getDiscountPrice(), 0.0001),
                () -> assertEquals(200.00, calculateDiscountedTotal(result), 0.0001)
        );
    }

    private double calculateDiscountedTotal(List<Product> products) {
        double total = 0.0;

        for (Product product : products) {
            total += product.getDiscountPrice();
        }

        return total;
    }
}