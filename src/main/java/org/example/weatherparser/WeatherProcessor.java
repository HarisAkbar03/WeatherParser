package org.example.weatherparser;

@FunctionalInterface
public interface WeatherProcessor {
    void process(java.util.List<WeatherRecord> records);
}
