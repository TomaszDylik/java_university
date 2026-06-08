package pl.zadanie.market.patterns.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AggressiveStrategyTest {

    private final PricingStrategy strategy = new AggressiveStrategy();

    @Test
    void shouldIncreaseMarginWhenDemandExceedsSupply() {
        double newMargin = strategy.calculateNewMargin(1.5, 0.20);

        assertEquals(0.25, newMargin, 1e-9);
    }

    @Test
    void shouldDecreaseMarginWhenProductsDoNotSell() {
        double newMargin = strategy.calculateNewMargin(0.8, 0.20);

        assertEquals(0.15, newMargin, 1e-9);
    }

    @Test
    void shouldDecreaseMarginWhenDemandEqualsSupply() {
        double newMargin = strategy.calculateNewMargin(1.0, 0.20);

        assertEquals(0.15, newMargin, 1e-9);
    }

    @Test
    void shouldNotDecreaseMarginBelowZero() {
        double newMargin = strategy.calculateNewMargin(0.5, 0.03);

        assertEquals(0.0, newMargin, 1e-9);
    }
}
