package pl.zadanie.market.patterns.visitor;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.Seller;

public class InflationUpdateVisitor implements MarketVisitor {

    private final double newInflationRate;

    public InflationUpdateVisitor(double newInflationRate) {
        if (newInflationRate < 0) {
            throw new IllegalArgumentException("Inflation rate must not be negative");
        }
        this.newInflationRate = newInflationRate;
    }

    public double getNewInflationRate() {
        return newInflationRate;
    }

    @Override
    public void visit(Seller seller) {
        seller.setInflationRate(newInflationRate);
    }

    @Override
    public void visit(Buyer buyer) {
        
    }
}
