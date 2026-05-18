package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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

        Product[] products = {tea, sugar, coffee};

        Product[] result = productSorter.sort(products, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC);

        assertArrayEquals(new Product[]{coffee, tea, sugar}, result);
    }

    @Test
    void shouldSortAlphabeticallyWhenPricesAreEqual() {
        Product bananas = new Product("CODE-001", "Banany", 10.00);
        Product apples = new Product("CODE-002", "Jablka", 10.00);
        Product tea = new Product("CODE-003", "Herbata", 5.00);

        Product[] products = {bananas, tea, apples};

        Product[] result = productSorter.sort(products, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC);

        assertArrayEquals(new Product[]{bananas, apples, tea}, result);
    }

    @Test
    void shouldReturnEmptyArrayWhenInputIsEmpty() {
        Product[] result = productSorter.sort(new Product[0], ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC);

        assertEquals(0, result.length);
    }

    @Test
    void shouldAllowChangingSortStrategyAtRuntime() {
        Product tea = new Product("CODE-001", "Herbata", 10.00);
        Product coffee = new Product("CODE-002", "Kawa", 19.99);
        Product sugar = new Product("CODE-003", "Cukier", 5.50);

        Product[] products = {tea, coffee, sugar};

        Product[] result = productSorter.sort(products, Comparator.comparing(Product::getName));

        assertArrayEquals(new Product[]{sugar, tea, coffee}, result);
    }

    @Test
    void shouldReturnSortedCopyWithoutMutatingInputArray() {
        Product tea = new Product("CODE-001", "Herbata", 10.00);
        Product coffee = new Product("CODE-002", "Kawa", 19.99);
        Product sugar = new Product("CODE-003", "Cukier", 5.50);

        Product[] products = {tea, coffee, sugar};

        Product[] result = productSorter.sort(products, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC);

        assertNotSame(products, result);
        assertArrayEquals(new Product[]{tea, coffee, sugar}, products);
        assertArrayEquals(new Product[]{coffee, tea, sugar}, result);
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class,
                () -> productSorter.sort(null, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC));
    }

    @Test
    void shouldRejectNullComparator() {
        Product[] products = {new Product("CODE-001", "Kawa", 19.99)};

        assertThrows(NullPointerException.class, () -> productSorter.sort(products, null));
    }

    @Test
    void shouldRejectArrayContainingNullProduct() {
        Product[] products = {
                new Product("CODE-001", "Kawa", 19.99),
                null
        };

        assertThrows(IllegalArgumentException.class,
                () -> productSorter.sort(products, ProductSorter.BY_PRICE_DESC_THEN_NAME_ASC));
    }
}