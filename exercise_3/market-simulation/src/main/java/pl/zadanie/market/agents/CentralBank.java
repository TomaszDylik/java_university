package pl.zadanie.market.agents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import pl.zadanie.market.config.SimulationConfig;
import pl.zadanie.market.patterns.observer.InflationChangedEvent;
import pl.zadanie.market.patterns.observer.MarketEvent;
import pl.zadanie.market.patterns.observer.Observer;
import pl.zadanie.market.patterns.observer.Subject;
import pl.zadanie.market.patterns.observer.TransactionEvent;

public class CentralBank implements Subject, Observer {

    private static final double INFLATION_ADJUSTMENT_STEP = 0.01;
    private static final double MIN_INFLATION_RATE = 0.0;

    private final SimulationConfig config;
    private final List<Observer> observers = new ArrayList<>();
    private double inflationRate;
    private double currentTurnover;

    public CentralBank(SimulationConfig config) {
        this.config = Objects.requireNonNull(config, "Simulation config must not be null");
        this.inflationRate = config.initialInflationRate();
        this.currentTurnover = 0.0;
    }

    public double getInflationRate() {
        return inflationRate;
    }

    public double getCurrentTurnover() {
        return currentTurnover;
    }

    @Override
    public void attach(Observer observer) {
        Objects.requireNonNull(observer, "Observer must not be null");
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(MarketEvent event) {
        Objects.requireNonNull(event, "Market event must not be null");
        for (Observer observer : List.copyOf(observers)) {
            observer.update(event);
        }
    }

    @Override
    public void update(MarketEvent event) {
        if (event instanceof TransactionEvent transactionEvent) {
            currentTurnover += transactionEvent.transaction().turnover();
        }
    }

    public void setInflationRate(double inflationRate) {
        if (inflationRate < 0) {
            throw new IllegalArgumentException("Inflation rate must not be negative");
        }
        this.inflationRate = inflationRate;
        notifyObservers(new InflationChangedEvent(inflationRate));
    }

    public void injectInflationShock(double value) {
        setInflationRate(Math.max(MIN_INFLATION_RATE, inflationRate + value));
    }

    public void adjustInflationRateForNextTurn() {
        double targetTurnover = config.taxThreshold();
        double adjustedRate = inflationRate;

        if (currentTurnover < targetTurnover) {
            adjustedRate = Math.max(MIN_INFLATION_RATE, inflationRate - INFLATION_ADJUSTMENT_STEP);
        } else if (currentTurnover > targetTurnover) {
            adjustedRate = inflationRate + INFLATION_ADJUSTMENT_STEP;
        }

        currentTurnover = 0.0;
        setInflationRate(adjustedRate);
    }

    public List<Observer> getObservers() {
        return Collections.unmodifiableList(observers);
    }
}
