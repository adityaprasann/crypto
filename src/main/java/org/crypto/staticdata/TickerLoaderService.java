package org.crypto.staticdata;

import org.crypto.Services;
import org.crypto.domain.BaseSecurity;
import org.crypto.domain.Option;
import org.crypto.domain.Stock;

import java.util.List;
import java.util.Map;

import static org.crypto.Constants.*;

public class TickerLoaderService implements Services {

    private final Map<String, List<? super BaseSecurity>> securities;
    private final Map<Stock, Double> stockPricesStore;
    private final Map<Option, Double> optionsPricesStore;

    public TickerLoaderService(Map<String, List<? super BaseSecurity>> securities, Map<Stock, Double> stockPrices, Map<Option, Double> optionsPricesStore) {
        this.securities = securities;
        this.stockPricesStore = stockPrices;
        this.optionsPricesStore = optionsPricesStore;
    }

    private void populateSecurityList() {
        securities.put(STOCK_KEY, H2DatabaseOps.getStocks());
        securities.put(PUT_KEY, H2DatabaseOps.getOptions(PUT_KEY));
        securities.put(CALL_KEY, H2DatabaseOps.getOptions(CALL_KEY));
        initializeStocksStore(securities.get(STOCK_KEY));
        initializeOptionsStore(securities.get(PUT_KEY));
        initializeOptionsStore(securities.get(CALL_KEY));
    }

    private void initializeStocksStore(List<? super BaseSecurity> stocks) {
        stocks.forEach(stock-> stockPricesStore.putIfAbsent((Stock)stock, ((Stock) stock).getInitialPrice()));
    }

    private void initializeOptionsStore(List<? super BaseSecurity> options) {
        options.forEach(option -> optionsPricesStore.putIfAbsent((Option) option, 0.0d));
    }

    @Override
    public void start() {
        populateSecurityList();
    }

    @Override
    public void stop() {

    }
}
