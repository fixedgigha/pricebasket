package pricebasket.prices;

import org.springframework.stereotype.Component;
import pricebasket.domain.PricedProduct;
import pricebasket.domain.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Pricing engine implmentation that loads product prices from a classpath root file <i>prices.properties</i>.
 * Prices should appear in standard java property form - Apple=1.30.
 * Prices are decimals expressed in pounds.
 */
@Component
public class ClasspathPropertiesProductPricer implements ProductPricer {

    private Map<String, BigDecimal> priceMap;

    public ClasspathPropertiesProductPricer() {
        Properties asProps = new Properties();
        try {
            asProps.load(getClass().getResourceAsStream("/prices.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load prices.properties", e);
        }
        priceMap = new HashMap<>();
        for (String name : asProps.stringPropertyNames()) {
            priceMap.put(name, new BigDecimal(asProps.getProperty(name)));
        }
    }

    @Override
    public PricedProduct getPriceForProduct(Product product) {
        BigDecimal price = priceMap.get(product.getName());
        if (price == null) {
            // Behaviour is undefined currently, I will throw an exception.
            throw new UnpriceableProductException(product);
        }
        return PricedProduct.builder().product(product).price(price).build();
    }
}
