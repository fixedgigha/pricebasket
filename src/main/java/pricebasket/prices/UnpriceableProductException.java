package pricebasket.prices;

import pricebasket.domain.Product;

public class UnpriceableProductException extends RuntimeException {

    private Product product;

    public UnpriceableProductException(Product product) {
        super("Unable to price product " + product.getName());
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
