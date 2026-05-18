package pl.javamarkt.cart;

import java.util.Objects;

public final class Product {

    private final String code;
    private final String name;
    private final double price;
    private final double discountPrice;

    public Product(String code, String name, double price) {
        this(code, name, price, price);
    }

    private Product(String code, String name, double price, double discountPrice) {
        this.code = Objects.requireNonNull(code, "Product code cannot be null");
        this.name = Objects.requireNonNull(name, "Product name cannot be null");

        if (price < 0) {
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        if (discountPrice < 0) {
            throw new IllegalArgumentException("Product discount price cannot be negative");
        }
        if (discountPrice > price) {
            throw new IllegalArgumentException("Product discount price cannot be greater than base price");
        }

        this.price = price;
        this.discountPrice = discountPrice;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public Product withDiscountPrice(double newDiscountPrice) {
        return new Product(code, name, price, newDiscountPrice);
    }
}
