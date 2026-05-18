package pl.javamarkt.cart;

import java.util.List;
import java.util.Objects;

public final class PriceCalculator {

    public double sum(List<Product> products) {
        Objects.requireNonNull(products, "Products list cannot be null");

        double total = 0.0;
        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products list cannot contain null elements");
            }
            total += product.getPrice();
        }
        return total;
    }
}