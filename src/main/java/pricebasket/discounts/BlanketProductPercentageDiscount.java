package pricebasket.discounts;

import pricebasket.domain.Product;
import pricebasket.domain.PricedProduct;

import java.math.BigDecimal;
import java.util.Collection;

import static java.math.BigDecimal.ZERO;

/**
 * For a given Product, apply an overall percentage discount to all purchases of that product.
 *
 * scenario: Apples have 10% off their normal price this week
 */
public class BlanketProductPercentageDiscount extends PricePercentageDiscount {

    private Product    product;

    public BlanketProductPercentageDiscount(Product product, BigDecimal discountPercent) {
        super(discountPercent);
        this.product = product;
    }

    @Override
    public String describe() {
        return String.format("%s %.0f%% off", product.getName(), getDiscountPercent());
    }

    @Override
    public boolean isApplicable(Collection<PricedProduct> products) {
        return products.stream()
                .anyMatch(candidate -> product.equals(candidate.getProduct()));
    }

    @Override
    public BigDecimal calculate(Collection<PricedProduct> products) {
        return products.stream()
                .filter(candidate -> product.equals(candidate.getProduct()))
                .map(PricedProduct::getPrice)
                .reduce(ZERO, (total, price) -> total.add(applyPercentage(price)));
    }
}
