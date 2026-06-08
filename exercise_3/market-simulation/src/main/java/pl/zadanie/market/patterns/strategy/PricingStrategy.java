package pl.zadanie.market.patterns.strategy;

public interface PricingStrategy {

    /**
     * Calculates a new margin based on the market demand-to-supply ratio
     * from the previous turn and the current margin.
     *
     * @param demandSupplyRatio ratio of demand to supply ({@code > 1} means demand exceeds supply)
     * @param currentMargin     current margin value
     * @return recalculated margin
     */
    double calculateNewMargin(double demandSupplyRatio, double currentMargin);
}
