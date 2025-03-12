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

/**
 * The `WeatherApp` class is the main entry point for the Weather Data Analyzer application.
 * It launches the app window, loads weather data from a CSV file, and displays the weather analysis.
 *
 * ## Features:
 * - Displays a background image and app icon.
 * - Shows a title and a button to analyze weather data.
 * - On button click, parses a CSV file containing weather data and displays a detailed report.
 *
 * ## Example usage:
 * ```java
 * WeatherApp.launch(args);
 * ```
 */
public class WeatherApp extends Application {

    /**
     * The `start` method sets up the primary user interface of the application.
     * It initializes the main window with a title, background image, and button.
     * On clicking the "Analyze Weather Data" button, the weather data is loaded and displayed.
     *
     * ### UI Components:
     * - Title label: Displays the app title "Weather Data Analyzer."
     * - Button: Clicking the button loads weather data from a CSV file and displays the results.
     * - Background image: Displays a weather-themed image behind the UI.
     *
     * ## Example usage:
     * ```java
     * launch(args);
     * ```
     *
     * @param stage The main stage of the JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Weather Data Analyzer");

        // App Icon
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/weather_icon.png"))));

        // Background Image
        Image backgroundImage = new Image(
                Objects.requireNonNull(getClass().getResourceAsStream("/images/weather_background.jpg"))
        );
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(600);

        // Title Label
        Label title = new Label("Weather Data Analyzer");
        title.getStyleClass().add("title");

        // Analyze Button
        Button analyzeButton = new Button("Analyze Weather Data");
        analyzeButton.setOnAction(e -> {
            try {
                // Load and display weather data from CSV file
                List<WeatherRecord> records = CsvParser.parse("weather_data.csv");
                WeatherController.displayWeatherData(records);
            } catch (IOException ex) {
                // Handle error if CSV data cannot be loaded
                title.setText("Error loading weather data!");
            }
        });

        // VBox layout to hold title and button
        VBox content = new VBox(15, title, analyzeButton);
        content.setAlignment(CENTER);
        content.getStyleClass().add("main-container");

        // StackPane to layer the background image and content
        StackPane root = new StackPane(backgroundView, content);

        // Create the scene and apply styles from external CSS
        Scene scene = new Scene(root, 400, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        // Set the scene and show the window
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method is the entry point to launch the JavaFX application.
     * It starts the JavaFX application by calling the `launch` method.
     *
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
