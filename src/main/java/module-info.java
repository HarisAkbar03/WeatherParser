module org.example.weatherparser {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.weatherparser to javafx.fxml;
    exports org.example.weatherparser;
}