package pricebasket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pricebasket.discounts.DiscountRepository;
import pricebasket.domain.AppliedDiscount;
import pricebasket.domain.BasketPriceResult;
import pricebasket.domain.PricedProduct;
import pricebasket.domain.Product;
import pricebasket.prices.ProductPricer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;

/**
 * Main basket pricing job. Determines base prices for provided products (using ProductPricer) and
 * then applies discounts sourced from DiscountRepository.
 */
@Component
public class BasketPricer {

    @Autowired
    private ProductPricer productPricer;

    @Autowired
    private DiscountRepository discountRepository;

    public BasketPriceResult priceBasket(Collection<Product> products) {
        Collection<PricedProduct> pricedProducts = products.stream().map(product ->
                productPricer.getPriceForProduct(product)).collect(Collectors.toList());

        BigDecimal subTotal = pricedProducts.stream().map(product -> product.getPrice()).reduce(BigDecimal::add).orElse(ZERO);

        return BasketPriceResult.builder()
                .subTotal(subTotal)
                .appliedDiscounts(applyDiscounts(pricedProducts))
                .build();
    }

    private List<AppliedDiscount> applyDiscounts(Collection<PricedProduct> pricedProducts) {
        List<AppliedDiscount> appliedDiscounts =
                discountRepository.getDiscounts().stream()
                        .filter(discount -> discount.isApplicable(pricedProducts))
                        .map(discount -> AppliedDiscount.builder()
                                .description(discount.describe())
                                .value(discount.calculate(pricedProducts))
                                .build())
                        .collect(Collectors.toList());
        if (appliedDiscounts.isEmpty()) {
            appliedDiscounts = NO_DISCOUNTS;
        }
        return appliedDiscounts;
    }

    private static List<AppliedDiscount> NO_DISCOUNTS = Arrays.asList(new AppliedDiscount[] {AppliedDiscount.NO_DISCOUNT});
}
