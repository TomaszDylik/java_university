package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FivePercentDiscountAboveThresholdPromotionTest {

    private final FivePercentDiscountAboveThresholdPromotion promotion =
            new FivePercentDiscountAboveThresholdPromotion();

    @Test
    void shouldApplyFivePercentDiscountWhenCurrentOrderValueIsGreaterThan300() {
        Product coffee = new Product("CODE-001", "Kawa", 200.00).withDiscountPrice(180.00);
        Product tea = new Product("CODE-002", "Herbata", 140.00);

        List<Product> result = promotion.apply(List.of(coffee, tea));

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(200.00, result.get(0).getPrice(), 0.0001),
                () -> assertEquals(171.00, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(140.00, result.get(1).getPrice(), 0.0001),
                () -> assertEquals(133.00, result.get(1).getDiscountPrice(), 0.0001),
                () -> assertEquals(180.00, coffee.getDiscountPrice(), 0.0001)
        );
    }

    @Test
    void shouldNotApplyDiscountWhenCurrentOrderValueIsExactly300() {
        Product coffee = new Product("CODE-001", "Kawa", 200.00).withDiscountPrice(160.00);
        Product tea = new Product("CODE-002", "Herbata", 140.00);

        List<Product> result = promotion.apply(List.of(coffee, tea));

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(160.00, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(140.00, result.get(1).getDiscountPrice(), 0.0001)
        );
    }
}