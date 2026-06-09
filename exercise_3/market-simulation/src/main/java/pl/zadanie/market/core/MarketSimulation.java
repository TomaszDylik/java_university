package pl.zadanie.market.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.CentralBank;
import pl.zadanie.market.agents.Seller;
import pl.zadanie.market.config.SimulationConfig;
import pl.zadanie.market.patterns.visitor.BudgetUpdateVisitor;
import pl.zadanie.market.patterns.visitor.StatisticsVisitor;

public class MarketSimulation {

    private final SimulationConfig config;
    private final CentralBank centralBank;
    private final List<Seller> sellers;
    private final List<Buyer> buyers;

    public MarketSimulation(
            SimulationConfig config,
            CentralBank centralBank,
            List<Seller> sellers,
            List<Buyer> buyers) {
        this.config = Objects.requireNonNull(config, "Simulation config must not be null");
        this.centralBank = Objects.requireNonNull(centralBank, "Central bank must not be null");
        this.sellers = List.copyOf(Objects.requireNonNull(sellers, "Sellers must not be null"));
        this.buyers = List.copyOf(Objects.requireNonNull(buyers, "Buyers must not be null"));
    }

    public SimulationResult run() {
        List<List<Double>> sellerProfitsPerTurn = new ArrayList<>();
        List<List<Double>> buyerExpensesPerTurn = new ArrayList<>();
        List<Double> inflationHistory = new ArrayList<>();

        inflationHistory.add(centralBank.getInflationRate());

        for (int turn = 1; turn <= config.maxTurns(); turn++) {
            executeTurn(turn);
            collectTurnStatistics(sellerProfitsPerTurn, buyerExpensesPerTurn);
            centralBank.adjustInflationRateForNextTurn();

            if (shouldInjectInflationShock(turn)) {
                centralBank.injectInflationShock(config.inflationShockValue());
            }

            inflationHistory.add(centralBank.getInflationRate());
        }

        return new SimulationResult(sellerProfitsPerTurn, buyerExpensesPerTurn, inflationHistory);
    }

    private void executeTurn(int turn) {
        applyBudgetUpdate();
        sellers.forEach(Seller::publishOffers);
        buyers.forEach(Buyer::attemptPurchases);
    }

    private void applyBudgetUpdate() {
        BudgetUpdateVisitor budgetUpdateVisitor = new BudgetUpdateVisitor();
        buyers.forEach(buyer -> buyer.accept(budgetUpdateVisitor));
    }

    private void collectTurnStatistics(
            List<List<Double>> sellerProfitsPerTurn,
            List<List<Double>> buyerExpensesPerTurn) {
        StatisticsVisitor statisticsVisitor = new StatisticsVisitor();

        sellers.forEach(seller -> seller.accept(statisticsVisitor));
        buyers.forEach(buyer -> buyer.accept(statisticsVisitor));

        sellerProfitsPerTurn.add(new ArrayList<>(statisticsVisitor.getSellerProfits()));
        buyerExpensesPerTurn.add(new ArrayList<>(statisticsVisitor.getBuyerExpenses()));
    }

    private boolean shouldInjectInflationShock(int turn) {
        return config.inflationShockTurn() > 0 && turn == config.inflationShockTurn();
    }
}
