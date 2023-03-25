module com.example.lab2 {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;


    opens com.example.lab2 to javafx.fxml, javafx.base;

    exports com.example.lab2;
    exports calculations;
    opens calculations to javafx.base, javafx.fxml;
}