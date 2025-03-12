package org.example.weatherparser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static javafx.geometry.Pos.CENTER;

public class WeatherApp extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Weather Data Analyzer");

        // App Icon
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/weather_icon.png"))));

        // Background Image
        ImageView background = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/weather_background.jpg"))));
        background.setFitWidth(800);
        background.setFitHeight(600);
        background.setOpacity(0.7); // Slight transparency

        // Title
        javafx.scene.control.Label title = new javafx.scene.control.Label("Weather Data Analyzer");
        title.getStyleClass().add("title");

        Button analyzeButton = new Button("Analyze Weather Data");

        analyzeButton.setOnAction(e -> {
            try {
                List<WeatherRecord> records = CsvParser.parse("weather_data.csv");
                WeatherController.displayWeatherData(records);
            } catch (IOException ex) {
                title.setText("Error loading weather data!");
            }
        });

        VBox root = new VBox(15, title, analyzeButton);
        root.setAlignment(CENTER);
        root.getStyleClass().add("main-container");

        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
