package pricebasket.discounts;

import org.springframework.stereotype.Component;
import pricebasket.domain.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * This implementation just instantiates concrete instances of discount classes. I would expect a more generic
 * solution would load discount data from a definition file or database.
 */
@Component
public class HardwiredDiscountRepository implements  DiscountRepository{

    private List<Discount> todaysDiscounts = Arrays.asList(new Discount[]{
            new BlanketProductPercentageDiscount(Product.builder().name("Apples").build(), new BigDecimal("10")),
            new CorrespondingProductDiscount(
                    Product.builder().name("Soup").build(),
                    2,
                    Product.builder().name("Bread").build(),
                    1,
                    new BigDecimal("50")
            )
    });

    @Override
    public List<Discount> getDiscounts() {
        return todaysDiscounts;
    }
}
