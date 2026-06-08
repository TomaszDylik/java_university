package pl.zadanie.market.domain;

import java.util.Objects;

public record Offer(Product product, double currentPrice) {

    public Offer {
        Objects.requireNonNull(product, "Product must not be null");
        if (currentPrice < 0) {
            throw new IllegalArgumentException("Current price must not be negative");
        }
    }
}
