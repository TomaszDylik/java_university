package pl.zadanie.market.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TransactionTest {

    private static final Product PRODUCT = new Product(ProductType.NECESSITY, 50.0);

    @Test
    void shouldCalculateTurnover() {
        Transaction transaction = new Transaction(PRODUCT, 120.0, 3);

        assertEquals(360.0, transaction.turnover());
    }

    @Test
    void shouldRejectNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> new Transaction(PRODUCT, -1.0, 1));
    }

    @Test
    void shouldRejectNonPositiveQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new Transaction(PRODUCT, 10.0, 0));
    }
}
