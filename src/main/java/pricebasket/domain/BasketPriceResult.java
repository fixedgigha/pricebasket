package pricebasket.domain;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Value
@Builder
public class BasketPriceResult {

    BigDecimal subTotal;
    List<AppliedDiscount> appliedDiscounts;
    public BigDecimal getTotal() {
        BigDecimal discountValue =
                appliedDiscounts.stream()
                        .map(AppliedDiscount::getValue)
                        .reduce(BigDecimal::add)
                        .orElse(ZERO);
        return subTotal.subtract(discountValue);
    }
}
