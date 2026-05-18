package pl.javamarkt.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BuyTwoGetCheapestFreePromotion implements PromotionStrategy {

    private static final int MINIMUM_PRODUCTS_FOR_PROMOTION = 3;

    @Override
    public List<Product> apply(List<Product> products) {
        Objects.requireNonNull(products, "Products list cannot be null");

        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products list cannot contain null elements");
            }
        }

        if (products.size() < MINIMUM_PRODUCTS_FOR_PROMOTION) {
            return new ArrayList<>(products);
        }

        int cheapestProductIndex = findCheapestProductIndex(products);
        List<Product> discountedProducts = new ArrayList<>();

        for (int index = 0; index < products.size(); index++) {
            Product product = products.get(index);
            if (index == cheapestProductIndex) {
                discountedProducts.add(product.withDiscountPrice(0.0));
            } else {
                discountedProducts.add(product);
            }
        }

        return discountedProducts;
    }

    private int findCheapestProductIndex(List<Product> products) {
        int cheapestProductIndex = 0;
        double cheapestPrice = products.get(0).getDiscountPrice();

        for (int index = 1; index < products.size(); index++) {
            double currentPrice = products.get(index).getDiscountPrice();
            if (currentPrice < cheapestPrice) {
                cheapestPrice = currentPrice;
                cheapestProductIndex = index;
            }
        }

        return cheapestProductIndex;
    }
}