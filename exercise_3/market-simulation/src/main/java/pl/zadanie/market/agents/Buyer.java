package pl.zadanie.market.agents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import pl.zadanie.market.config.SimulationConfig;
import pl.zadanie.market.domain.Offer;
import pl.zadanie.market.domain.Product;
import pl.zadanie.market.domain.ProductType;
import pl.zadanie.market.patterns.observer.InflationChangedEvent;
import pl.zadanie.market.patterns.observer.MarketEvent;
import pl.zadanie.market.patterns.observer.NewOfferEvent;
import pl.zadanie.market.patterns.observer.Observer;
import pl.zadanie.market.patterns.visitor.MarketVisitor;

public class Buyer implements MarketParticipant, Observer {

    private final double initialBudget;
    private final Map<Product, Integer> necessityNeeds = new HashMap<>();
    private final Map<Product, Integer> luxuryNeeds = new HashMap<>();
    private final List<TrackedOffer> availableOffers = new ArrayList<>();

    private double budget;
    private double spentAmount;
    private double inflationRate;
    private SimulationConfig config;

    public Buyer(double initialBudget) {
        if (initialBudget < 0) {
            throw new IllegalArgumentException("Initial budget must not be negative");
        }
        this.initialBudget = initialBudget;
        this.budget = initialBudget;
        this.spentAmount = 0.0;
        this.inflationRate = 0.0;
    }

    public Buyer(
            double initialBudget,
            SimulationConfig config,
            CentralBank centralBank,
            Map<Product, Integer> necessityNeeds,
            Map<Product, Integer> luxuryNeeds) {
        this(initialBudget);
        this.config = Objects.requireNonNull(config, "Simulation config must not be null");
        Objects.requireNonNull(centralBank, "Central bank must not be null");
        this.inflationRate = centralBank.getInflationRate();

        Objects.requireNonNull(necessityNeeds, "Necessity needs must not be null");
        Objects.requireNonNull(luxuryNeeds, "Luxury needs must not be null");
        necessityNeeds.forEach(this::addNecessityNeed);
        luxuryNeeds.forEach(this::addLuxuryNeed);

        centralBank.attach(this);
    }

    public double getBudget() {
        return budget;
    }

    public double getInitialBudget() {
        return initialBudget;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public double getInflationRate() {
        return inflationRate;
    }

    public List<Offer> getAvailableOffers() {
        return availableOffers.stream()
                .map(TrackedOffer::offer)
                .toList();
    }

    public void setBudget(double budget) {
        if (budget < 0) {
            throw new IllegalArgumentException("Budget must not be negative");
        }
        this.budget = budget;
    }

    public void setSpentAmount(double spentAmount) {
        if (spentAmount < 0) {
            throw new IllegalArgumentException("Spent amount must not be negative");
        }
        this.spentAmount = spentAmount;
    }

    public void addNecessityNeed(Product product, int quantity) {
        validateNecessityNeed(product, quantity);
        necessityNeeds.merge(product, quantity, Integer::sum);
    }

    public void addLuxuryNeed(Product product, int quantity) {
        validateLuxuryNeed(product, quantity);
        luxuryNeeds.merge(product, quantity, Integer::sum);
    }

    public void subscribeToSeller(Seller seller) {
        Objects.requireNonNull(seller, "Seller must not be null");
        seller.attach(this);
    }

    public void clearAvailableOffers() {
        availableOffers.clear();
    }

    public void attemptPurchases() {
        for (Map.Entry<Product, Integer> entry : necessityNeeds.entrySet()) {
            attemptPurchasesForProduct(entry.getKey(), entry.getValue(), true);
        }

        for (Map.Entry<Product, Integer> entry : luxuryNeeds.entrySet()) {
            attemptPurchasesForProduct(entry.getKey(), entry.getValue(), false);
        }
    }

    @Override
    public void update(MarketEvent event) {
        if (event instanceof InflationChangedEvent inflationChangedEvent) {
            inflationRate = inflationChangedEvent.newInflationRate();
            return;
        }

        if (event instanceof NewOfferEvent newOfferEvent) {
            availableOffers.removeIf(trackedOffer -> trackedOffer.seller() == newOfferEvent.seller()
                    && trackedOffer.offer().product().equals(newOfferEvent.offer().product()));
            availableOffers.add(new TrackedOffer(newOfferEvent.offer(), newOfferEvent.seller()));
        }
    }

    @Override
    public void accept(MarketVisitor visitor) {
        visitor.visit(this);
    }

    private void attemptPurchasesForProduct(Product product, int quantity, boolean necessity) {
        for (int i = 0; i < quantity; i++) {
            Optional<TrackedOffer> cheapestOffer = findCheapestOffer(product);
            if (cheapestOffer.isEmpty()) {
                return;
            }

            TrackedOffer trackedOffer = cheapestOffer.get();
            Offer offer = trackedOffer.offer();
            if (!canAfford(offer)) {
                return;
            }

            if (!necessity && !isWillingToBuyLuxury(offer)) {
                return;
            }

            executePurchase(trackedOffer);
        }
    }

    private Optional<TrackedOffer> findCheapestOffer(Product product) {
        return availableOffers.stream()
                .filter(trackedOffer -> trackedOffer.offer().product().equals(product))
                .min(Comparator.comparingDouble(trackedOffer -> trackedOffer.offer().currentPrice()));
    }

    private boolean canAfford(Offer offer) {
        return budget >= offer.currentPrice();
    }

    private boolean isWillingToBuyLuxury(Offer offer) {
        if (config == null) {
            return true;
        }

        double fairPrice = offer.product().baseCost() * (1.0 + inflationRate);
        if (fairPrice == 0.0) {
            return true;
        }

        double markupRatio = (offer.currentPrice() - fairPrice) / fairPrice;
        double maxAcceptableMarkup = config.priceElasticityThresholds()[config.priceElasticityThresholds().length - 1];
        return markupRatio <= maxAcceptableMarkup;
    }

    private void executePurchase(TrackedOffer trackedOffer) {
        Offer offer = trackedOffer.offer();
        Seller seller = trackedOffer.seller();

        if (!seller.fulfillPurchase(offer.product(), offer.currentPrice())) {
            return;
        }

        double price = offer.currentPrice();
        budget -= price;
        spentAmount += price;
    }

    private static void validateNecessityNeed(Product product, int quantity) {
        Objects.requireNonNull(product, "Product must not be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (product.type() != ProductType.NECESSITY) {
            throw new IllegalArgumentException("Necessity needs require NECESSITY product type");
        }
    }

    private static void validateLuxuryNeed(Product product, int quantity) {
        Objects.requireNonNull(product, "Product must not be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (product.type() != ProductType.LUXURY) {
            throw new IllegalArgumentException("Luxury needs require LUXURY product type");
        }
    }

    private record TrackedOffer(Offer offer, Seller seller) {
    }
}
