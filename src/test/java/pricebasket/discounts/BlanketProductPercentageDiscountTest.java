package pricebasket.discounts;

import org.junit.Test;
import pricebasket.domain.PricedProduct;
import pricebasket.domain.Product;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BlanketProductPercentageDiscountTest {

    static Product candidateProduct = Product.builder().name("candidate").build();
    static Product anotherProduct = Product.builder().name("another").build();
    static PricedProduct candidatePricedProduct = PricedProduct.builder().product(candidateProduct).price(new BigDecimal("150")).build();
    static PricedProduct anotherPricedProduct = PricedProduct.builder().product(anotherProduct).price(new BigDecimal("120")).build();

    Discount discount = new BlanketProductPercentageDiscount(candidateProduct, new BigDecimal("30"));

    @Test
    public void candidateProductNotGiven() {
        assertFalse("Discount should not be applicable", discount.isApplicable(newArrayList(anotherPricedProduct)));
    }

    @Test
    public void singleCandidateProductOnlyGiven() {
        List<PricedProduct> products = newArrayList(candidatePricedProduct);

        assertTrue("Discount should be applicable", discount.isApplicable(products));
        assertTrue("Applied discount 150 * 30% = 45", discount.calculate(products).compareTo(new BigDecimal("45")) == 0);
    }

    @Test
    public void multipleCandidateProductOnlyGiven() {
        List<PricedProduct> products = newArrayList(candidatePricedProduct, candidatePricedProduct);

        assertTrue("Discount should be applicable", discount.isApplicable(products));
        assertTrue("Applied discount 2 * 150 * 30% = 90", discount.calculate(products).compareTo(new BigDecimal("90")) == 0);
    }

    @Test
    public void mixedProductsGiven() {
        List<PricedProduct> products = newArrayList(candidatePricedProduct, anotherPricedProduct, candidatePricedProduct);

        assertTrue("Discount should be applicable", discount.isApplicable(products));
        assertTrue("Applied discount 2 * 150 * 30% = 90", discount.calculate(products).compareTo(new BigDecimal("90")) == 0);
    }

    @Test
    public void correctDescription() {
        assertEquals("candidate 30% off", discount.describe());
    }
}
