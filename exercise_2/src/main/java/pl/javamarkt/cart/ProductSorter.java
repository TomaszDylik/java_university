package pl.javamarkt.cart;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class ProductSorter {

    public static final Comparator<Product> BY_PRICE_DESC_THEN_NAME_ASC =
            Comparator.comparingDouble(Product::getPrice)
                    .reversed()
                    .thenComparing(Product::getName);

    public List<Product> sort(List<Product> products, Comparator<Product> comparator) {
        Objects.requireNonNull(products, "Products list cannot be null");
        Objects.requireNonNull(comparator, "Comparator cannot be null");

        List<Product> copiedProducts = new ArrayList<>(products);
        validateProductsList(copiedProducts);
        copiedProducts.sort(comparator);
        return copiedProducts;
    }

    private void validateProductsList(List<Product> products) {
        for (Product product : products) {
            if (product == null) {
                throw new IllegalArgumentException("Products list cannot contain null elements");
            }
        }
    }
}