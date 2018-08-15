package pricebasket.domain;

import java.math.BigDecimal;
import java.util.List;

public class BasketPriceResult {

    private BigDecimal subTotal;
    private List<AppliedDiscount> appliedDiscounts;

    private BasketPriceResult(BigDecimal subTotal, List<AppliedDiscount> appliedDiscounts) {
        this.subTotal = subTotal;
        this.appliedDiscounts = appliedDiscounts;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public List<AppliedDiscount> getAppliedDiscounts() {
        return appliedDiscounts;
    }

    public BigDecimal getTotal() {
        BigDecimal discountValue =
                appliedDiscounts.stream().map(appliedDiscount -> appliedDiscount.getValue()).reduce(
                        (BigDecimal::add)).orElse(BigDecimal.ZERO);
        return subTotal.subtract(discountValue);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BigDecimal subTotal;
        private List<AppliedDiscount> appliedDiscounts;

        public Builder subTotal(BigDecimal subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public Builder appliedDiscounts(List<AppliedDiscount> appliedDiscounts) {
            this.appliedDiscounts = appliedDiscounts;
            return this;
        }

        public BasketPriceResult build() {
            return new BasketPriceResult(subTotal, appliedDiscounts);
        }

    }
}
