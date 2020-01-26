package org.crypto.domain;

import java.util.Objects;

public class BaseSecurity {
    private String tickerCode;

    public String getTickerCode() {
        return tickerCode;
    }

    public void setTickerCode(String tickerCode) {
        this.tickerCode = tickerCode;
    }

    public BaseSecurity(String tickerCode) {
        this.tickerCode = tickerCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseSecurity that = (BaseSecurity) o;
        return Objects.equals(tickerCode, that.tickerCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tickerCode);
    }

    @Override
    public String toString() {
        return "BaseSecurity{" +
                "tickerCode='" + tickerCode + '\'' +
                '}';
    }
}
