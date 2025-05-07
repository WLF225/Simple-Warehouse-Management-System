module com.example.project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project3 to javafx.fxml;
    exports com.example.project3;
    exports com.example.project3.FX;
    opens com.example.project3.FX to javafx.fxml;

    opens com.example.project3.Classes to javafx.base;
}