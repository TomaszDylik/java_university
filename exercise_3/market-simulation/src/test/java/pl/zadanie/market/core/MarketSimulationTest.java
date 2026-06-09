package pl.zadanie.market.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.CentralBank;
import pl.zadanie.market.agents.Seller;
import pl.zadanie.market.config.SimulationConfig;
import pl.zadanie.market.patterns.visitor.BudgetUpdateVisitor;
import pl.zadanie.market.patterns.visitor.StatisticsVisitor;

class MarketSimulationTest {

    private static final int MAX_TURNS = 3;

    private SimulationConfig config;
    private CentralBank centralBank;
    private Seller sellerOne;
    private Seller sellerTwo;
    private Buyer buyerOne;
    private Buyer buyerTwo;

    @BeforeEach
    void setUp() {
        config = SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.1)
                .taxThreshold(500.0)
                .priceElasticityThresholds(0.1, 0.5, 1.0)
                .initialBuyerBudgets(1000.0, 500.0)
                .maxTurns(MAX_TURNS)
                .build();

        centralBank = mock(CentralBank.class);
        when(centralBank.getInflationRate()).thenReturn(0.02);

        sellerOne = mock(Seller.class);
        sellerTwo = mock(Seller.class);
        buyerOne = mock(Buyer.class);
        buyerTwo = mock(Buyer.class);
    }

    @Test
    void shouldExecuteTurnLoopForConfiguredNumberOfTurns() {
        MarketSimulation simulation = new MarketSimulation(
                config,
                centralBank,
                List.of(sellerOne, sellerTwo),
                List.of(buyerOne, buyerTwo));

        SimulationResult result = simulation.run();

        verify(buyerOne, times(MAX_TURNS)).accept(any(BudgetUpdateVisitor.class));
        verify(buyerTwo, times(MAX_TURNS)).accept(any(BudgetUpdateVisitor.class));
        verify(sellerOne, times(MAX_TURNS)).publishOffers();
        verify(sellerTwo, times(MAX_TURNS)).publishOffers();
        verify(buyerOne, times(MAX_TURNS)).attemptPurchases();
        verify(buyerTwo, times(MAX_TURNS)).attemptPurchases();
        verify(sellerOne, times(MAX_TURNS)).accept(any(StatisticsVisitor.class));
        verify(sellerTwo, times(MAX_TURNS)).accept(any(StatisticsVisitor.class));
        verify(buyerOne, times(MAX_TURNS)).accept(any(StatisticsVisitor.class));
        verify(buyerTwo, times(MAX_TURNS)).accept(any(StatisticsVisitor.class));
        verify(centralBank, times(MAX_TURNS)).adjustInflationRateForNextTurn();
        verify(centralBank, never()).injectInflationShock(org.mockito.ArgumentMatchers.anyDouble());

        assertEquals(MAX_TURNS, result.sellerProfitsPerTurn().size());
        assertEquals(MAX_TURNS, result.buyerExpensesPerTurn().size());
        assertEquals(MAX_TURNS + 1, result.inflationHistory().size());
    }

    @Test
    void shouldInjectInflationShockOnConfiguredTurn() {
        SimulationConfig shockConfig = SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.1)
                .taxThreshold(500.0)
                .priceElasticityThresholds(0.1, 0.5, 1.0)
                .initialBuyerBudgets(1000.0)
                .maxTurns(10)
                .inflationShockTurn(10)
                .inflationShockValue(0.05)
                .build();

        MarketSimulation simulation = new MarketSimulation(
                shockConfig,
                centralBank,
                List.of(sellerOne),
                List.of(buyerOne));

        simulation.run();

        verify(centralBank, times(10)).adjustInflationRateForNextTurn();
        verify(centralBank, times(1)).injectInflationShock(0.05);
    }

    @Test
    void shouldCollectStatisticsFromVisitorsEachTurn() {
        when(sellerOne.getProfit()).thenReturn(100.0, 150.0, 200.0);
        when(buyerOne.getSpentAmount()).thenReturn(50.0, 75.0, 90.0);
        when(buyerOne.getInitialBudget()).thenReturn(1000.0);

        doAnswer(invocation -> {
            invocation.getArgument(0, pl.zadanie.market.patterns.visitor.MarketVisitor.class).visit(sellerOne);
            return null;
        }).when(sellerOne).accept(any());

        doAnswer(invocation -> {
            invocation.getArgument(0, pl.zadanie.market.patterns.visitor.MarketVisitor.class).visit(buyerOne);
            return null;
        }).when(buyerOne).accept(any());

        MarketSimulation simulation = new MarketSimulation(
                config,
                centralBank,
                List.of(sellerOne),
                List.of(buyerOne));

        SimulationResult result = simulation.run();

        assertEquals(100.0, result.sellerProfitsPerTurn().get(0).get(0));
        assertEquals(150.0, result.sellerProfitsPerTurn().get(1).get(0));
        assertEquals(200.0, result.sellerProfitsPerTurn().get(2).get(0));
        assertEquals(50.0, result.buyerExpensesPerTurn().get(0).get(0));
        assertEquals(75.0, result.buyerExpensesPerTurn().get(1).get(0));
        assertEquals(90.0, result.buyerExpensesPerTurn().get(2).get(0));
    }

    @Test
    void shouldRejectNullDependencies() {
        assertThrows(NullPointerException.class, () -> new MarketSimulation(
                null, centralBank, List.of(sellerOne), List.of(buyerOne)));
        assertThrows(NullPointerException.class, () -> new MarketSimulation(
                config, null, List.of(sellerOne), List.of(buyerOne)));
        assertThrows(NullPointerException.class, () -> new MarketSimulation(
                config, centralBank, null, List.of(buyerOne)));
        assertThrows(NullPointerException.class, () -> new MarketSimulation(
                config, centralBank, List.of(sellerOne), null));
    }
}
