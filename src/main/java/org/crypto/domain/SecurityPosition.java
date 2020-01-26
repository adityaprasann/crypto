package org.crypto.domain;

public class SecurityPosition {
    private String tickerCode;
    private Integer quantity;

    public SecurityPosition(String tickerCode, Integer quantity) {
        this.tickerCode = tickerCode;
        this.quantity = quantity;
    }

    public String getTickerCode() {
        return tickerCode;
    }

    public void setTickerCode(String tickerCode) {
        this.tickerCode = tickerCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SecurityPosition{" +
                "tickerCode='" + tickerCode + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
