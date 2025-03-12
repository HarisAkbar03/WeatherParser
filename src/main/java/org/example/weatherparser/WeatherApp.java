package org.example.weatherparser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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


        Image backgroundImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/images/weather_background.jpg"))
        );
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600);
        //System.out.println(getClass().getResourceAsStream("/images/download.jpg") == null ? "Image Not Found!" : "Image Loaded Successfully!");


        // Title Label
        Label title = new Label("Weather Data Analyzer");
        title.getStyleClass().add("title");

        // Analyze Button
        Button analyzeButton = new Button("Analyze Weather Data");
        analyzeButton.setOnAction(e -> {
            try {
                List<WeatherRecord> records = CsvParser.parse("weather_data.csv");
                WeatherController.displayWeatherData(records);
            } catch (IOException ex) {
                title.setText("Error loading weather data!");
            }
        });

        VBox content = new VBox(15, title, analyzeButton);
        content.setAlignment(CENTER);
        content.getStyleClass().add("main-container");

        // StackPane to layer background image behind content
        StackPane root = new StackPane(backgroundView, content);

        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
