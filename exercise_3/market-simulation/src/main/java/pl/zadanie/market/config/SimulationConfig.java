package pl.zadanie.market.config;

import java.util.Arrays;
import java.util.Objects;

public record SimulationConfig(
        double initialInflationRate,
        double defaultMargin,
        double taxThreshold,
        double[] priceElasticityThresholds,
        double[] initialBuyerBudgets) {

    public SimulationConfig {
        if (initialInflationRate < 0) {
            throw new IllegalArgumentException("Initial inflation rate must not be negative");
        }
        if (defaultMargin < 0) {
            throw new IllegalArgumentException("Default margin must not be negative");
        }
        if (taxThreshold < 0) {
            throw new IllegalArgumentException("Tax threshold must not be negative");
        }
        Objects.requireNonNull(priceElasticityThresholds, "Price elasticity thresholds must not be null");
        Objects.requireNonNull(initialBuyerBudgets, "Initial buyer budgets must not be null");
        if (priceElasticityThresholds.length == 0) {
            throw new IllegalArgumentException("Price elasticity thresholds must not be empty");
        }
        if (initialBuyerBudgets.length == 0) {
            throw new IllegalArgumentException("Initial buyer budgets must not be empty");
        }
        validateNonNegativeValues(priceElasticityThresholds, "Price elasticity threshold");
        validateNonNegativeValues(initialBuyerBudgets, "Initial buyer budget");

        priceElasticityThresholds = priceElasticityThresholds.clone();
        initialBuyerBudgets = initialBuyerBudgets.clone();
    }

    @Override
    public double[] priceElasticityThresholds() {
        return priceElasticityThresholds.clone();
    }

    @Override
    public double[] initialBuyerBudgets() {
        return initialBuyerBudgets.clone();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private double initialInflationRate;
        private double defaultMargin;
        private double taxThreshold;
        private double[] priceElasticityThresholds;
        private double[] initialBuyerBudgets;

        private Builder() {
        }

        public Builder initialInflationRate(double initialInflationRate) {
            this.initialInflationRate = initialInflationRate;
            return this;
        }

        public Builder defaultMargin(double defaultMargin) {
            this.defaultMargin = defaultMargin;
            return this;
        }

        public Builder taxThreshold(double taxThreshold) {
            this.taxThreshold = taxThreshold;
            return this;
        }

        public Builder priceElasticityThresholds(double... priceElasticityThresholds) {
            this.priceElasticityThresholds = priceElasticityThresholds;
            return this;
        }

        public Builder initialBuyerBudgets(double... initialBuyerBudgets) {
            this.initialBuyerBudgets = initialBuyerBudgets;
            return this;
        }

        public SimulationConfig build() {
            return new SimulationConfig(
                    initialInflationRate,
                    defaultMargin,
                    taxThreshold,
                    priceElasticityThresholds,
                    initialBuyerBudgets);
        }
    }

    private static void validateNonNegativeValues(double[] values, String fieldName) {
        for (double value : values) {
            if (value < 0) {
                throw new IllegalArgumentException(fieldName + " must not be negative");
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimulationConfig other)) {
            return false;
        }
        return Double.compare(initialInflationRate, other.initialInflationRate) == 0
                && Double.compare(defaultMargin, other.defaultMargin) == 0
                && Double.compare(taxThreshold, other.taxThreshold) == 0
                && Arrays.equals(priceElasticityThresholds, other.priceElasticityThresholds)
                && Arrays.equals(initialBuyerBudgets, other.initialBuyerBudgets);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(initialInflationRate, defaultMargin, taxThreshold);
        result = 31 * result + Arrays.hashCode(priceElasticityThresholds);
        result = 31 * result + Arrays.hashCode(initialBuyerBudgets);
        return result;
    }

    @Override
    public String toString() {
        return "SimulationConfig["
                + "initialInflationRate=" + initialInflationRate
                + ", defaultMargin=" + defaultMargin
                + ", taxThreshold=" + taxThreshold
                + ", priceElasticityThresholds=" + Arrays.toString(priceElasticityThresholds)
                + ", initialBuyerBudgets=" + Arrays.toString(initialBuyerBudgets)
                + ']';
    }
}
