package pl.javamarkt.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class FreeMugAboveThresholdPromotion implements PromotionStrategy {

    static final String FREE_MUG_CODE = "FREE-MUG";
    static final String FREE_MUG_NAME = "Firmowy kubek";
    private static final double ORDER_THRESHOLD = 200.0;

    @Override
    public List<Product> apply(List<Product> products) {
        Objects.requireNonNull(products, "Products list cannot be null");

        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products list cannot contain null elements");
            }
        }

        List<Product> resultProducts = new ArrayList<>(products);
        if (calculateCurrentOrderValue(products) > ORDER_THRESHOLD) {
            resultProducts.add(new Product(FREE_MUG_CODE, FREE_MUG_NAME, 0.0));
        }

        return resultProducts;
    }

    private double calculateCurrentOrderValue(List<Product> products) {
        double total = 0.0;

        for (Product product : products) {
            total += product.getDiscountPrice();
        }

        return total;
    }
}