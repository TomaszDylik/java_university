package pl.javamarkt.cart;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public final class ProductFinder {

    public Optional<Product> findCheapest(Product[] products) {
        validateProductsArray(products);

        if (products.length == 0) {
            return Optional.empty();
        }

        Product cheapest = products[0];
        for (int index = 1; index < products.length; index++) {
            Product currentProduct = products[index];
            if (currentProduct.getPrice() < cheapest.getPrice()) {
                cheapest = currentProduct;
            }
        }

        return Optional.of(cheapest);
    }

    public Optional<Product> findMostExpensive(Product[] products) {
        validateProductsArray(products);

        if (products.length == 0) {
            return Optional.empty();
        }

        Product mostExpensive = products[0];
        for (int index = 1; index < products.length; index++) {
            Product currentProduct = products[index];
            if (currentProduct.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = currentProduct;
            }
        }

        return Optional.of(mostExpensive);
    }

    public Product[] findCheapestProducts(Product[] products, int count) {
        validateProductsArray(products);
        validateRequestedCount(count);

        Product[] copiedProducts = Arrays.copyOf(products, products.length);
        Arrays.sort(copiedProducts, Comparator.comparingDouble(Product::getPrice));
        return Arrays.copyOf(copiedProducts, Math.min(count, copiedProducts.length));
    }

    public Product[] findMostExpensiveProducts(Product[] products, int count) {
        validateProductsArray(products);
        validateRequestedCount(count);

        Product[] copiedProducts = Arrays.copyOf(products, products.length);
        Arrays.sort(copiedProducts, Comparator.comparingDouble(Product::getPrice).reversed());
        return Arrays.copyOf(copiedProducts, Math.min(count, copiedProducts.length));
    }

    private void validateProductsArray(Product[] products) {
        Objects.requireNonNull(products, "Products array cannot be null");

        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products array cannot contain null elements");
            }
        }
    }

    private void validateRequestedCount(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Requested product count cannot be negative");
        }
    }
}