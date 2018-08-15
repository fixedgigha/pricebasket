package pricebasket.discounts;

import pricebasket.domain.PricedProduct;
import pricebasket.domain.Product;

import java.math.BigDecimal;
import java.util.Collection;

import static java.math.BigDecimal.ZERO;

/**
 * Discount applies to a second product if the customer buys a minimum quantity of a qualifying product.
 * Application to second quantity is limited (e.g. to 1 item purchased).
 * I will allow this discount to <i>apply</i> even if second product isn't purchased - behaviour to be clarified.
 *
 * scenario: Buy 2 tins of soup and get a loaf of bread for half price
 *
 */
public class CorrespondingProductDiscount extends PricePercentageDiscount {

    private Product qualifyingProduct;
    private int minimumQualifyingAmount;
    private Product discountedProduct;
    private int maximumApplicability;

    public CorrespondingProductDiscount(Product qualifyingProduct,
                                        int minimumQualifyingAmount,
                                        Product discountedProduct,
                                        int maximumApplicability,
                                        BigDecimal discountPercent) {
        super(discountPercent);
        this.qualifyingProduct = qualifyingProduct;
        this.minimumQualifyingAmount = minimumQualifyingAmount;
        this.discountedProduct = discountedProduct;
        this.maximumApplicability = maximumApplicability;
    }

    @Override
    public String describe() {
        return String.format("Buy %d of %s get %d %s at %.0f%% off",
                minimumQualifyingAmount,
                qualifyingProduct.getName(),
                maximumApplicability,
                discountedProduct.getName(),
                getDiscountPercent());
    }

    @Override
    public boolean isApplicable(Collection<PricedProduct> products) {
        long count =  products.stream()
                .filter(product -> {
                    boolean  match = qualifyingProduct.equals(product.getProduct());
                    return match;
                })
                .limit(minimumQualifyingAmount)
                .count();
        return count >= minimumQualifyingAmount;
    }

    @Override
    public BigDecimal calculate(Collection<PricedProduct> products) {
        return products.stream()
                .filter(product -> discountedProduct.equals(product.getProduct()))
                .limit(maximumApplicability)
                .map(PricedProduct::getPrice)
                .reduce(ZERO, (total, price) -> total.add(applyPercentage(price)));
    }
}
