package pl.zadanie.market.patterns.observer;

public record InflationChangedEvent(double newInflationRate) implements MarketEvent {

    public InflationChangedEvent {
        if (newInflationRate < 0) {
            throw new IllegalArgumentException("Inflation rate must not be negative");
        }
    }
}
