package org.example.weatherparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CsvParser {
    public static List<WeatherRecord> parse(String fileName) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(CsvParser.class.getClassLoader().getResourceAsStream(fileName))))) {

            return reader.lines()
                    .skip(1) // Skip header
                    .map(line -> line.split(","))
                    .map(parts -> new WeatherRecord(parts[0],
                            Double.parseDouble(parts[1]),
                            Integer.parseInt(parts[2]),
                            Double.parseDouble(parts[3])))
                    .collect(Collectors.toList());
        }
    }
}

