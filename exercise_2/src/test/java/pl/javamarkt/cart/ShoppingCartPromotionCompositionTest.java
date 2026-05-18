package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingCartPromotionCompositionTest {

    @Test
    void shouldApplySeveralPromotionsInDeclaredOrder() {
        Product coffee = new Product("CODE-001", "Kawa", 250.00);
        Product tea = new Product("CODE-002", "Herbata", 100.00);

        ShoppingCart shoppingCart = new ShoppingCart(List.of(coffee, tea))
                .withPromotion(new FivePercentDiscountAboveThresholdPromotion())
                .withPromotion(new FreeMugAboveThresholdPromotion());

        List<Product> result = shoppingCart.getProductsAfterPromotions();

        assertAll(
                () -> assertEquals(3, result.size()),
                () -> assertEquals(237.50, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(95.00, result.get(1).getDiscountPrice(), 0.0001),
                () -> assertEquals(FreeMugAboveThresholdPromotion.FREE_MUG_CODE, result.get(2).getCode()),
                () -> assertEquals(0.0, result.get(2).getDiscountPrice(), 0.0001),
                () -> assertEquals(332.50, calculateDiscountedTotal(result), 0.0001)
        );
    }

    @Test
    void shouldAllowAddingAndRemovingPromotionStrategiesDynamically() {
        Product coffee = new Product("CODE-001", "Kawa", 100.00);
        PromotionStrategy customPromotion = products -> List.of(products.get(0).withDiscountPrice(1.00));

        ShoppingCart baseCart = new ShoppingCart(List.of(coffee));
        ShoppingCart cartWithPromotion = baseCart.withPromotion(customPromotion);
        ShoppingCart cartWithoutPromotion = cartWithPromotion.withoutPromotion(customPromotion);

        assertAll(
                () -> assertEquals(100.00, baseCart.getProductsAfterPromotions().get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(1.00, cartWithPromotion.getProductsAfterPromotions().get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(100.00, cartWithoutPromotion.getProductsAfterPromotions().get(0).getDiscountPrice(), 0.0001)
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