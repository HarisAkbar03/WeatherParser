package org.example.weatherparser;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;

/**
 * The `WeatherController` class is responsible for displaying the weather data
 * in a styled window with an image and weather report.
 *
 * This class calculates average temperature, hot days (above threshold),
 * and rainy days from the list of weather records and displays them.
 *
 * ## Example usage:
 *
 * ```java
 * List<WeatherRecord> records = CsvParser.parse("weather_data.csv");
 * WeatherController.displayWeatherData(records);
 * ```
 */
public class WeatherController {

    /**
     * Displays the weather data including average temperature, hot days,
     * and rainy days in a window.
     *
     * This method takes a list of `WeatherRecord` objects, calculates
     * average temperature, counts the hot days, and counts the rainy days.
     * It then shows the weather information in a styled window with an image
     * at the top and the report displayed below.
     *
     * ### Features:
     * - Displays an image at the top (first quarter of the window)
     * - Displays the weather report below the image.
     * - Applies custom styles including gradients and drop shadows.
     *
     * ## Example usage:
     *
     * ```java
     * List<WeatherRecord> records = CsvParser.parse("weather_data.csv");
     * WeatherController.displayWeatherData(records);
     * ```
     *
     * @param records List of `WeatherRecord` objects containing weather data.
     */
    public static void displayWeatherData(List<WeatherRecord> records) {
        // Calculate average temperature
        double avgTemp = records.stream()
                .mapToDouble(WeatherRecord::temperature)
                .average()
                .orElse(Double.NaN);

        // Define the temperature threshold for hot days
        double threshold = 30.0;

        // Filter days that have temperature above the threshold (hot days)
        long hotDays = records.stream()
                .filter(record -> record.temperature() > threshold)
                .count();

        // Count rainy days
        long rainyDays = records.stream()
                .filter(record -> record.precipitation() > 0)
                .count();

        // Define the report text, including hot days and rainy days
        String report = """
                ===== Weather Report =====
                Average Temperature: %.2f°C
                Hot Days (> %.1f°C): %d
                Rainy Days: %d
                =========================
                """.formatted(avgTemp, threshold, hotDays, rainyDays);

        // Create the report label
        Label reportLabel = new Label(report);
        reportLabel.getStyleClass().add("report-text");

        // Create UI Card Effect
        Rectangle card = new Rectangle(280, 150);
        card.setArcWidth(20);
        card.setArcHeight(20);
        card.setFill(Color.web("#ffffff"));
        card.setOpacity(0.8);

        // VBox to hold the weather information
        VBox reportBox = new VBox(10, reportLabel);
        reportBox.setAlignment(Pos.CENTER);
        reportBox.setPadding(new Insets(20));

        // Load and display image in the top quarter
        Image image = new Image(
                Objects.requireNonNull(WeatherController.class.getResourceAsStream("/images/weather.jpg"))
        ); // Replace with your image path
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);  // Adjust width as needed
        imageView.setFitHeight(400); // First quarter size (adjust height based on your screen size)

        // Use BorderPane to manage layout
        BorderPane root = new BorderPane();
        root.setTop(imageView);      // Place the image in the top
        root.setCenter(reportBox);   // Place the weather report in the center

        // Create and style the scene
        Scene scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(WeatherController.class.getResource("/style.css").toExternalForm());

        // Create and show the stage
        Stage stage = new Stage();
        stage.setTitle("Weather Report");
        stage.setScene(scene);
        stage.show();
    }
}
