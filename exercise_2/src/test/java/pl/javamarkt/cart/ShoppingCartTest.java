package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShoppingCartTest {

    @Test
    void shouldReturnBasePricesWhenCartHasNoPromotions() {
        Product coffee = new Product("CODE-001", "Kawa", 19.99);
        Product tea = new Product("CODE-002", "Herbata", 10.00);

        ShoppingCart shoppingCart = new ShoppingCart(List.of(coffee, tea));

        List<Product> result = shoppingCart.getProductsAfterPromotions();

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(19.99, result.get(0).getPrice(), 0.0001),
                () -> assertEquals(19.99, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(10.00, result.get(1).getPrice(), 0.0001),
                () -> assertEquals(10.00, result.get(1).getDiscountPrice(), 0.0001)
        );
    }

    @Test
    void shouldApplySinglePromotionStrategyToCartProducts() {
        Product coffee = new Product("CODE-001", "Kawa", 100.00);
        Product tea = new Product("CODE-002", "Herbata", 40.00);
        PromotionStrategy promotionStrategy = products -> List.of(
                products.get(0).withDiscountPrice(95.00),
                products.get(1)
        );

        ShoppingCart shoppingCart = new ShoppingCart(List.of(coffee, tea), List.of(promotionStrategy));

        List<Product> result = shoppingCart.getProductsAfterPromotions();

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(100.00, result.get(0).getPrice(), 0.0001),
                () -> assertEquals(95.00, result.get(0).getDiscountPrice(), 0.0001),
                () -> assertEquals(40.00, result.get(1).getPrice(), 0.0001),
                () -> assertEquals(40.00, result.get(1).getDiscountPrice(), 0.0001)
        );
    }
}