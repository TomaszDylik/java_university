package pl.zadanie.market.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ProductTypeTest {

    @Test
    void shouldContainExpectedValues() {
        assertEquals(2, ProductType.values().length);
        assertEquals(ProductType.NECESSITY, ProductType.valueOf("NECESSITY"));
        assertEquals(ProductType.LUXURY, ProductType.valueOf("LUXURY"));
    }
}
