package org.crypto.calculate;

import org.crypto.domain.BaseSecurity;
import org.crypto.domain.Option;
import org.crypto.domain.Stock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.crypto.Constants.*;

public class OptionsPricesCalculatorTest {

    private final Map<String, List<? super BaseSecurity>> securities = new ConcurrentHashMap<>();
    private final Map<Stock, Double> stockPricesStore = new ConcurrentHashMap<>();
    private final Map<Option, Double> optionsPricesStore = new ConcurrentHashMap<>();
    OptionPricesCalculator optionPricesCalculator;
    Stock stock1;
    Stock stock2;

    @Before
    public void populateSecurityList() {
        List<? super BaseSecurity> tempStocks = new ArrayList<>();
        List<? super BaseSecurity> tempPuts = new ArrayList<>();
        List<? super BaseSecurity> tempCalls = new ArrayList<>();
        stock1 = new Stock("STOCK1");
        stock1.setMean(0.8d);
        stock1.setStdev(0.6d);
        stock1.setInitialPrice(100);
        stock2 = new Stock("STOCK2");
        stock2.setMean(0.7d);
        stock2.setStdev(0.3d);
        stock2.setInitialPrice(150);
        tempStocks.add(stock1);
        tempStocks.add(stock2);

        Option callOption = new Option("CALLOPTION1");
        callOption.setType(CALL_KEY);
        callOption.setMaturity(10d);
        callOption.setStrike(65d);
        callOption.setUnderlyer("STOCK1");
        tempCalls.add(callOption);

        Option putOption = new Option("PUTOPTION1");
        putOption.setType(PUT_KEY);
        putOption.setMaturity(15d);
        putOption.setStrike(145d);
        putOption.setUnderlyer("STOCK2");
        tempPuts.add(putOption);

        securities.put(STOCK_KEY, tempStocks);
        securities.put(PUT_KEY, tempPuts);
        securities.put(CALL_KEY, tempCalls);
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

    @Test
    public void testOptionCalculatorPrice(){
        optionPricesCalculator = new OptionPricesCalculator(securities, stockPricesStore, optionsPricesStore);
        optionPricesCalculator.updateOptionPrice(stock1);
        optionPricesCalculator.updateOptionPrice(stock2);
        optionsPricesStore.forEach((option, value) -> {
            if(option.getTickerCode().equalsIgnoreCase("CALLOPTION1"))
                Assert.assertEquals(75.68405380393358, value, 0.1);
            if(option.getTickerCode().equalsIgnoreCase("PUTOPTION1"))
                Assert.assertEquals(37.22029531674663, value, 0.1);
        });
    }

}
