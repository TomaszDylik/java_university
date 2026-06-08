package pl.zadanie.market.patterns.visitor;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.MarketParticipant;
import pl.zadanie.market.agents.Seller;

class StatisticsVisitorTest {

    private StatisticsVisitor visitor;

    @BeforeEach
    void setUp() {
        visitor = new StatisticsVisitor();
    }

    @Test
    void shouldCollectSellerProfitsWithoutModifyingAgents() {
        Seller sellerOne = new Seller(120.0);
        Seller sellerTwo = new Seller(80.0);

        sellerOne.accept(visitor);
        sellerTwo.accept(visitor);

        assertArrayEquals(new double[]{120.0, 80.0}, toDoubleArray(visitor.getSellerProfits()));
        assertEquals(120.0, sellerOne.getProfit());
        assertEquals(80.0, sellerTwo.getProfit());
    }

    @Test
    void shouldCollectBuyerExpensesWithoutModifyingAgents() {
        Buyer buyerOne = new Buyer(1000.0);
        buyerOne.setSpentAmount(150.0);
        Buyer buyerTwo = new Buyer(500.0);
        buyerTwo.setSpentAmount(75.0);

        buyerOne.accept(visitor);
        buyerTwo.accept(visitor);

        assertArrayEquals(new double[]{150.0, 75.0}, toDoubleArray(visitor.getBuyerExpenses()));
        assertEquals(150.0, buyerOne.getSpentAmount());
        assertEquals(75.0, buyerTwo.getSpentAmount());
    }

    @Test
    void shouldIterateOverMixedParticipants() {
        Seller seller = new Seller(200.0);
        Buyer buyer = new Buyer(800.0);
        buyer.setSpentAmount(120.0);

        List<MarketParticipant> participants = Arrays.asList(seller, buyer, new Seller(50.0));

        participants.forEach(participant -> participant.accept(visitor));

        assertEquals(2, visitor.getSellerProfits().size());
        assertEquals(1, visitor.getBuyerExpenses().size());
        assertTrue(visitor.getSellerProfits().contains(200.0));
        assertTrue(visitor.getSellerProfits().contains(50.0));
        assertEquals(120.0, visitor.getBuyerExpenses().get(0));
    }

    @Test
    void shouldResetCollectedStatistics() {
        new Seller(100.0).accept(visitor);
        new Buyer(500.0).accept(visitor);

        visitor.reset();

        assertTrue(visitor.getSellerProfits().isEmpty());
        assertTrue(visitor.getBuyerExpenses().isEmpty());
    }

    private static double[] toDoubleArray(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
