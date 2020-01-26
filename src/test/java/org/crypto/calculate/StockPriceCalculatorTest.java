package org.crypto.calculate;

import org.junit.Assert;
import org.junit.Test;

public class StockPriceCalculatorTest {
    @Test
    public void testDelta(){
        double delta = StockPriceCalculator.calculateDelta(10d, 0.5d, 1d, 0.6d);
        Assert.assertEquals(10d, (10d -delta), 0.01);
    }
}
