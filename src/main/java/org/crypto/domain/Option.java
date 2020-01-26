package org.crypto.domain;

public class Option extends BaseSecurity{

    private double strike;
    private double maturity;
    private String type;
    private String underlyer;

    public Option(String tickerCode) {
        super(tickerCode);
    }

    public double getStrike() {
        return strike;
    }

    public void setStrike(double strike) {
        this.strike = strike;
    }

    public double getMaturity() {
        return maturity;
    }

    public void setMaturity(double maturity) {
        this.maturity = maturity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnderlyer() {
        return underlyer;
    }

    public void setUnderlyer(String underlyer) {
        this.underlyer = underlyer;
    }
}
