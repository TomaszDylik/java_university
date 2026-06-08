package pl.zadanie.market.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OfferTest {

    private final Product product = new Product(ProductType.LUXURY, 50.0);

    @Test
    void shouldCreateOfferWithValidData() {
        Offer offer = new Offer(product, 75.0);

        assertEquals(product, offer.product());
        assertEquals(75.0, offer.currentPrice());
    }

    @Test
    void shouldAllowZeroCurrentPrice() {
        Offer offer = new Offer(product, 0.0);

        assertEquals(0.0, offer.currentPrice());
    }

    @Test
    void shouldRejectNullProduct() {
        assertThrows(NullPointerException.class, () -> new Offer(null, 75.0));
    }

    @Test
    void shouldRejectNegativeCurrentPrice() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Offer(product, -0.01));

        assertEquals("Current price must not be negative", exception.getMessage());
    }
}
