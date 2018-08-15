package pricebasket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pricebasket.domain.AppliedDiscount;
import pricebasket.domain.BasketPriceResult;
import pricebasket.domain.Product;
import pricebasket.prices.ProductPricer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static java.math.BigDecimal.ZERO;

@Component
public class BasketPricer {

    @Autowired
    private ProductPricer productPricer;

    public BasketPriceResult priceBasket(Collection<Product> products) {
        BigDecimal subTotal = products.stream().map(product -> productPricer.getPriceForProduct(product)).reduce(BigDecimal::add).orElse(ZERO);

        return BasketPriceResult.builder().subTotal(subTotal).
                appliedDiscounts(Arrays.asList(new AppliedDiscount[] {
                        AppliedDiscount.NO_DISCOUNT
                })).build();
    }
}
