package pl.javamarkt.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class FivePercentDiscountAboveThresholdPromotion implements PromotionStrategy {

    private static final double ORDER_THRESHOLD = 300.0;
    private static final double DISCOUNT_RATE = 0.05;

    @Override
    public List<Product> apply(List<Product> products) {
        Objects.requireNonNull(products, "Products list cannot be null");

        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products list cannot contain null elements");
            }
        }

        if (calculateCurrentOrderValue(products) <= ORDER_THRESHOLD) {
            return new ArrayList<>(products);
        }

        List<Product> discountedProducts = new ArrayList<>();
        for (Product product : products) {
            double discountedPrice = product.getDiscountPrice() * (1 - DISCOUNT_RATE);
            discountedProducts.add(product.withDiscountPrice(discountedPrice));
        }

        return discountedProducts;
    }

    private double calculateCurrentOrderValue(List<Product> products) {
        double total = 0.0;

        for (Product product : products) {
            total += product.getDiscountPrice();
        }

        return total;
    }
}