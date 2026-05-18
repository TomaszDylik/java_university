package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductSorterTest {

    private final ProductSorter productSorter = new ProductSorter();

    @Test
    void shouldSortProductsByPriceDescending() {
        Product coffee = new Product("CODE-001", "Kawa", 19.99);
        Product tea = new Product("CODE-002", "Herbata", 10.00);
        Product sugar = new Product("CODE-003", "Cukier", 5.50);

        List<Product> products = List.of(tea, sugar, coffee);

        List<Product> result = productSorter.sort(products, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC);

        assertEquals(List.of(coffee, tea, sugar), result);
    }

    @Test
    void shouldSortAlphabeticallyWhenPricesAreEqual() {
        Product bananas = new Product("CODE-001", "Banany", 10.00);
        Product apples = new Product("CODE-002", "Jablka", 10.00);
        Product tea = new Product("CODE-003", "Herbata", 5.00);

        List<Product> products = List.of(bananas, tea, apples);

        List<Product> result = productSorter.sort(products, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC);

        assertEquals(List.of(bananas, apples, tea), result);
    }

    @Test
    void shouldReturnEmptyListWhenInputIsEmpty() {
        List<Product> result = productSorter.sort(List.of(), ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC);

        assertEquals(0, result.size());
    }

    @Test
    void shouldAllowChangingSortStrategyAtRuntime() {
        Product tea = new Product("CODE-001", "Herbata", 10.00);
        Product coffee = new Product("CODE-002", "Kawa", 19.99);
        Product sugar = new Product("CODE-003", "Cukier", 5.50);

        List<Product> products = List.of(tea, coffee, sugar);

        List<Product> result = productSorter.sort(products, Comparator.comparing(Product::getName));

        assertEquals(List.of(sugar, tea, coffee), result);
    }

    @Test
    void shouldReturnSortedCopyWithoutMutatingInputArray() {
        Product tea = new Product("CODE-001", "Herbata", 10.00);
        Product coffee = new Product("CODE-002", "Kawa", 19.99);
        Product sugar = new Product("CODE-003", "Cukier", 5.50);

        List<Product> products = List.of(tea, coffee, sugar);

        List<Product> result = productSorter.sort(products, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC);

        assertNotSame(products, result);
        assertEquals(List.of(tea, coffee, sugar), products);
        assertEquals(List.of(coffee, tea, sugar), result);
    }

    @Test
    void shouldRejectNullList() {
        assertThrows(NullPointerException.class,
                () -> productSorter.sort(null, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC));
    }

    @Test
    void shouldRejectNullComparator() {
        List<Product> products = List.of(new Product("CODE-001", "Kawa", 19.99));

        assertThrows(NullPointerException.class, () -> productSorter.sort(products, null));
    }

    @Test
    void shouldRejectListContainingNullProduct() {
        List<Product> products = Arrays.asList(
                new Product("CODE-001", "Kawa", 19.99),
                null
        );

        assertThrows(IllegalArgumentException.class,
                () -> productSorter.sort(products, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC));
    }
}