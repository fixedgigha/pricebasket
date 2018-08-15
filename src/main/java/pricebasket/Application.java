package pricebasket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import pricebasket.domain.AppliedDiscount;
import pricebasket.domain.BasketPriceResult;
import pricebasket.domain.Product;

import java.io.PrintStream;
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
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private BasketPricer pricer;

    @Autowired
    private PrintStream output;

    @Bean
    @Profile("!Test")
    public PrintStream output() {
        return System.out;
    }

    @Override
    public void run(String... args) {
        LOG.debug("Running Basket Pricer");

        BasketPriceResult result = pricer.priceBasket(asProducts(args));

        output.printf("Subtotal: %s\n", format(result.getSubTotal()));
        for (AppliedDiscount discount : result.getAppliedDiscounts()) {

            output.println(discount.display());
        }
        output.printf("Total: %s\n", format(result.getTotal()));

    }

    private static Collection<Product> asProducts(String... names) {
        return
            Arrays.stream(names).map(name -> Product.builder().name(name).build()).
                    collect(Collectors.toList());
    }
}
