package org.example.weatherparser;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WeatherAnalyzer {
    public static void main(String[] args) {
        try {
            List<WeatherRecord> records = CsvParser.parse("weather_data.csv");

            // Lambda for average temperature calculation
            double avgTemp = records.stream()
                    .mapToDouble(WeatherRecord::temperature)
                    .average()
                    .orElse(Double.NaN);

            // Filtering days with temperature above a threshold
            double threshold = 30.0;
            List<WeatherRecord> hotDays = records.stream()
                    .filter(record -> record.temperature() > threshold)
                    .collect(Collectors.toList());

            // Counting rainy days using Predicate
            Predicate<WeatherRecord> isRainy = record -> record.precipitation() > 0;
            long rainyDays = records.stream().filter(isRainy).count();

            // Output using Text Blocks
            String report = """
                ===== Weather Report =====
                Average Temperature: %.2f°C
                Hot Days (> %.1f°C): %d
                Rainy Days: %d
                =========================
                """.formatted(avgTemp, threshold, hotDays.size(), rainyDays);
            System.out.println(report);

            // Categorizing weather using enhanced switch
            records.forEach(record -> System.out.printf("Date: %s - %s%n",
                    record.date(), WeatherCategory.categorize(record.temperature())));

        } catch (IOException e) {
            System.err.println("Error reading weather data: " + e.getMessage());
        }
    }
}
