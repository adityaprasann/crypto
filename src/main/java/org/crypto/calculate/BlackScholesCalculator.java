package org.crypto.calculate;

public class BlackScholesCalculator {
    // return pdf(x) = standard Gaussian pdf
    public static double pdf(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

    // return cdf(z) = standard Gaussian cdf using Taylor approximation
    public static double cdf(double z) {
        if (z < -8.0) return 0.0;
        if (z >  8.0) return 1.0;
        double sum = 0.0, term = z;
        for (int i = 3; sum + term != sum; i += 2) {
            sum  = sum + term;
            term = term * z * z / i;
        }
        return 0.5 + sum * pdf(z);
    }

    public static double callPrice(double stockPrice, double strikePrice, double riskFreeRate, double sigma, double maturity) {
        double d1 = (Math.log(stockPrice/strikePrice) + (riskFreeRate + sigma * sigma/2) * maturity) / (sigma * Math.sqrt(maturity));
        double d2 = d1 - sigma * Math.sqrt(maturity);
        return stockPrice * cdf(d1) - strikePrice * Math.exp(-riskFreeRate*maturity) * cdf(d2);
    }

    public static double putPrice(double stockPrice, double strikePrice, double riskFreeRate, double sigma, double maturity) {
        double d1 = (Math.log(stockPrice/strikePrice) + (riskFreeRate + sigma * sigma/2) * maturity) / (sigma * Math.sqrt(maturity));
        double d2 = d1 - sigma * Math.sqrt(maturity);
        return strikePrice * Math.exp(-riskFreeRate*maturity) * cdf(-d2) - stockPrice * cdf(-d1);
    }
}
