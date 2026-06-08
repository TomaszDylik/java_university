package pl.zadanie.market.patterns.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.Seller;

public class StatisticsVisitor implements MarketVisitor {

    private final List<Double> sellerProfits = new ArrayList<>();
    private final List<Double> buyerExpenses = new ArrayList<>();

    @Override
    public void visit(Seller seller) {
        sellerProfits.add(seller.getProfit());
    }

    @Override
    public void visit(Buyer buyer) {
        buyerExpenses.add(buyer.getSpentAmount());
    }

    public List<Double> getSellerProfits() {
        return Collections.unmodifiableList(sellerProfits);
    }

    public List<Double> getBuyerExpenses() {
        return Collections.unmodifiableList(buyerExpenses);
    }

    public void reset() {
        sellerProfits.clear();
        buyerExpenses.clear();
    }
}
