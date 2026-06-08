package pl.zadanie.market.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void shouldCreateProductWithValidData() {
        Product product = new Product(ProductType.NECESSITY, 10.0);

        assertEquals(ProductType.NECESSITY, product.type());
        assertEquals(10.0, product.baseCost());
    }

    @Test
    void shouldAllowZeroBaseCost() {
        Product product = new Product(ProductType.LUXURY, 0.0);

        assertEquals(0.0, product.baseCost());
    }

    @Test
    void shouldRejectNullProductType() {
        assertThrows(NullPointerException.class, () -> new Product(null, 10.0));
    }

    @Test
    void shouldRejectNegativeBaseCost() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product(ProductType.NECESSITY, -1.0));

        assertEquals("Base cost must not be negative", exception.getMessage());
    }
}
