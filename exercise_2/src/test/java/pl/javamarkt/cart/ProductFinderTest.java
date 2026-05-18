package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductFinderTest {

    private final ProductFinder productFinder = new ProductFinder();

    @Test
    void shouldReturnEmptyForCheapestWhenListIsEmpty() {
        Optional<Product> result = productFinder.findCheapest(List.of());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyForMostExpensiveWhenListIsEmpty() {
        Optional<Product> result = productFinder.findMostExpensive(List.of());

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnSingleProductAsCheapestAndMostExpensive() {
        Product product = new Product("CODE-001", "Kawa", 19.99);
        List<Product> products = List.of(product);

        assertSame(product, productFinder.findCheapest(products).orElseThrow());
        assertSame(product, productFinder.findMostExpensive(products).orElseThrow());
    }

    @Test
    void shouldFindCheapestProduct() {
        Product coffee = new Product("CODE-001", "Kawa", 19.99);
        Product sugar = new Product("CODE-002", "Cukier", 5.50);
        Product tea = new Product("CODE-003", "Herbata", 10.00);

        List<Product> products = List.of(coffee, sugar, tea);

        assertSame(sugar, productFinder.findCheapest(products).orElseThrow());
    }

    @Test
    void shouldFindMostExpensiveProduct() {
        Product coffee = new Product("CODE-001", "Kawa", 19.99);
        Product sugar = new Product("CODE-002", "Cukier", 5.50);
        Product tea = new Product("CODE-003", "Herbata", 10.00);

        List<Product> products = List.of(coffee, sugar, tea);

        assertSame(coffee, productFinder.findMostExpensive(products).orElseThrow());
    }

    @Test
    void shouldTreatZeroPriceAsValidCheapestProduct() {
        Product mug = new Product("CODE-001", "Kubek", 0.0);
        Product tea = new Product("CODE-002", "Herbata", 10.00);

        List<Product> products = List.of(tea, mug);

        assertEquals(0.0, productFinder.findCheapest(products).orElseThrow().getPrice(), 0.0001);
    }

    @Test
    void shouldRejectNullList() {
        assertThrows(NullPointerException.class, () -> productFinder.findCheapest(null));
        assertThrows(NullPointerException.class, () -> productFinder.findMostExpensive(null));
    }

    @Test
    void shouldRejectListContainingNullProduct() {
        List<Product> products = Arrays.asList(
                new Product("CODE-001", "Kawa", 19.99),
                null
        );

        assertThrows(IllegalArgumentException.class, () -> productFinder.findCheapest(products));
        assertThrows(IllegalArgumentException.class, () -> productFinder.findMostExpensive(products));
    }

    @Test
    void shouldReturnRequestedNumberOfCheapestProductsInAscendingOrder() {
        Product coffee = new Product("CODE-001", "Kawa", 19.99);
        Product sugar = new Product("CODE-002", "Cukier", 5.50);
        Product tea = new Product("CODE-003", "Herbata", 10.00);
        Product mug = new Product("CODE-004", "Kubek", 0.0);

        List<Product> products = List.of(coffee, sugar, tea, mug);

        List<Product> result = productFinder.findCheapestProducts(products, 2);

        assertEquals(List.of(mug, sugar), result);
    }

    @Test
    void shouldReturnRequestedNumberOfMostExpensiveProductsInDescendingOrder() {
        Product coffee = new Product("CODE-001", "Kawa", 19.99);
        Product sugar = new Product("CODE-002", "Cukier", 5.50);
        Product tea = new Product("CODE-003", "Herbata", 10.00);
        Product mug = new Product("CODE-004", "Kubek", 0.0);

        List<Product> products = List.of(coffee, sugar, tea, mug);

        List<Product> result = productFinder.findMostExpensiveProducts(products, 2);

        assertEquals(List.of(coffee, tea), result);
    }

    @Test
    void shouldReturnEmptyArrayWhenRequestedCountIsZero() {
        List<Product> products = List.of(new Product("CODE-001", "Kawa", 19.99));

        assertEquals(0, productFinder.findCheapestProducts(products, 0).size());
        assertEquals(0, productFinder.findMostExpensiveProducts(products, 0).size());
    }

    @Test
    void shouldReturnAllAvailableProductsWhenRequestedCountIsGreaterThanArrayLength() {
        Product coffee = new Product("CODE-001", "Kawa", 19.99);
        Product sugar = new Product("CODE-002", "Cukier", 5.50);

        List<Product> products = List.of(coffee, sugar);

        assertEquals(List.of(sugar, coffee), productFinder.findCheapestProducts(products, 10));
        assertEquals(List.of(coffee, sugar), productFinder.findMostExpensiveProducts(products, 10));
    }

    @Test
    void shouldRejectNegativeRequestedCount() {
        List<Product> products = List.of(new Product("CODE-001", "Kawa", 19.99));

        assertThrows(IllegalArgumentException.class, () -> productFinder.findCheapestProducts(products, -1));
        assertThrows(IllegalArgumentException.class, () -> productFinder.findMostExpensiveProducts(products, -1));
    }
}