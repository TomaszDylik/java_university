package pl.zadanie.market.patterns.strategy;

public class ConservativeStrategy implements PricingStrategy {

    private final double initialMargin;

    public ConservativeStrategy(double initialMargin) {
        if (initialMargin < 0) {
            throw new IllegalArgumentException("Initial margin must not be negative");
        }
        this.initialMargin = initialMargin;
    }

    @Override
    public double calculateNewMargin(double demandSupplyRatio, double currentMargin) {
        return initialMargin;
    }
}
