package pricebasket;

import java.math.BigDecimal;

public class AmountFormatter {

    private static BigDecimal ONEHUNDRED = new BigDecimal("100");

    public static String format(BigDecimal amount) {
        if (BigDecimal.ONE.compareTo(amount.abs()) <= 0) {
            return String.format("£%.2f", amount);
        }
        return String.format("%.0fp", amount.multiply(ONEHUNDRED));
    }
}
