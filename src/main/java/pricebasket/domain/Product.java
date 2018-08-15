package pricebasket.domain;

public class Product {

    private String name;

    public String getName() {
        return name;
    }

    private Product(String name) {
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Product build() {
            return new Product(name);
        }
    }
}
