package pricebasket.domain;

import pricebasket.AmountFormatter;

import java.math.BigDecimal;

/**
 * Captures output of applying a discount to a product list.
 */
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

    /**
     * Value of the discount is returned as a positive price (assuming a reduction).
     * @return
     */
    public BigDecimal getValue() {
        return value;
    }

    public String display() {
        return String.format("%s: -%s", getDescription(), AmountFormatter.format(getValue()));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String description;
        private BigDecimal value;

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }

        public AppliedDiscount build() {
            return new AppliedDiscount(description, value);
        }
    }
}
