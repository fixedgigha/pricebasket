package pricebasket.prices;

import pricebasket.domain.PricedProduct;
import pricebasket.domain.Product;

/**
 * Contract for Pricing engine implementations.
 */
public interface ProductPricer {

    PricedProduct getPriceForProduct(Product product);
}
