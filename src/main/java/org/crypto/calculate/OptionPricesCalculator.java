package org.crypto.calculate;

import org.crypto.domain.BaseSecurity;
import org.crypto.domain.Option;
import org.crypto.domain.Stock;

import java.util.List;
import java.util.Map;

import static org.crypto.Constants.*;

public class OptionPricesCalculator {
    private final Map<Stock, Double> stockPricesStore;
    private final Map<Option, Double> optionsPricesStore;
    private final Map<String, List<? super BaseSecurity>>securities;

    public OptionPricesCalculator(Map<String, List<? super BaseSecurity>> securities, Map<Stock, Double> stockPricesStore, Map<Option, Double> optionsPricesStore) {
        this.securities = securities;
        this.stockPricesStore = stockPricesStore;
        this.optionsPricesStore = optionsPricesStore;
    }

    public void updateOptionPrice(Stock stock){
        List<? super BaseSecurity> calls = securities.get(CALL_KEY);
        List<? super BaseSecurity> puts = securities.get(PUT_KEY);
        String stockTicker = stock.getTickerCode();
        double stdDev = stock.getStdev();
        calls.stream()
                .filter(call -> ((Option)call).getUnderlyer().equalsIgnoreCase(stockTicker))
                .forEach(call -> {
                    double currentStockPrice = stockPricesStore.get(stock);
                    double strikePrice = ((Option)call).getStrike();
                    double maturity = ((Option)call).getMaturity();
                    optionsPricesStore.compute(((Option) call),
                            (k, v) -> BlackScholesCalculator.callPrice(currentStockPrice, strikePrice, RISK_FREE_RATE, stdDev, maturity));
                });
        puts.stream()
                .filter(put -> ((Option)put).getUnderlyer().equalsIgnoreCase(stockTicker))
                .forEach(put -> {
                    double currentStockPrice = stockPricesStore.get(stock);
                    double strikePrice = ((Option)put).getStrike();
                    double maturity = ((Option)put).getMaturity();
                    optionsPricesStore.compute(((Option) put),
                            (k, v) -> BlackScholesCalculator.putPrice(currentStockPrice, strikePrice, RISK_FREE_RATE, stdDev, maturity));
                });
    }
}
