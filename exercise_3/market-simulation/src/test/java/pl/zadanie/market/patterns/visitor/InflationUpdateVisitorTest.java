package pl.zadanie.market.patterns.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.Seller;

class InflationUpdateVisitorTest {

    @Test
    void shouldUpdateSellerInflationRate() {
        InflationUpdateVisitor visitor = new InflationUpdateVisitor(0.05);
        Seller seller = new Seller(100.0, 0.02);

        seller.accept(visitor);

        assertEquals(0.05, seller.getInflationRate());
    }

    @Test
    void shouldNotModifyBuyer() {
        InflationUpdateVisitor visitor = new InflationUpdateVisitor(0.05);
        Buyer buyer = new Buyer(500.0);
        buyer.setBudget(200.0);
        buyer.setSpentAmount(300.0);

        buyer.accept(visitor);

        assertEquals(200.0, buyer.getBudget());
        assertEquals(300.0, buyer.getSpentAmount());
    }

    @Test
    void shouldDelegateAcceptToVisitor() {
        MarketVisitor visitor = mock(MarketVisitor.class);
        Seller seller = new Seller(50.0);

        seller.accept(visitor);

        verify(visitor).visit(seller);
    }
}
