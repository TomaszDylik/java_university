package pl.javamarkt.cart;

import java.util.List;

@FunctionalInterface
public interface PromotionStrategy {

    List<Product> apply(List<Product> products);
}