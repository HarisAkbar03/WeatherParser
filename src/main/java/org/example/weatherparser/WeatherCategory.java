package org.example.weatherparser;

public enum WeatherCategory {
    HOT, WARM, COLD, UNKNOWN;

    public static WeatherCategory categorize(double temperature) {
        return switch ((int) temperature / 10) {
            case 3, 4 -> HOT;
            case 2 -> WARM;
            case 0, 1 -> COLD;
            default -> UNKNOWN;
        };
    }
}
