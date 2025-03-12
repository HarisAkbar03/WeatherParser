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

        // Create the report label
        Label reportLabel = new Label(reportText);
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
        Image image  = new Image(
                Objects.requireNonNull(WeatherController.class.getResourceAsStream("/images/weather.jpg"))
        ); // Replace with your image path
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);  // Adjust width as needed
        imageView.setFitHeight(200); // First quarter size (adjust height based on your screen size)

        // Use BorderPane to manage layout
        BorderPane root = new BorderPane();
        root.setTop(imageView);      // Place the image in the top
        root.setCenter(reportBox);   // Place the weather report in the center

        // Create and style the scene
        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(WeatherController.class.getResource("/style.css").toExternalForm());

        // Create and show the stage
        Stage stage = new Stage();
        stage.setTitle("Weather Report");
        stage.setScene(scene);
        stage.show();
    }
}
