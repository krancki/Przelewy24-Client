package pl.doubleByte.przelewy24.models;

import lombok.Value;

import java.util.Optional;

@Value
public class CartItem {
    String sellerId;
    String sellerCategory;
    String name;
    String description;
    Integer quantity;
    Integer price;
    String number;

    private CartItem(String sellerId, String sellerCategory, String name, String description,
                     Integer quantity, Integer price, String number) {
        this.sellerId = sellerId;
        this.sellerCategory = sellerCategory;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.number = number;
    }


    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Integer> getQuantity() {
        return Optional.ofNullable(quantity);
    }

    public Optional<Integer> getPrice() {
        return Optional.ofNullable(price);
    }

    public Optional<String> getNumber() {
        return Optional.ofNullable(number);
    }

    public static SellerIdStep builder() {
        return new Builder();
    }

    public interface SellerIdStep {
        SellerCategoryStep sellerId(String sellerId);
    }

    public interface SellerCategoryStep {
        OptionalStep sellerCategory(String sellerCategory);
    }

    public interface OptionalStep {
        OptionalStep name(String name);

        OptionalStep description(String description);

        OptionalStep quantity(Integer quantity);

        OptionalStep price(Integer price);

        OptionalStep number(String number);

        CartItem build();
    }

    private static class Builder implements SellerIdStep, SellerCategoryStep, OptionalStep {
        private String sellerId;
        private String sellerCategory;
        private String name;
        private String description;
        private Integer quantity;
        private Integer price;
        private String number;

        @Override
        public SellerCategoryStep sellerId(String sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        @Override
        public OptionalStep sellerCategory(String sellerCategory) {
            this.sellerCategory = sellerCategory;
            return this;
        }

        @Override
        public OptionalStep name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public OptionalStep description(String description) {
            this.description = description;
            return this;
        }

        @Override
        public OptionalStep quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        public OptionalStep price(Integer price) {
            this.price = price;
            return this;
        }

        @Override
        public OptionalStep number(String number) {
            this.number = number;
            return this;
        }

        @Override
        public CartItem build() {
            return new CartItem(sellerId, sellerCategory, name, description, quantity, price, number);
        }
    }

}
