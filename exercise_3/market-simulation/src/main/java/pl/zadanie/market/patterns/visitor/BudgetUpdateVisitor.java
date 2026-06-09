package pl.zadanie.market.patterns.visitor;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.Seller;

public class BudgetUpdateVisitor implements MarketVisitor {

    @Override
    public void visit(Seller seller) {
        
    }

    @Override
    public void visit(Buyer buyer) {
        buyer.setBudget(buyer.getInitialBudget());
        buyer.setSpentAmount(0.0);
    }
}
