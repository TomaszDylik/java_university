package pl.javamarkt.cart;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

public final class ProductSorter {

    public static final Comparator<Product> BY_PRICE_DESC_THEN_NAME_ASC =
            Comparator.comparingDouble(Product::getPrice)
                    .reversed()
                    .thenComparing(Product::getName);

    public Product[] sort(Product[] products, Comparator<Product> comparator) {
        Objects.requireNonNull(products, "Products array cannot be null");
        Objects.requireNonNull(comparator, "Comparator cannot be null");

        Product[] copiedProducts = Arrays.copyOf(products, products.length);
        validateProductsArray(copiedProducts);
        Arrays.sort(copiedProducts, comparator);
        return copiedProducts;
    }

    private void validateProductsArray(Product[] products) {
        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products array cannot contain null elements");
            }
        }
    }
}