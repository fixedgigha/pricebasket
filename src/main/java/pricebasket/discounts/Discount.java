package pricebasket.discounts;

import pricebasket.domain.PricedProduct;

import java.math.BigDecimal;
import java.util.Collection;

public interface Discount {
    /**
     * Human (end user) readable text that describes the disocunt.
     */
    String describe();
    /**
     *
     * @param products Product set to be determined for applicability
     * @return true if a discount can apply
     */
    boolean isApplicable(Collection<PricedProduct> products);
    /**
     *
     * @param products Product set from which discount will be applied
     * @return total value of the discount for products, where a positive amount is the amount to be discounted.
     */
    BigDecimal calculate(Collection<PricedProduct> products);
}
