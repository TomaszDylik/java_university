package pl.javamarkt.cart;

import java.util.Objects;

public final class PriceCalculator {

    public double sum(Product[] products) {
        Objects.requireNonNull(products, "Products array cannot be null");

        double total = 0.0;
        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products array cannot contain null elements");
            }
            total += product.getPrice();
        }
        return total;
    }
}