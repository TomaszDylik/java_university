package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BuyTwoGetCheapestFreePromotionTest {

    private final BuyTwoGetCheapestFreePromotion promotion = new BuyTwoGetCheapestFreePromotion();

    @Test
    void shouldMakeCheapestProductFreeWhenCartContainsThreeProducts() {
        Product coffee = new Product("CODE-001", "Kawa", 20.00);
        Product tea = new Product("CODE-002", "Herbata", 10.00);
        Product mug = new Product("CODE-003", "Kubek", 5.00);

        List<Product> result = promotion.apply(List.of(coffee, tea, mug));

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(20.00, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(10.00, result.get(1).getDiscountPrice(), 0.0001),
                () -> assertEquals(0.0, result.get(2).getDiscountPrice(), 0.0001),
                () -> assertEquals(5.00, mug.getDiscountPrice(), 0.0001)
        );
    }

    @Test
    void shouldNotApplyPromotionWhenCartContainsOnlyTwoProducts() {
        Product coffee = new Product("CODE-001", "Kawa", 20.00);
        Product tea = new Product("CODE-002", "Herbata", 10.00);

        List<Product> result = promotion.apply(List.of(coffee, tea));

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(20.00, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(10.00, result.get(1).getDiscountPrice(), 0.0001)
        );
    }

    @Test
    void shouldChooseCheapestProductFromLargerCartUsingCurrentDiscountPrices() {
        Product coffee = new Product("CODE-001", "Kawa", 100.00);
        Product tea = new Product("CODE-002", "Herbata", 50.00);
        Product mug = new Product("CODE-003", "Kubek", 20.00).withDiscountPrice(4.00);
        Product sugar = new Product("CODE-004", "Cukier", 6.00);

        List<Product> result = promotion.apply(List.of(coffee, tea, mug, sugar));

        assertAll(
                () -> assertEquals(4, result.size()),
                () -> assertEquals(100.00, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(50.00, result.get(1).getDiscountPrice(), 0.0001),
                () -> assertEquals(0.0, result.get(2).getDiscountPrice(), 0.0001),
                () -> assertEquals(6.00, result.get(3).getDiscountPrice(), 0.0001),
                () -> assertEquals(4.00, mug.getDiscountPrice(), 0.0001)
        );
    }
}