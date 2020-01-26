package org.crypto.staticdata;

import org.crypto.Services;
import org.crypto.domain.SecurityPosition;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.currentThread;
import static java.nio.file.Files.lines;
import static java.nio.file.Paths.get;
import static java.util.Objects.requireNonNull;

public class SecurityPositionLoaderService implements Services {

    private Map<String, SecurityPosition>  positions;
    private final String fileName = "tickerpositions.csv";

    public SecurityPositionLoaderService(Map<String, SecurityPosition> positions) {
        this.positions = positions;
    }

    public void loadPositionsFromCsv() {
        try (Stream<String> lines = lines(get(requireNonNull(currentThread().getContextClassLoader().getResource(fileName)).toURI()))) {
            List<List<String>> values = lines.map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
            values.forEach(value -> positions.put(value.get(0), new SecurityPosition(value.get(0), Integer.parseInt(value.get(1)))));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        loadPositionsFromCsv();
    }

    @Override
    public void stop() {

    }
}
