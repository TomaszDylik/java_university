package pl.javamarkt.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SingleUseCouponPromotion implements PromotionStrategy {

    private static final double DISCOUNT_RATE = 0.30;

    private final String targetProductCode;
    private boolean used;

    public SingleUseCouponPromotion(String targetProductCode) {
        this.targetProductCode = Objects.requireNonNull(targetProductCode, "Target product code cannot be null");
    }

    @Override
    public List<Product> apply(List<Product> products) {
        Objects.requireNonNull(products, "Products list cannot be null");

        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products list cannot contain null elements");
            }
        }

        if (used) {
            return new ArrayList<>(products);
        }

        List<Product> resultProducts = new ArrayList<>();
        boolean applied = false;

        for (Product product : products) {
            if (!applied && product.getCode().equals(targetProductCode)) {
                double discountedPrice = product.getDiscountPrice() * (1 - DISCOUNT_RATE);
                resultProducts.add(product.withDiscountPrice(discountedPrice));
                applied = true;
            } else {
                resultProducts.add(product);
            }
        }

        if (applied) {
            used = true;
        }

        return resultProducts;
    }
}