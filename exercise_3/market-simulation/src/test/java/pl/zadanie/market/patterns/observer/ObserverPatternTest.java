package pl.zadanie.market.patterns.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.zadanie.market.agents.Buyer;
import pl.zadanie.market.agents.CentralBank;
import pl.zadanie.market.agents.Seller;
import pl.zadanie.market.config.SimulationConfig;
import pl.zadanie.market.domain.Offer;
import pl.zadanie.market.domain.Product;
import pl.zadanie.market.domain.ProductType;
import pl.zadanie.market.patterns.strategy.ConservativeStrategy;

class ObserverPatternTest {

    private SimulationConfig config;
    private CentralBank centralBank;
    private Product necessity;

    @BeforeEach
    void setUp() {
        config = SimulationConfig.builder()
                .initialInflationRate(0.0)
                .defaultMargin(0.1)
                .taxThreshold(1000.0)
                .priceElasticityThresholds(0.1, 0.5, 1.0)
                .initialBuyerBudgets(1000.0)
                .build();
        centralBank = new CentralBank(config);
        necessity = new Product(ProductType.NECESSITY, 100.0);
    }

    @Test
    void sellerShouldRaiseOfferPricesWhenBankChangesInflation() {
        Seller seller = createSeller(0.1);
        seller.publishOffers();

        double priceBefore = seller.getActiveOffers().get(0).currentPrice();

        centralBank.setInflationRate(0.1);

        double priceAfter = seller.getActiveOffers().get(0).currentPrice();
        assertTrue(priceAfter > priceBefore);
        assertEquals(100.0 * 1.1 * 1.1, priceAfter, 0.001);
    }

    @Test
    void bankShouldNotifyAttachedObserversOnInflationChange() {
        Observer observer = mock(Observer.class);
        centralBank.attach(observer);

        centralBank.setInflationRate(0.05);

        verify(observer).update(new InflationChangedEvent(0.05));
    }

    @Test
    void sellerShouldNotifyBuyersAboutNewOffers() {
        Seller seller = createSeller(0.1);
        Buyer buyer = mock(Buyer.class);
        seller.attach(buyer);

        seller.publishOffers();

        verify(buyer, atLeastOnce()).update(any(NewOfferEvent.class));
    }

    @Test
    void detachedObserverShouldNotReceiveNotifications() {
        Observer observer = mock(Observer.class);
        centralBank.attach(observer);
        centralBank.detach(observer);

        centralBank.setInflationRate(0.08);

        verify(observer, never()).update(any());
    }

    @Test
    void buyerShouldReceiveUpdatedInflationFromBank() {
        Buyer buyer = new Buyer(500.0, config, centralBank, Map.of(), Map.of());

        centralBank.setInflationRate(0.07);

        assertEquals(0.07, buyer.getInflationRate());
    }

    @Test
    void buyerShouldCollectOffersFromSeller() {
        Seller seller = createSeller(0.1);
        Buyer buyer = new Buyer(500.0, config, centralBank, Map.of(), Map.of());
        buyer.subscribeToSeller(seller);

        seller.publishOffers();

        assertEquals(1, buyer.getAvailableOffers().size());
        Offer offer = buyer.getAvailableOffers().get(0);
        assertEquals(necessity, offer.product());
    }

    private Seller createSeller(double margin) {
        return new Seller(
                0.0,
                margin,
                new ConservativeStrategy(margin),
                centralBank,
                Map.of(necessity, 5));
    }
}
