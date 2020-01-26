package org.crypto.publish;

import org.crypto.domain.Option;
import org.crypto.domain.SecurityPosition;
import org.crypto.domain.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.lineSeparator;

public class PortfolioPublisher {

    private final Map<String, SecurityPosition> positions;
    private final Map<Stock, Double> stockPricesStore;
    private final Map<Option, Double> optionsPricesStore;

    public PortfolioPublisher(Map<String, SecurityPosition> positions, Map<Stock, Double> stockPricesStore, Map<Option, Double> optionsPricesStore) {
        this.positions = positions;
        this.stockPricesStore = stockPricesStore;
        this.optionsPricesStore = optionsPricesStore;
    }

    public void publish(){
        List<Double> portfolioVals = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("***********************************").append(lineSeparator());
        positions.forEach((ticker, position) -> {
            stockPricesStore.keySet().stream()
                            .filter(stock -> stock.getTickerCode().equalsIgnoreCase(ticker))
                            .forEach(stock -> {
                                double value = stockPricesStore.get(stock) * positions.get(ticker).getQuantity();
                                portfolioVals.add(value);
                                sb.append(ticker).append("    ->    ").append(value).append(lineSeparator());
                            });
            optionsPricesStore.keySet().stream()
                    .filter(option -> option.getTickerCode().equalsIgnoreCase(ticker))
                    .forEach(option -> {
                        double value = optionsPricesStore.get(option) * positions.get(ticker).getQuantity();
                        portfolioVals.add(value);
                        sb.append(ticker).append("    ->    ").append(value).append(lineSeparator());
                    });
        });
        double sum = portfolioVals.stream().mapToDouble(x -> x).sum();
        sb.append("-----------------------------------").append(lineSeparator());
        sb.append("PORTFOLIO    ->    ").append(sum).append(lineSeparator());
        System.out.println(sb.toString());
    }


}
