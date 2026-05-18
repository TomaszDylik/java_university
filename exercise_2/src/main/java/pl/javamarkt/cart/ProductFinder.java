package pl.javamarkt.cart;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ProductFinder {

    public Optional<Product> findCheapest(List<Product> products) {
        validateProductsList(products);

        if (products.isEmpty()) {
            return Optional.empty();
        }

        Product cheapest = products.get(0);
        for (int index = 1; index < products.size(); index++) {
            Product currentProduct = products.get(index);
            if (currentProduct.getPrice() < cheapest.getPrice()) {
                cheapest = currentProduct;
            }
        }

        return Optional.of(cheapest);
    }

    public Optional<Product> findMostExpensive(List<Product> products) {
        validateProductsList(products);

        if (products.isEmpty()) {
            return Optional.empty();
        }

        Product mostExpensive = products.get(0);
        for (int index = 1; index < products.size(); index++) {
            Product currentProduct = products.get(index);
            if (currentProduct.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = currentProduct;
            }
        }

        return Optional.of(mostExpensive);
    }

    public List<Product> findCheapestProducts(List<Product> products, int count) {
        validateProductsList(products);
        validateRequestedCount(count);

        List<Product> copiedProducts = new ArrayList<>(products);
        copiedProducts.sort(Comparator.comparingDouble(Product::getPrice));
        return new ArrayList<>(copiedProducts.subList(0, Math.min(count, copiedProducts.size())));
    }

    public List<Product> findMostExpensiveProducts(List<Product> products, int count) {
        validateProductsList(products);
        validateRequestedCount(count);

        List<Product> copiedProducts = new ArrayList<>(products);
        copiedProducts.sort(Comparator.comparingDouble(Product::getPrice).reversed());
        return new ArrayList<>(copiedProducts.subList(0, Math.min(count, copiedProducts.size())));
    }

    private void validateProductsList(List<Product> products) {
        Objects.requireNonNull(products, "Products list cannot be null");

        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products list cannot contain null elements");
            }
        }
    }

    private void validateRequestedCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Requested product count cannot be negative");
        }
    }
}