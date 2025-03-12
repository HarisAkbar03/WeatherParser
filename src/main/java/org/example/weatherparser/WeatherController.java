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

public class WeatherController {

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

        VBox reportBox = new VBox(10, reportLabel);
        reportBox.setAlignment(Pos.CENTER);
        reportBox.setPadding(new Insets(20));

        Image image = new Image(
                Objects.requireNonNull(WeatherController.class.getResourceAsStream("/images/weather.jpg"))
        );
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(400);

        // Use BorderPane to manage layout
        BorderPane root = new BorderPane();
        root.setTop(imageView);
        root.setCenter(reportBox);

        // Create and style the scene
        Scene scene = new Scene(root, 500, 600);
        scene.getStylesheets().add(Objects.requireNonNull(WeatherController.class.getResource("/style.css")).toExternalForm());

        // Create and show the stage
        Stage stage = new Stage();
        stage.setTitle("Weather Report");
        stage.setScene(scene);
        stage.show();
    }
}
