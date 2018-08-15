package pricebasket.discounts;

import java.math.BigDecimal;

/**
 * (Hopefully) helpful base class for discounts that will apply a percentage factor to the original price.
 */
public abstract class PricePercentageDiscount implements  Discount {

    private static final BigDecimal ONEHUNDRED = new BigDecimal("100.00");

    private BigDecimal discountPercent;

    public  PricePercentageDiscount(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public BigDecimal applyPercentage(BigDecimal price) {
        return price.multiply(discountPercent).divide(ONEHUNDRED);
    }

}
