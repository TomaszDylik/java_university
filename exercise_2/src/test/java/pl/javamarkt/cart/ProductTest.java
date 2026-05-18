package pl.javamarkt.cart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void shouldCreateProductWithReadableState() {
        Product product = new Product("CODE-001", "Kawa", 19.99);

        assertAll(
                () -> assertEquals("CODE-001", product.getCode()),
                () -> assertEquals("Kawa", product.getName()),
                () -> assertEquals(19.99, product.getPrice()),
                () -> assertEquals(19.99, product.getDiscountPrice())
        );
    }

    @Test
    void shouldRejectNullCode() {
        assertThrows(NullPointerException.class, () -> new Product(null, "Kawa", 19.99));
    }

    @Test
    void shouldRejectNullName() {
        assertThrows(NullPointerException.class, () -> new Product("CODE-001", null, 19.99));
    }

    @Test
    void shouldRejectNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("CODE-001", "Kawa", -1.00));
    }
}