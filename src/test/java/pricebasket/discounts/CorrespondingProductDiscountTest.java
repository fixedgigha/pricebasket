package pricebasket.discounts;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import pricebasket.domain.PricedProduct;
import pricebasket.domain.Product;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CorrespondingProductDiscountTest {

    static Product qualifyingProduct = Product.builder().name("qualifying").build();
    static Product discountedProduct = Product.builder().name("discounted").build();
    static Product anotherProduct = Product.builder().name("another").build();
    static PricedProduct qualifyingPricedProduct = PricedProduct.builder().product(qualifyingProduct).price(new BigDecimal("150")).build();
    static PricedProduct discountedPricedProduct = PricedProduct.builder().product(discountedProduct).price(new BigDecimal("120")).build();
    static PricedProduct anotherPricedProduct = PricedProduct.builder().product(anotherProduct).price(new BigDecimal("120")).build();

    Discount discount = new CorrespondingProductDiscount(qualifyingProduct, 2, discountedProduct, 2, new BigDecimal("30"));

    @Test
    public void qualifyingProductNotGiven() {
        List<PricedProduct> products = Lists.newArrayList(discountedPricedProduct, anotherPricedProduct);

        assertFalse("Not applicable, no qualifying products", discount.isApplicable(products));
    }

    @Test
    public void insufficientQualifyingProductGiven() {
        List<PricedProduct> products = Lists.newArrayList(discountedPricedProduct, anotherPricedProduct, qualifyingPricedProduct);

        assertFalse("Not applicable, insufficient qualifying products", discount.isApplicable(products));
    }

    @Test
    public void exactQualifyingProductGiven() {
        List<PricedProduct> products = Lists.newArrayList(discountedPricedProduct, qualifyingPricedProduct, qualifyingPricedProduct);

        assertTrue("Discount should apply", discount.isApplicable(products));
        assertTrue("Applied discount 120 * 30% = 36", discount.calculate(products).compareTo(new BigDecimal("36")) == 0);
    }

    @Test
    public void excessiveQualifyingProductGiven() {
        List<PricedProduct> products = Lists.newArrayList(qualifyingPricedProduct, discountedPricedProduct, qualifyingPricedProduct, qualifyingPricedProduct);

        assertTrue("Discount should apply", discount.isApplicable(products));
        assertTrue("Applied discount 120 * 30% = 36", discount.calculate(products).compareTo(new BigDecimal("36")) == 0);
    }

    @Test
    public void excessiveDiscountableProductGiven() {
        List<PricedProduct> products = Lists.newArrayList(qualifyingPricedProduct, discountedPricedProduct, qualifyingPricedProduct, discountedPricedProduct, discountedPricedProduct);

        assertTrue("Discount should apply", discount.isApplicable(products));
        assertTrue("Applied discount 2 * 120 * 30% = 72", discount.calculate(products).compareTo(new BigDecimal("72")) == 0);
    }

    @Test
    public void correctDescription() {
        assertEquals("Buy 2 of qualifying get 2 discounted at 30% off", discount.describe());
    }

}
