package pricebasket.prices;

import org.springframework.stereotype.Component;
import pricebasket.domain.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
    public BigDecimal getPriceForProduct(Product product) {
        BigDecimal price = priceMap.get(product.getName());
        if (price == null) {
            // Behaviour is undefined, I will throw an exception.
            throw new RuntimeException(String.format("Product %s is unkown", product.getName()));
        }
        return price;
    }
}
