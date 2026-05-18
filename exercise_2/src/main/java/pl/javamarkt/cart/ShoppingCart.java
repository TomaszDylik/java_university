package pl.javamarkt.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ShoppingCart {

    private final List<Product> products;
    private final List<PromotionStrategy> promotionStrategies;

    public ShoppingCart(List<Product> products) {
        this(products, List.of());
    }

    public ShoppingCart(List<Product> products, List<PromotionStrategy> promotionStrategies) {
        this.products = copyProducts(products, "Products list cannot be null");
        this.promotionStrategies = copyPromotionStrategies(promotionStrategies);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public ShoppingCart withPromotion(PromotionStrategy promotionStrategy) {
        Objects.requireNonNull(promotionStrategy, "Promotion strategy cannot be null");

        List<PromotionStrategy> updatedPromotionStrategies = new ArrayList<>(promotionStrategies);
        updatedPromotionStrategies.add(promotionStrategy);
        return new ShoppingCart(products, updatedPromotionStrategies);
    }

    public ShoppingCart withoutPromotion(PromotionStrategy promotionStrategy) {
        Objects.requireNonNull(promotionStrategy, "Promotion strategy cannot be null");

        List<PromotionStrategy> updatedPromotionStrategies = new ArrayList<>();
        for (PromotionStrategy registeredPromotionStrategy : promotionStrategies) {
            if (registeredPromotionStrategy != promotionStrategy) {
                updatedPromotionStrategies.add(registeredPromotionStrategy);
            }
        }

        return new ShoppingCart(products, updatedPromotionStrategies);
    }

    public List<Product> getProductsAfterPromotions() {
        List<Product> currentProducts = new ArrayList<>(products);

        for (PromotionStrategy promotionStrategy : promotionStrategies) {
            currentProducts = copyProducts(
                    promotionStrategy.apply(currentProducts),
                    "Promotion result cannot be null"
            );
        }

        return currentProducts;
    }

    private List<Product> copyProducts(List<Product> products, String nullListMessage) {
        Objects.requireNonNull(products, nullListMessage);

        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products list cannot contain null elements");
            }
        }

        return new ArrayList<>(products);
    }

    private List<PromotionStrategy> copyPromotionStrategies(List<PromotionStrategy> promotionStrategies) {
        Objects.requireNonNull(promotionStrategies, "Promotion strategies list cannot be null");

        for (PromotionStrategy promotionStrategy : promotionStrategies) {
            if (promotionStrategy == null) {
                throw new IllegalArgumentException("Promotion strategies list cannot contain null elements");
            }
        }

        return new ArrayList<>(promotionStrategies);
    }
}