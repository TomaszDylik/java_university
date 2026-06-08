package pl.zadanie.market.patterns.strategy;

public class AggressiveStrategy implements PricingStrategy {

    private static final double MARGIN_ADJUSTMENT = 0.05;

    @Override
    public double calculateNewMargin(double demandSupplyRatio, double currentMargin) {
        if (demandSupplyRatio > 1.0) {
            return currentMargin + MARGIN_ADJUSTMENT;
        }
        return Math.max(0.0, currentMargin - MARGIN_ADJUSTMENT);
    }
}
