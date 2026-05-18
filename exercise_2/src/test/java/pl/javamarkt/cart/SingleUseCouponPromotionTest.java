package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleUseCouponPromotionTest {

    @Test
    void shouldApplyThirtyPercentDiscountOnlyToSelectedProduct() {
        SingleUseCouponPromotion promotion = new SingleUseCouponPromotion("CODE-002");
        Product coffee = new Product("CODE-001", "Kawa", 100.00);
        Product tea = new Product("CODE-002", "Herbata", 50.00);

        List<Product> result = promotion.apply(List.of(coffee, tea));

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(100.00, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(35.00, result.get(1).getDiscountPrice(), 0.0001),
                () -> assertEquals(50.00, tea.getDiscountPrice(), 0.0001)
        );
    }

    @Test
    void shouldNotApplyCouponTwiceToTheSameProduct() {
        SingleUseCouponPromotion promotion = new SingleUseCouponPromotion("CODE-001");
        Product coffee = new Product("CODE-001", "Kawa", 100.00);

        List<Product> firstResult = promotion.apply(List.of(coffee));
        List<Product> secondResult = promotion.apply(firstResult);

        assertAll(
                () -> assertEquals(70.00, firstResult.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(70.00, secondResult.get(0).getDiscountPrice(), 0.0001)
        );
    }

    @Test
    void shouldLeaveProductsUnchangedWhenSelectedProductDoesNotExist() {
        SingleUseCouponPromotion promotion = new SingleUseCouponPromotion("CODE-999");
        Product coffee = new Product("CODE-001", "Kawa", 100.00);

        List<Product> result = promotion.apply(List.of(coffee));

        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertEquals("CODE-001", result.get(0).getCode()),
                () -> assertEquals(100.00, result.get(0).getDiscountPrice(), 0.0001)
        );
    }
}