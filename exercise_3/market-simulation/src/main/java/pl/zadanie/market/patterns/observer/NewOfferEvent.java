package pl.zadanie.market.patterns.observer;

import java.util.Objects;

import pl.zadanie.market.agents.Seller;
import pl.zadanie.market.domain.Offer;

public record NewOfferEvent(Offer offer, Seller seller) implements MarketEvent {

    public NewOfferEvent {
        Objects.requireNonNull(offer, "Offer must not be null");
        Objects.requireNonNull(seller, "Seller must not be null");
    }
}
