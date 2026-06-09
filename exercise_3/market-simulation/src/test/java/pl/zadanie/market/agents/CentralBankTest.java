package pl.zadanie.market.agents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.zadanie.market.config.SimulationConfig;
import pl.zadanie.market.domain.Product;
import pl.zadanie.market.domain.ProductType;
import pl.zadanie.market.domain.Transaction;
import pl.zadanie.market.patterns.observer.InflationChangedEvent;
import pl.zadanie.market.patterns.observer.Observer;
import pl.zadanie.market.patterns.observer.TransactionEvent;

class CentralBankTest {

    private SimulationConfig config;
    private CentralBank centralBank;

    @BeforeEach
    void setUp() {
        config = SimulationConfig.builder()
                .initialInflationRate(0.02)
                .defaultMargin(0.1)
                .taxThreshold(500.0)
                .priceElasticityThresholds(0.1, 0.5, 1.0)
                .initialBuyerBudgets(1000.0)
                .maxTurns(10)
                .build();
        centralBank = new CentralBank(config);
    }

    @Test
    void shouldStartWithConfiguredInflationRate() {
        assertEquals(0.02, centralBank.getInflationRate());
    }

    @Test
    void shouldNotifyObserversWhenInflationChanges() {
        Observer observer = mock(Observer.class);
        centralBank.attach(observer);

        centralBank.setInflationRate(0.05);

        verify(observer).update(new InflationChangedEvent(0.05));
    }

    @Test
    void shouldInjectInflationShock() {
        centralBank.injectInflationShock(0.03);

        assertEquals(0.05, centralBank.getInflationRate());
    }

    @Test
    void shouldNotAllowNegativeInflationAfterShock() {
        centralBank.injectInflationShock(-0.5);

        assertEquals(0.0, centralBank.getInflationRate());
    }

    @Test
    void shouldCollectTurnoverFromTransactions() {
        centralBank.update(new TransactionEvent(new Transaction(
                new Product(ProductType.NECESSITY, 10.0), 100.0, 2)));

        assertEquals(200.0, centralBank.getCurrentTurnover());
    }

    @Test
    void shouldDecreaseInflationWhenTurnoverBelowTaxThreshold() {
        centralBank.update(new TransactionEvent(new Transaction(
                new Product(ProductType.NECESSITY, 10.0), 100.0, 2)));

        centralBank.adjustInflationRateForNextTurn();

        assertEquals(0.01, centralBank.getInflationRate());
        assertEquals(0.0, centralBank.getCurrentTurnover());
    }

    @Test
    void shouldIncreaseInflationWhenTurnoverAboveTaxThreshold() {
        centralBank.update(new TransactionEvent(new Transaction(
                new Product(ProductType.NECESSITY, 10.0), 300.0, 2)));

        centralBank.adjustInflationRateForNextTurn();

        assertEquals(0.03, centralBank.getInflationRate());
    }

    @Test
    void shouldKeepInflationWhenTurnoverMatchesTaxThreshold() {
        centralBank.update(new TransactionEvent(new Transaction(
                new Product(ProductType.NECESSITY, 10.0), 500.0, 1)));

        centralBank.adjustInflationRateForNextTurn();

        assertEquals(0.02, centralBank.getInflationRate());
    }

    @Test
    void shouldRejectNullObserver() {
        assertThrows(NullPointerException.class, () -> centralBank.attach(null));
    }
}
