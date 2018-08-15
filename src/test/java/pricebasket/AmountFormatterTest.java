package pricebasket;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AmountFormatterTest {

    @Test
    public void amountOnePound() {
        assertEquals("£1.00", AmountFormatter.format(BigDecimal.ONE));
    }

    @Test
    public void amountOverOnePound() {
        assertEquals("£1.34", AmountFormatter.format(new BigDecimal("1.34")));
    }

    @Test
    public void amountUnderOnePound() {
        assertEquals("99p", AmountFormatter.format(new BigDecimal("0.99")));
    }

    @Test
    public void amountOverNegOnePound() {
        assertEquals("£-1.34", AmountFormatter.format(new BigDecimal("-1.34")));
    }

    @Test
    public void amountUnderNegOnePound() {
        assertEquals("-99p", AmountFormatter.format(new BigDecimal("-0.99")));
    }
}
