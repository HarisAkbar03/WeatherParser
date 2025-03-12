package org.example.weatherparser;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherController {
    public static void displayWeatherData(List<WeatherRecord> records) {
        double avgTemp = records.stream()
                .mapToDouble(WeatherRecord::temperature)
                .average()
                .orElse(Double.NaN);

        long rainyDays = records.stream()
                .filter(record -> record.precipitation() > 0)
                .count();

        String reportText = """
                🌦️ Weather Report
                ------------------
                🌡️ Avg Temp: %.2f°C
                🌧️ Rainy Days: %d
                """.formatted(avgTemp, rainyDays);

        Label reportLabel = new Label(reportText);
        reportLabel.getStyleClass().add("report-text");

        // UI Card Effect
        Rectangle card = new Rectangle(280, 150);
        card.setArcWidth(20);
        card.setArcHeight(20);
        card.setFill(Color.web("#ffffff"));
        card.setOpacity(0.8);

        VBox reportBox = new VBox(10, reportLabel);
        reportBox.setAlignment(Pos.CENTER);
        reportBox.setPadding(new Insets(20));

        Scene scene = new Scene(reportBox, 400, 600);
        scene.getStylesheets().add(WeatherController.class.getResource("/style.css").toExternalForm());

        Stage stage = new Stage();
        stage.setTitle("Weather Report");
        stage.setScene(scene);
        stage.show();
    }
}
