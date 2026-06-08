package pl.zadanie.market.agents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import pl.zadanie.market.domain.Offer;
import pl.zadanie.market.domain.Product;
import pl.zadanie.market.patterns.observer.InflationChangedEvent;
import pl.zadanie.market.patterns.observer.MarketEvent;
import pl.zadanie.market.domain.Transaction;
import pl.zadanie.market.patterns.observer.NewOfferEvent;
import pl.zadanie.market.patterns.observer.Observer;
import pl.zadanie.market.patterns.observer.Subject;
import pl.zadanie.market.patterns.observer.TransactionEvent;
import pl.zadanie.market.patterns.strategy.PricingStrategy;
import pl.zadanie.market.patterns.visitor.MarketVisitor;

public class Seller implements MarketParticipant, Subject, Observer {

    private final List<Observer> observers = new ArrayList<>();
    private final Map<Product, Integer> stockPerTurn = new HashMap<>();
    private final Map<Product, Integer> currentStock = new HashMap<>();
    private final List<Offer> activeOffers = new ArrayList<>();

    private double profit;
    private double inflationRate;
    private double margin;
    private PricingStrategy pricingStrategy;
    private CentralBank centralBank;

    public Seller(double profit) {
        this(profit, 0.0);
    }

    public Seller(double profit, double inflationRate) {
        if (profit < 0) {
            throw new IllegalArgumentException("Profit must not be negative");
        }
        if (inflationRate < 0) {
            throw new IllegalArgumentException("Inflation rate must not be negative");
        }
        this.profit = profit;
        this.inflationRate = inflationRate;
        this.margin = 0.0;
    }

    public Seller(
            double profit,
            double initialMargin,
            PricingStrategy pricingStrategy,
            CentralBank centralBank,
            Map<Product, Integer> stockPerTurn) {
        if (profit < 0) {
            throw new IllegalArgumentException("Profit must not be negative");
        }
        if (initialMargin < 0) {
            throw new IllegalArgumentException("Margin must not be negative");
        }
        this.profit = profit;
        this.margin = initialMargin;
        this.pricingStrategy = Objects.requireNonNull(pricingStrategy, "Pricing strategy must not be null");
        this.centralBank = Objects.requireNonNull(centralBank, "Central bank must not be null");
        this.inflationRate = centralBank.getInflationRate();

        Objects.requireNonNull(stockPerTurn, "Stock per turn must not be null");
        stockPerTurn.forEach(this::registerProduct);
        centralBank.attach(this);
        attach(centralBank);
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        if (profit < 0) {
            throw new IllegalArgumentException("Profit must not be negative");
        }
        this.profit = profit;
    }

    public double getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(double inflationRate) {
        if (inflationRate < 0) {
            throw new IllegalArgumentException("Inflation rate must not be negative");
        }
        this.inflationRate = inflationRate;
        refreshOffers();
    }

    public double getMargin() {
        return margin;
    }

    public List<Offer> getActiveOffers() {
        return Collections.unmodifiableList(activeOffers);
    }

    public int getCurrentStock(Product product) {
        return currentStock.getOrDefault(product, 0);
    }

    public void registerProduct(Product product, int stockPerTurnQuantity) {
        Objects.requireNonNull(product, "Product must not be null");
        if (stockPerTurnQuantity < 0) {
            throw new IllegalArgumentException("Stock per turn must not be negative");
        }
        stockPerTurn.put(product, stockPerTurnQuantity);
        currentStock.put(product, stockPerTurnQuantity);
    }

    public void renewStock() {
        for (Map.Entry<Product, Integer> entry : stockPerTurn.entrySet()) {
            currentStock.put(entry.getKey(), entry.getValue());
        }
        refreshOffers();
    }

    public void publishOffers() {
        refreshOffers();
        for (Offer offer : activeOffers) {
            notifyObservers(new NewOfferEvent(offer, this));
        }
    }

    public Optional<Offer> findOffer(Product product) {
        Objects.requireNonNull(product, "Product must not be null");
        return activeOffers.stream()
                .filter(offer -> offer.product().equals(product))
                .findFirst();
    }

    public boolean fulfillPurchase(Product product, double price) {
        Objects.requireNonNull(product, "Product must not be null");
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }

        int availableStock = currentStock.getOrDefault(product, 0);
        if (availableStock <= 0) {
            return false;
        }

        currentStock.put(product, availableStock - 1);
        profit += price;
        refreshOffers();
        notifyObservers(new TransactionEvent(new Transaction(product, price, 1)));
        return true;
    }

    public void updateMargin(double demandSupplyRatio) {
        if (pricingStrategy == null) {
            return;
        }
        margin = pricingStrategy.calculateNewMargin(demandSupplyRatio, margin);
        refreshOffers();
    }

    @Override
    public void attach(Observer observer) {
        Objects.requireNonNull(observer, "Observer must not be null");
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(MarketEvent event) {
        Objects.requireNonNull(event, "Market event must not be null");
        for (Observer observer : List.copyOf(observers)) {
            observer.update(event);
        }
    }

    @Override
    public void update(MarketEvent event) {
        if (event instanceof InflationChangedEvent inflationChangedEvent) {
            inflationRate = inflationChangedEvent.newInflationRate();
            refreshOffers();
        }
    }

    @Override
    public void accept(MarketVisitor visitor) {
        visitor.visit(this);
    }

    private void refreshOffers() {
        activeOffers.clear();
        for (Product product : stockPerTurn.keySet()) {
            if (getCurrentStock(product) > 0) {
                activeOffers.add(new Offer(product, calculatePrice(product)));
            }
        }
    }

    private double calculatePrice(Product product) {
        return product.baseCost() * (1.0 + inflationRate) * (1.0 + margin);
    }
}
