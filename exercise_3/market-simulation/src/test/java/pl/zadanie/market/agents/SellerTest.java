package pl.zadanie.market.agents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.zadanie.market.config.SimulationConfig;
import pl.zadanie.market.domain.Product;
import pl.zadanie.market.domain.ProductType;
import pl.zadanie.market.patterns.strategy.AggressiveStrategy;

class SellerTest {

    private SimulationConfig config;
    private CentralBank centralBank;
    private Product bread;

    @BeforeEach
    void setUp() {
        config = SimulationConfig.builder()
                .initialInflationRate(0.05)
                .defaultMargin(0.1)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(0.1, 0.5, 1.0)
                .initialBuyerBudgets(1000.0)
                .maxTurns(10)
                .build();
        centralBank = new CentralBank(config);
        bread = new Product(ProductType.NECESSITY, 10.0);
    }

    @Test
    void shouldCreateOfferFromBaseCostInflationAndMargin() {
        Seller seller = new Seller(0.0, 0.2, new AggressiveStrategy(), centralBank, Map.of(bread, 3));

        seller.publishOffers();

        assertEquals(10.0 * 1.05 * 1.2, seller.getActiveOffers().get(0).currentPrice(), 0.001);
    }

    @Test
    void shouldRenewStockEachTurn() {
        Seller seller = new Seller(0.0, 0.1, new AggressiveStrategy(), centralBank, Map.of(bread, 2));
        seller.fulfillPurchase(bread, 12.0);
        seller.fulfillPurchase(bread, 12.0);

        assertEquals(0, seller.getCurrentStock(bread));

        seller.renewStock();

        assertEquals(2, seller.getCurrentStock(bread));
        assertEquals(1, seller.getActiveOffers().size());
    }

    @Test
    void shouldIncreaseProfitAfterPurchase() {
        Seller seller = new Seller(50.0, 0.1, new AggressiveStrategy(), centralBank, Map.of(bread, 1));
        seller.publishOffers();
        double price = seller.getActiveOffers().get(0).currentPrice();

        boolean fulfilled = seller.fulfillPurchase(bread, price);

        assertTrue(fulfilled);
        assertEquals(50.0 + price, seller.getProfit(), 0.001);
    }

    @Test
    void shouldReportTransactionTurnoverToCentralBank() {
        Seller seller = new Seller(0.0, 0.1, new AggressiveStrategy(), centralBank, Map.of(bread, 1));
        seller.publishOffers();
        double price = seller.getActiveOffers().get(0).currentPrice();

        seller.fulfillPurchase(bread, price);

        assertEquals(price, centralBank.getCurrentTurnover(), 0.001);
    }

    @Test
    void shouldRecalculateOfferPriceAfterMarginUpdate() {
        Seller seller = new Seller(0.0, 0.1, new AggressiveStrategy(), centralBank, Map.of(bread, 1));
        seller.publishOffers();
        double initialPrice = seller.getActiveOffers().get(0).currentPrice();

        seller.updateMargin(1.5);

        assertTrue(seller.getActiveOffers().get(0).currentPrice() > initialPrice);
    }
}
