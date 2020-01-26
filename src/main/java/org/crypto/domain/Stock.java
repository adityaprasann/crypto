package org.crypto.domain;

public class Stock extends BaseSecurity{

    double mean;
    double stdev;
    double initialPrice;

    public Stock(String tickerCode) {
        super(tickerCode);
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getStdev() {
        return stdev;
    }

    public void setStdev(double stdev) {
        this.stdev = stdev;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }
}
