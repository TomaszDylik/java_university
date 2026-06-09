package pl.zadanie.market;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.CentralBank;
import pl.zadanie.market.agents.Seller;
import pl.zadanie.market.config.SimulationConfig;
import pl.zadanie.market.core.ConsoleReporter;
import pl.zadanie.market.core.MarketSimulation;
import pl.zadanie.market.core.SimulationResult;
import pl.zadanie.market.domain.Product;
import pl.zadanie.market.domain.ProductType;
import pl.zadanie.market.patterns.strategy.AggressiveStrategy;
import pl.zadanie.market.patterns.strategy.ConservativeStrategy;

public final class Main {

    private static final Product BREAD = new Product(ProductType.NECESSITY, 10.0);
    private static final Product MILK = new Product(ProductType.NECESSITY, 8.0);
    private static final Product MEDICINE = new Product(ProductType.NECESSITY, 25.0);
    private static final Product WATCH = new Product(ProductType.LUXURY, 100.0);
    private static final Product JEWELRY = new Product(ProductType.LUXURY, 200.0);
    private static final Product PERFUME = new Product(ProductType.LUXURY, 80.0);

    private Main() {
    }

    public static void main(String[] args) {
        SimulationConfig config = createSimulationConfig();
        CentralBank centralBank = new CentralBank(config);
        List<Seller> sellers = createSellers(centralBank);
        List<Buyer> buyers = createBuyers(config, centralBank);
        connectBuyersToSellers(buyers, sellers);

        MarketSimulation simulation = new MarketSimulation(config, centralBank, sellers, buyers);
        SimulationResult result = simulation.run();

        new ConsoleReporter(result).printReport();
    }

    private static SimulationConfig createSimulationConfig() {
        return SimulationConfig.builder()
                .initialInflationRate(0.05)
                .defaultMargin(0.10)
                .taxThreshold(2500.0)
                .priceElasticityThresholds(0.1, 0.5, 1.0)
                .initialBuyerBudgets(
                        800.0, 600.0, 1200.0, 450.0, 900.0, 1500.0,
                        350.0, 700.0, 1100.0, 500.0, 950.0, 650.0)
                .maxTurns(30)
                .inflationShockTurn(15)
                .inflationShockValue(0.05)
                .build();
    }

    private static List<Seller> createSellers(CentralBank centralBank) {
        List<Seller> sellers = new ArrayList<>();

        sellers.add(new Seller(
                100.0, 0.10, new AggressiveStrategy(), centralBank,
                Map.of(BREAD, 20, MILK, 15)));

        sellers.add(new Seller(
                50.0, 0.15, new AggressiveStrategy(), centralBank,
                Map.of(MEDICINE, 10)));

        sellers.add(new Seller(
                200.0, 0.20, new ConservativeStrategy(0.20), centralBank,
                Map.of(WATCH, 8)));

        sellers.add(new Seller(
                150.0, 0.25, new ConservativeStrategy(0.25), centralBank,
                Map.of(JEWELRY, 5, PERFUME, 12)));

        sellers.add(new Seller(
                75.0, 0.12, new AggressiveStrategy(), centralBank,
                Map.of(BREAD, 10, WATCH, 4, PERFUME, 6)));

        return sellers;
    }

    private static List<Buyer> createBuyers(SimulationConfig config, CentralBank centralBank) {
        double[] budgets = config.initialBuyerBudgets();
        List<Buyer> buyers = new ArrayList<>();

        buyers.add(new Buyer(budgets[0], config, centralBank,
                Map.of(BREAD, 2, MILK, 1), Map.of(WATCH, 1)));

        buyers.add(new Buyer(budgets[1], config, centralBank,
                Map.of(BREAD, 1), Map.of()));

        buyers.add(new Buyer(budgets[2], config, centralBank,
                Map.of(BREAD, 1, MILK, 2, MEDICINE, 1), Map.of(JEWELRY, 1)));

        buyers.add(new Buyer(budgets[3], config, centralBank,
                Map.of(MILK, 2), Map.of(PERFUME, 1)));

        buyers.add(new Buyer(budgets[4], config, centralBank,
                Map.of(BREAD, 2), Map.of(WATCH, 1, PERFUME, 1)));

        buyers.add(new Buyer(budgets[5], config, centralBank,
                Map.of(MEDICINE, 2), Map.of(JEWELRY, 2, WATCH, 1)));

        buyers.add(new Buyer(budgets[6], config, centralBank,
                Map.of(BREAD, 1), Map.of()));

        buyers.add(new Buyer(budgets[7], config, centralBank,
                Map.of(MILK, 1, MEDICINE, 1), Map.of(PERFUME, 1)));

        buyers.add(new Buyer(budgets[8], config, centralBank,
                Map.of(BREAD, 3, MILK, 1), Map.of(WATCH, 2)));

        buyers.add(new Buyer(budgets[9], config, centralBank,
                Map.of(BREAD, 1, MILK, 1), Map.of()));

        buyers.add(new Buyer(budgets[10], config, centralBank,
                Map.of(MEDICINE, 1), Map.of(JEWELRY, 1, PERFUME, 1)));

        buyers.add(new Buyer(budgets[11], config, centralBank,
                Map.of(BREAD, 2, MEDICINE, 1), Map.of(WATCH, 1)));

        return buyers;
    }

    private static void connectBuyersToSellers(List<Buyer> buyers, List<Seller> sellers) {
        for (Buyer buyer : buyers) {
            for (Seller seller : sellers) {
                buyer.subscribeToSeller(seller);
            }
        }
    }
}
