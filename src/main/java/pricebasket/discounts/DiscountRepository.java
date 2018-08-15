package pricebasket.discounts;

import java.util.List;

/**
 * Contract for components returning applicable discounts.
 */
public interface DiscountRepository {

    public List<Discount> getDiscounts();
}
