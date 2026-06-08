package pl.zadanie.market.agents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.zadanie.market.config.SimulationConfig;
import pl.zadanie.market.domain.Product;
import pl.zadanie.market.domain.ProductType;
import pl.zadanie.market.patterns.strategy.ConservativeStrategy;

class BuyerTest {

    private SimulationConfig config;
    private CentralBank centralBank;
    private Product bread;
    private Product watch;

    @BeforeEach
    void setUp() {
        config = SimulationConfig.builder()
                .initialInflationRate(0.0)
                .defaultMargin(0.0)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(0.1, 0.5, 1.0)
                .initialBuyerBudgets(1000.0)
                .build();
        centralBank = new CentralBank(config);
        bread = new Product(ProductType.NECESSITY, 10.0);
        watch = new Product(ProductType.LUXURY, 100.0);
    }

    @Test
    void shouldAlwaysBuyNecessityWhenAffordable() {
        Seller cheapSeller = createSeller(bread, 0.0, 5);
        Seller expensiveSeller = createSeller(bread, 0.5, 5);
        Buyer buyer = createBuyer(Map.of(bread, 1), Map.of());
        buyer.subscribeToSeller(cheapSeller);
        buyer.subscribeToSeller(expensiveSeller);

        cheapSeller.publishOffers();
        expensiveSeller.publishOffers();
        buyer.attemptPurchases();

        assertEquals(10.0, buyer.getSpentAmount(), 0.001);
        assertEquals(90.0, buyer.getBudget(), 0.001);
    }

    @Test
    void shouldBuyLuxuryOnlyFromSurplus() {
        Seller seller = new Seller(0.0, 0.0, new ConservativeStrategy(0.0), centralBank, Map.of(bread, 1, watch, 1));
        Buyer buyer = createBuyer(Map.of(bread, 1), Map.of(watch, 1));
        buyer.subscribeToSeller(seller);
        seller.publishOffers();

        buyer.attemptPurchases();

        assertEquals(10.0, buyer.getSpentAmount(), 0.001);
        assertEquals(90.0, buyer.getBudget(), 0.001);
    }

    @Test
    void shouldRejectLuxuryWhenPriceElasticityExceeded() {
        Seller seller = new Seller(0.0, 1.5, new ConservativeStrategy(1.5), centralBank, Map.of(watch, 1));
        Buyer buyer = createBuyer(Map.of(), Map.of(watch, 1));
        buyer.setBudget(500.0);
        buyer.subscribeToSeller(seller);
        seller.publishOffers();

        buyer.attemptPurchases();

        assertEquals(0.0, buyer.getSpentAmount(), 0.001);
    }

    @Test
    void shouldBuyLuxuryWhenMarkupWithinElasticityThreshold() {
        Seller seller = new Seller(0.0, 0.2, new ConservativeStrategy(0.2), centralBank, Map.of(watch, 1));
        Buyer buyer = createBuyer(Map.of(), Map.of(watch, 1));
        buyer.setBudget(500.0);
        buyer.subscribeToSeller(seller);
        seller.publishOffers();

        buyer.attemptPurchases();

        assertTrue(buyer.getSpentAmount() > 0.0);
    }

    @Test
    void shouldPrioritizeNecessitiesBeforeLuxuries() {
        Product affordableWatch = new Product(ProductType.LUXURY, 80.0);
        Seller seller = new Seller(0.0, 0.0, new ConservativeStrategy(0.0), centralBank, Map.of(bread, 1, affordableWatch, 1));
        Buyer buyer = createBuyer(Map.of(bread, 1), Map.of(affordableWatch, 1));
        buyer.setBudget(85.0);
        buyer.subscribeToSeller(seller);
        seller.publishOffers();

        buyer.attemptPurchases();

        assertEquals(10.0, buyer.getSpentAmount(), 0.001);
        assertEquals(75.0, buyer.getBudget(), 0.001);
    }

    private Buyer createBuyer(Map<Product, Integer> necessities, Map<Product, Integer> luxuries) {
        return new Buyer(100.0, config, centralBank, necessities, luxuries);
    }

    private Seller createSeller(Product product, double margin, int stock) {
        return new Seller(0.0, margin, new ConservativeStrategy(margin), centralBank, Map.of(product, stock));
    }
}
