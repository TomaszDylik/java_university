package pl.zadanie.market.patterns.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ConservativeStrategyTest {

    private static final double INITIAL_MARGIN = 0.15;

    private final PricingStrategy strategy = new ConservativeStrategy(INITIAL_MARGIN);

    @Test
    void shouldAlwaysReturnInitialMarginRegardlessOfDemand() {
        assertEquals(INITIAL_MARGIN, strategy.calculateNewMargin(2.0, 0.30));
        assertEquals(INITIAL_MARGIN, strategy.calculateNewMargin(0.5, 0.10));
        assertEquals(INITIAL_MARGIN, strategy.calculateNewMargin(1.0, 0.25));
    }

    @Test
    void shouldRejectNegativeInitialMargin() {
        assertThrows(IllegalArgumentException.class, () -> new ConservativeStrategy(-0.01));
    }
}
