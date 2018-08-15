package pricebasket;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pricebasket.discounts.Discount;
import pricebasket.discounts.DiscountRepository;
import pricebasket.domain.AppliedDiscount;
import pricebasket.domain.BasketPriceResult;
import pricebasket.domain.PricedProduct;
import pricebasket.domain.Product;
import pricebasket.prices.ProductPricer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BasketPricerTest.TestConfiguration.class)
public class BasketPricerTest {

    static Product product1 = Product.builder().name("P1").build();
    static Product product2 = Product.builder().name("P2").build();
    static Product product3 = Product.builder().name("P3").build();

    static List<Product> products = newArrayList(product1, product2, product3);

    static Map<Product, BigDecimal> prices = ImmutableMap.of(
            product1, new BigDecimal("1.23"),
            product2, BigDecimal.ONE,
            product3, new BigDecimal("0.60"));

    static Answer<PricedProduct> pricingAnswer = new Answer<PricedProduct>() {
        @Override
        public PricedProduct answer(InvocationOnMock invocation) throws Throwable {
            Product product = (Product)invocation.getArgument(0);
            return PricedProduct.builder().product(product).price(prices.get(product)).build();
        }
    };

    @Test
    public void priceWithDiscounts() {
        when(discountRepository.getDiscounts()).thenReturn(newArrayList(appliesDiscount));

        BasketPriceResult result = basketPricer.priceBasket(products);

        assertEquals(new BigDecimal("2.83"), result.getSubTotal());
        assertEquals(new BigDecimal("1.58"), result.getTotal());
        assertEquals(1, result.getAppliedDiscounts().size());
        assertEquals(new BigDecimal("1.25"), result.getAppliedDiscounts().get(0).getValue());
    }

    @Test
    public void priceWithNoDiscount() {
        when(discountRepository.getDiscounts()).thenReturn(newArrayList(rejectsDiscount));

        BasketPriceResult result = basketPricer.priceBasket(products);

        assertEquals(new BigDecimal("2.83"), result.getSubTotal());
        assertEquals(new BigDecimal("2.83"), result.getTotal());
        assertThat(result.getAppliedDiscounts(), hasItem(AppliedDiscount.NO_DISCOUNT));
    }

    @Test
    public void priceWithMixedDiscounts() {
        when(discountRepository.getDiscounts()).thenReturn(newArrayList(appliesDiscount, rejectsDiscount));

        BasketPriceResult result = basketPricer.priceBasket(products);

        assertEquals(new BigDecimal("2.83"), result.getSubTotal());
        assertEquals(new BigDecimal("1.58"), result.getTotal());
        assertEquals(1, result.getAppliedDiscounts().size());
        assertEquals(new BigDecimal("1.25"), result.getAppliedDiscounts().get(0).getValue());
    }

    @Autowired
    public BasketPricer basketPricer;

    @Autowired
    public ProductPricer productPricer;

    @Autowired
    public DiscountRepository discountRepository;

    @Autowired
    @Qualifier("appliesDiscount")
    public Discount appliesDiscount;

    @Autowired
    @Qualifier("rejectsDiscount")
    public Discount rejectsDiscount;

    @Configuration
    public static class TestConfiguration  {

        @Bean
        public BasketPricer basketPricer() {
            return new BasketPricer();
        }

        @Bean
        public ProductPricer productPricer() {
            ProductPricer productPricer = mock(ProductPricer.class);
            when(productPricer.getPriceForProduct(any())).then(pricingAnswer);
            return productPricer;
        }

        @Bean
        public DiscountRepository discountRepository() {
            return mock(DiscountRepository.class);
        }

        @Bean("appliesDiscount")
        public Discount appliesDiscount() {
            Discount discount =  mock(Discount.class);
            when(discount.isApplicable(any())).thenReturn(true);
            when(discount.calculate(any())).thenReturn(new BigDecimal("1.25"));
            return discount;
        }

        @Bean("rejectsDiscount")
        public Discount rejectsDiscount() {
            Discount discount =  mock(Discount.class);
            when(discount.isApplicable(any())).thenReturn(false);
            return discount;
        }

    }
}
