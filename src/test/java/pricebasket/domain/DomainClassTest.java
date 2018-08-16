package pricebasket.domain;

import org.assertj.core.util.Lists;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DomainClassTest {

    @Test
    public void domainObjectsWireCorrectly() {
        Product product = Product.builder().name("testProduct").build();
        assertEquals("testProduct", product.getName());

        BigDecimal price = new BigDecimal("4.56");
        PricedProduct pricedProduct = PricedProduct.builder().product(product).price(price).build();
        assertEquals(price, pricedProduct.getPrice());
        assertEquals(product, pricedProduct.getProduct());

        BigDecimal discountValue = new BigDecimal("7.89");
        AppliedDiscount discount = AppliedDiscount.builder().description("appliedDiscount").value(discountValue).build();
        assertEquals("appliedDiscount", discount.getDescription());
        assertEquals(discountValue, discount.getValue());

        BigDecimal subTotal = new BigDecimal("12.34");
        BasketPriceResult priceResult = BasketPriceResult.builder().subTotal(subTotal).appliedDiscounts(Lists.newArrayList(discount)).build();
        assertEquals(subTotal, priceResult.getSubTotal());
        assertThat(priceResult.getAppliedDiscounts(), CoreMatchers.hasItem(discount));
        assertEquals(new BigDecimal("4.45"), priceResult.getTotal());
    }

}
