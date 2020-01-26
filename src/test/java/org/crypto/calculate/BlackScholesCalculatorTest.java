package org.crypto.calculate;

import org.junit.Assert;
import org.junit.Test;

public class BlackScholesCalculatorTest {

    @Test
    public void testCallOption(){
        double price = BlackScholesCalculator.callPrice(23.75d,15.00d, 0.01d, 0.35d, 0.5d);
        Assert.assertEquals(8.879159263714117, price, 0.1);
    }

    @Test
    public void testPutOption(){
        double price = BlackScholesCalculator.putPrice(23.75d,25.00d, 0.01d, 0.35d, 0.5d);
        System.out.println(price);
        Assert.assertEquals(2.9985537709835306, price, 0.1);
    }
}
