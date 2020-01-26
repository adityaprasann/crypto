package org.crypto;

import org.crypto.domain.BaseSecurity;
import org.crypto.domain.Option;
import org.crypto.domain.SecurityPosition;
import org.crypto.domain.Stock;
import org.crypto.marketdata.MarketDataLoader;
import org.crypto.publish.PortfolioPublisher;
import org.crypto.staticdata.H2DatabaseOps;
import org.crypto.staticdata.SecurityPositionLoaderService;
import org.crypto.staticdata.TickerLoaderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TradePositionsApp {

    private final Map<String, List<? super BaseSecurity>> securities = new ConcurrentHashMap<>();//Holder for all static data from database
    private final Map<String, SecurityPosition> positions = new HashMap<>();//Holder for positions of all securities from csv file
    private final Map<Stock, Double> stockPricesStore = new ConcurrentHashMap<>();//Holder for latest stock prices
    private final Map<Option, Double> optionsPricesStore = new ConcurrentHashMap<>();//Holder for latest options prices
    private TickerLoaderService tickerLoaderService;
    private SecurityPositionLoaderService positionsLoaderService;
    private MarketDataLoader marketDataLoader;
    private PortfolioPublisher portfolioPublisher;

    private void createTickerLoader() {
        tickerLoaderService = new TickerLoaderService(securities, stockPricesStore, optionsPricesStore);
    }
    private void createPositionLoader() {
        positionsLoaderService = new SecurityPositionLoaderService(positions);
    }

    private void createPublisher() {
        portfolioPublisher = new PortfolioPublisher(positions, stockPricesStore, optionsPricesStore);
    }

    private void createMarketDataLoader() {
        marketDataLoader = new MarketDataLoader(stockPricesStore, securities, optionsPricesStore, portfolioPublisher);
    }

    private void setupH2Database() {
        H2DatabaseOps.createDatabase();
    }

    private void createComponents() {
        setupH2Database();
        createTickerLoader();
        createPositionLoader();
        createPublisher();
        createMarketDataLoader();
    }

    private void start() {
        tickerLoaderService.start();
        positionsLoaderService.start();
        marketDataLoader.start();
    }

    public static void main(String[] args) {
        TradePositionsApp app = new TradePositionsApp();
        app.createComponents();
        app.start();
}


}
