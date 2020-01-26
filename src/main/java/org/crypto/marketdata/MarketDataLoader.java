package org.crypto.marketdata;

import org.crypto.Services;
import org.crypto.calculate.OptionPricesCalculator;
import org.crypto.calculate.StockPriceCalculator;
import org.crypto.domain.BaseSecurity;
import org.crypto.domain.Option;
import org.crypto.domain.Stock;
import org.crypto.publish.PortfolioPublisher;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.crypto.Constants.STOCK_KEY;

public class MarketDataLoader implements Services {

    private final Map<Stock, Double> stockPricesStore;
    private final Map<String, List<? super BaseSecurity>>securities;
    private final static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private long randomDelay = ThreadLocalRandom.current().nextLong(500, 2000);// Generate a new stock price after a variable interval between 0.5s to 2s
    private final OptionPricesCalculator optionPricesCalculator;
    private final PortfolioPublisher portfolioPublisher;

    public MarketDataLoader(Map<Stock, Double> stockPricesStore, Map<String, List<? super BaseSecurity>> securities, Map<Option, Double> optionsPricesStore, PortfolioPublisher portfolioPublisher) {
        this.stockPricesStore = stockPricesStore;
        this.securities = securities;
        this.optionPricesCalculator = new OptionPricesCalculator(securities, stockPricesStore, optionsPricesStore);
        this.portfolioPublisher = portfolioPublisher;
    }

    private void populateMarketData() {
        Worker task = new Worker();
        executor.scheduleAtFixedRate(task, randomDelay, randomDelay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void start() {
        populateMarketData();
    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    private class Worker implements Runnable{
        @Override
        public void run() {
            MarketDataLoader.this.randomDelay = ThreadLocalRandom.current().nextLong(500, 2000);
            List<? super BaseSecurity> stocks = securities.get(STOCK_KEY);
            stocks.forEach(stock-> {
                double oldPrice = stockPricesStore.get(stock);
                double mean = ((Stock) stock).getMean();
                double stddev = ((Stock) stock).getStdev();
                double delta = StockPriceCalculator.calculateDelta(oldPrice, mean, randomDelay, stddev);
                //System.out.println("stock: " + ((Stock) stock).getTickerCode() + " oldPrice: " + oldPrice + " delay is: " + randomDelay + " newPrice: " + (oldPrice + delta));
                stockPricesStore.compute(((Stock) stock), (k, v) -> v + delta);
                optionPricesCalculator.updateOptionPrice(((Stock) stock));
            });
            portfolioPublisher.publish();
        }
    }
}
