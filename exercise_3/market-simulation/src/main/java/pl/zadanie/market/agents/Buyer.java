package pl.zadanie.market.agents;

import pl.zadanie.market.patterns.visitor.MarketVisitor;

public class Buyer implements MarketParticipant {

    private double budget;
    private final double initialBudget;
    private double spentAmount;

    public Buyer(double initialBudget) {
        if (initialBudget < 0) {
            throw new IllegalArgumentException("Initial budget must not be negative");
        }
        this.initialBudget = initialBudget;
        this.budget = initialBudget;
        this.spentAmount = 0.0;
    }

    public double getBudget() {
        return budget;
    }

    public double getInitialBudget() {
        return initialBudget;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public void setBudget(double budget) {
        if (budget < 0) {
            throw new IllegalArgumentException("Budget must not be negative");
        }
        this.budget = budget;
    }

    public void setSpentAmount(double spentAmount) {
        if (spentAmount < 0) {
            throw new IllegalArgumentException("Spent amount must not be negative");
        }
        this.spentAmount = spentAmount;
    }

    @Override
    public void accept(MarketVisitor visitor) {
        visitor.visit(this);
    }
}
