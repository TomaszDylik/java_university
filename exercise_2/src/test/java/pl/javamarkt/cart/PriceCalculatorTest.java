package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PriceCalculatorTest {

    private final PriceCalculator priceCalculator = new PriceCalculator();

    @Test
    void shouldReturnZeroForEmptyList() {
        List<Product> products = List.of();

        assertEquals(0.0, priceCalculator.sum(products), 0.0001);
    }

    @Test
    void shouldReturnSumOfAllProductPrices() {
        List<Product> products = List.of(
                new Product("CODE-001", "Kawa", 19.99),
                new Product("CODE-002", "Herbata", 10.00),
                new Product("CODE-003", "Cukier", 5.50)
        );

        assertEquals(35.49, priceCalculator.sum(products), 0.0001);
    }

    @Test
    void shouldRejectNullList() {
        assertThrows(NullPointerException.class, () -> priceCalculator.sum(null));
    }

    @Test
    void shouldRejectListContainingNullProduct() {
        List<Product> products = Arrays.asList(
                new Product("CODE-001", "Kawa", 19.99),
                null
        );

        assertThrows(IllegalArgumentException.class, () -> priceCalculator.sum(products));
    }
}