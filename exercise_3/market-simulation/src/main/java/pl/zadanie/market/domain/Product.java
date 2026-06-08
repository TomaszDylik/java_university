package pl.zadanie.market.domain;

import java.util.Objects;

public record Product(ProductType type, double baseCost) {

    public Product {
        Objects.requireNonNull(type, "Product type must not be null");
        if (baseCost < 0) {
            throw new IllegalArgumentException("Base cost must not be negative");
        }
    }
}
