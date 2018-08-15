package pricebasket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pricebasket.domain.AppliedDiscount;
import pricebasket.domain.BasketPriceResult;
import pricebasket.domain.Product;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static pricebasket.AmountFormatter.format;

@SpringBootApplication
public class Application
        implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(Application.class);

    public static void main(String[] args) {
        LOG.debug("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        LOG.debug("APPLICATION FINISHED");
    }

    @Autowired
    private BasketPricer pricer;

    @Override
    public void run(String... args) {
        LOG.debug("Running Basket Pricer");

        BasketPriceResult result = pricer.priceBasket(asProducts(args));

        System.out.printf("Subtotal: %s\n", format(result.getSubTotal()));
        for (AppliedDiscount discount : result.getAppliedDiscounts()) {

            System.out.println(discount.display());
        }
        System.out.printf("Total: %s\n", format(result.getTotal()));

    }

    private static Collection<Product> asProducts(String... names) {
        return
            Arrays.stream(names).map(name -> Product.builder().name(name).build()).
                    collect(Collectors.toList());
    }
}
