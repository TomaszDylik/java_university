package pl.zadanie.market.config;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SimulationConfigTest {

    private static final double[] ELASTICITY_THRESHOLDS = {0.1, 0.5, 1.0};
    private static final double[] BUYER_BUDGETS = {100.0, 200.0, 300.0};

    @Test
    void shouldCreateConfigWithValidData() {
        SimulationConfig config = SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.15)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(ELASTICITY_THRESHOLDS)
                .initialBuyerBudgets(BUYER_BUDGETS)
                .build();

        assertEquals(0.02, config.initialInflationRate());
        assertEquals(0.15, config.defaultMargin());
        assertEquals(1000.0, config.taxThreshold());
        assertArrayEquals(ELASTICITY_THRESHOLDS, config.priceElasticityThresholds());
        assertArrayEquals(BUYER_BUDGETS, config.initialBuyerBudgets());
    }

    @Test
    void shouldCreateDefensiveCopyOfArrays() {
        double[] elasticity = {0.1, 0.5};
        double[] budgets = {100.0, 200.0};

        SimulationConfig config = new SimulationConfig(0.02, 0.15, 1000.0, elasticity, budgets);

        elasticity[0] = 99.0;
        budgets[0] = 99.0;

        assertArrayEquals(new double[]{0.1, 0.5}, config.priceElasticityThresholds());
        assertArrayEquals(new double[]{100.0, 200.0}, config.initialBuyerBudgets());
        assertNotSame(elasticity, config.priceElasticityThresholds());
        assertNotSame(budgets, config.initialBuyerBudgets());
    }

    @Test
    void shouldRejectNegativeInitialInflationRate() {
        assertThrows(IllegalArgumentException.class, () -> SimulationConfig.builder()
                .initialInflationRate(-0.01)
                .defaultMargin(0.15)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(ELASTICITY_THRESHOLDS)
                .initialBuyerBudgets(BUYER_BUDGETS)
                .build());
    }

    @Test
    void shouldRejectNegativeDefaultMargin() {
        assertThrows(IllegalArgumentException.class, () -> SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(-0.01)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(ELASTICITY_THRESHOLDS)
                .initialBuyerBudgets(BUYER_BUDGETS)
                .build());
    }

    @Test
    void shouldRejectNegativeTaxThreshold() {
        assertThrows(IllegalArgumentException.class, () -> SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.15)
                .taxThreshold(-1.0)
                .priceElasticityThresholds(ELASTICITY_THRESHOLDS)
                .initialBuyerBudgets(BUYER_BUDGETS)
                .build());
    }

    @Test
    void shouldRejectNullPriceElasticityThresholds() {
        assertThrows(NullPointerException.class, () -> SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.15)
                .taxThreshold(1000.0)
                .priceElasticityThresholds((double[]) null)
                .initialBuyerBudgets(BUYER_BUDGETS)
                .build());
    }

    @Test
    void shouldRejectEmptyPriceElasticityThresholds() {
        assertThrows(IllegalArgumentException.class, () -> SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.15)
                .taxThreshold(1000.0)
                .priceElasticityThresholds()
                .initialBuyerBudgets(BUYER_BUDGETS)
                .build());
    }

    @Test
    void shouldRejectNegativePriceElasticityThreshold() {
        assertThrows(IllegalArgumentException.class, () -> SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.15)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(-0.1, 0.5)
                .initialBuyerBudgets(BUYER_BUDGETS)
                .build());
    }

    @Test
    void shouldRejectNullInitialBuyerBudgets() {
        assertThrows(NullPointerException.class, () -> SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.15)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(ELASTICITY_THRESHOLDS)
                .initialBuyerBudgets((double[]) null)
                .build());
    }

    @Test
    void shouldRejectEmptyInitialBuyerBudgets() {
        assertThrows(IllegalArgumentException.class, () -> SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.15)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(ELASTICITY_THRESHOLDS)
                .initialBuyerBudgets()
                .build());
    }

    @Test
    void shouldRejectNegativeInitialBuyerBudget() {
        assertThrows(IllegalArgumentException.class, () -> SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.15)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(ELASTICITY_THRESHOLDS)
                .initialBuyerBudgets(100.0, -50.0)
                .build());
    }
}
