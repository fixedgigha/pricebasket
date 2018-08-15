package pricebasket.prices;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pricebasket.domain.Product;

import java.math.BigDecimal;

/**
 * Note - I've added an overriding price.properties file in src/test/resources to allows for changes in
 * the src/main/price.properties file
 */
public class ClasspathPropertiesProductPricerTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    static Product stuff1 = Product.builder().name("Stuff1").build();
    static Product stuff2 = Product.builder().name("Stuff2").build();
    static Product unknown = Product.builder().name("Unkown").build();

    @Test
    public void pricesLoadedCorrectly() {
        ProductPricer pricer = new ClasspathPropertiesProductPricer();

        Assert.assertEquals(new BigDecimal("0.66"), pricer.getPriceForProduct(stuff1).getPrice());
        Assert.assertEquals(new BigDecimal("1.23"), pricer.getPriceForProduct(stuff2).getPrice());
    }

    @Test
    public void unknownProductRaisesException() {
        thrown.expect(UnpriceableProductException.class);

        ProductPricer pricer = new ClasspathPropertiesProductPricer();
        pricer.getPriceForProduct(unknown);
    }
}
