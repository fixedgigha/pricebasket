package pricebasket.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PricedProduct {
    Product    product;
    BigDecimal price;
}
