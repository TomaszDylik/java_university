package pl.zadanie.market.patterns.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.Seller;

class BudgetUpdateVisitorTest {

    @Test
    void shouldRenewBuyerBudgetToInitialValue() {
        BudgetUpdateVisitor visitor = new BudgetUpdateVisitor();
        Buyer buyer = new Buyer(1000.0);
        buyer.setBudget(250.0);
        buyer.setSpentAmount(180.0);

        buyer.accept(visitor);

        assertEquals(1000.0, buyer.getBudget());
        assertEquals(0.0, buyer.getSpentAmount());
    }

    @Test
    void shouldNotModifySeller() {
        BudgetUpdateVisitor visitor = new BudgetUpdateVisitor();
        Seller seller = new Seller(500.0, 0.03);

        seller.accept(visitor);

        assertEquals(500.0, seller.getProfit());
        assertEquals(0.03, seller.getInflationRate());
    }

    @Test
    void shouldDelegateAcceptToVisitor() {
        MarketVisitor visitor = mock(MarketVisitor.class);
        Buyer buyer = new Buyer(300.0);

        buyer.accept(visitor);

        verify(visitor).visit(buyer);
    }
}
