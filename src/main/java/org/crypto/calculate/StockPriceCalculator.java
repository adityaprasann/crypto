package org.crypto.calculate;

import java.util.Random;

public class StockPriceCalculator {
    public static double calculateDelta(double oldPrice, double mean, double randomDelay, double stdDev){
        double factor = randomDelay/7275600d;
        double epsilon = getEpsilon();
        return oldPrice * ((mean * factor) + (stdDev * epsilon * Math.sqrt(factor)));
    }

    private static double getEpsilon(){
        Random ran = new Random();
        return ran.nextGaussian();
    }
}
