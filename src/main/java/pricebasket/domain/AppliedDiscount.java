package pricebasket.domain;

import java.math.BigDecimal;

public class AppliedDiscount {

    public static final AppliedDiscount NO_DISCOUNT =
            new AppliedDiscount("(no offers available)", BigDecimal.ZERO) {
                public String display() {
                    return getDescription();
                }
            };

    private String description;
    private BigDecimal value;

    private AppliedDiscount(String description, BigDecimal value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String display() {
        return String.format("%s: -%s", getDescription(), getValue());
    }
}
