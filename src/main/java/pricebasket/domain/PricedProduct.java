package pricebasket.domain;

import java.math.BigDecimal;

public class PricedProduct {

    private Product    product;
    private BigDecimal price;

    private PricedProduct(Product product, BigDecimal price) {
        this.product = product;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Product    product;
        private BigDecimal price;

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public PricedProduct build() {
            return new PricedProduct(product, price);
        }
    }
}
