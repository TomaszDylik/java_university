package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductFinderTest {

    private final ProductFinder productFinder = new ProductFinder();

    @Test
    void shouldReturnEmptyForCheapestWhenArrayIsEmpty() {
        Optional<Product> result = productFinder.findCheapest(new Product[0]);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyForMostExpensiveWhenArrayIsEmpty() {
        Optional<Product> result = productFinder.findMostExpensive(new Product[0]);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnSingleProductAsCheapestAndMostExpensive() {
        Product product = new Product("CODE-001", "Kawa", 19.99);
        Product[] products = {product};

        assertSame(product, productFinder.findCheapest(products).orElseThrow());
        assertSame(product, productFinder.findMostExpensive(products).orElseThrow());
    }

    @Test
    void shouldFindCheapestProduct() {
        Product coffee = new Product("CODE-001", "Kawa", 19.99);
        Product sugar = new Product("CODE-002", "Cukier", 5.50);
        Product tea = new Product("CODE-003", "Herbata", 10.00);

        Product[] products = {coffee, sugar, tea};

        assertSame(sugar, productFinder.findCheapest(products).orElseThrow());
    }

    @Test
    void shouldFindMostExpensiveProduct() {
        Product coffee = new Product("CODE-001", "Kawa", 19.99);
        Product sugar = new Product("CODE-002", "Cukier", 5.50);
        Product tea = new Product("CODE-003", "Herbata", 10.00);

        Product[] products = {coffee, sugar, tea};

        assertSame(coffee, productFinder.findMostExpensive(products).orElseThrow());
    }

    @Test
    void shouldTreatZeroPriceAsValidCheapestProduct() {
        Product mug = new Product("CODE-001", "Kubek", 0.0);
        Product tea = new Product("CODE-002", "Herbata", 10.00);

        Product[] products = {tea, mug};

        assertEquals(0.0, productFinder.findCheapest(products).orElseThrow().getPrice(), 0.0001);
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> productFinder.findCheapest(null));
        assertThrows(NullPointerException.class, () -> productFinder.findMostExpensive(null));
    }

    @Test
    void shouldRejectArrayContainingNullProduct() {
        Product[] products = {
                new Product("CODE-001", "Kawa", 19.99),
                null
        };

        assertThrows(IllegalArgumentException.class, () -> productFinder.findCheapest(products));
        assertThrows(IllegalArgumentException.class, () -> productFinder.findMostExpensive(products));
    }
}