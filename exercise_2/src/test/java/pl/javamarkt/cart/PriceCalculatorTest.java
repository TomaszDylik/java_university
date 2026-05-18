package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceCalculatorTest {

    private final PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    void shouldReturnZeroForEmptyArray() {
        Product[] products = new Product[0];

        assertEquals(0.0, priceCalculator.sum(products), 0.0001);
    }

    @Test
    void shouldReturnSumOfAllProductPrices() {
        Product[] products = {
                new Product("CODE-001", "Kawa", 19.99),
                new Product("CODE-002", "Herbata", 10.00),
                new Product("CODE-003", "Cukier", 5.50)
        };

        assertEquals(35.49, priceCalculator.sum(products), 0.0001);
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> priceCalculator.sum(null));
    }

    @Test
    void shouldRejectArrayContainingNullProduct() {
        Product[] products = {
                new Product("CODE-001", "Kawa", 19.99),
                null
        };

        assertThrows(IllegalArgumentException.class, () -> priceCalculator.sum(products));
    }
}