package pl.zadanie.market.domain;

import java.util.Objects;

public record Transaction(Product product, double price, int quantity) {

    public Transaction {
        Objects.requireNonNull(product, "Product must not be null");
        if (price < 0) {
            throw new IllegalArgumentException("Price must not be negative");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }

    public double turnover() {
        return price * quantity;
    }
}
