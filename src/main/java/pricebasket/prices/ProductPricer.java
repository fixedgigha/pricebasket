package pricebasket.prices;

import pricebasket.domain.Product;

import java.math.BigDecimal;

public interface ProductPricer {

    BigDecimal getPriceForProduct(Product product);
}
