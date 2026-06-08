package pl.zadanie.market.agents;

import pl.zadanie.market.patterns.visitor.MarketVisitor;

public class Seller implements MarketParticipant {

    private double profit;
    private double inflationRate;

    public Seller(double profit) {
        this(profit, 0.0);
    }

    public Seller(double profit, double inflationRate) {
        if (profit < 0) {
            throw new IllegalArgumentException("Profit must not be negative");
        }
        if (inflationRate < 0) {
            throw new IllegalArgumentException("Inflation rate must not be negative");
        }
        this.profit = profit;
        this.inflationRate = inflationRate;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        if (profit < 0) {
            throw new IllegalArgumentException("Profit must not be negative");
        }
        this.profit = profit;
    }

    public double getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(double inflationRate) {
        if (inflationRate < 0) {
            throw new IllegalArgumentException("Inflation rate must not be negative");
        }
        this.inflationRate = inflationRate;
    }

    @Override
    public void accept(MarketVisitor visitor) {
        visitor.visit(this);
    }
}
