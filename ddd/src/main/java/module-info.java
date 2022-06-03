module com.example.ddd {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ddd to javafx.fxml;
    exports com.example.ddd;
}