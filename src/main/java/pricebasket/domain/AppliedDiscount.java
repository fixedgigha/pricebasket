package pricebasket.domain;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;
import pricebasket.AmountFormatter;

import java.math.BigDecimal;

/**
 * Captures output of applying a discount to a product list.
 */
@Value
@Builder
@NonFinal // making this non final so I can subclass the display()  of NO_DISCOUNT
public class AppliedDiscount {

    public static final AppliedDiscount NO_DISCOUNT =
            new AppliedDiscount("(no offers available)", BigDecimal.ZERO) {
                public String display() {
                    return getDescription();
                }
            };

    String description;
    BigDecimal value;

    public String display() {
        return String.format("%s: -%s", getDescription(), AmountFormatter.format(getValue()));
    }
}
