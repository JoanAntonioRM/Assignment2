module com.mycompany.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.assignment2 to javafx.fxml;
    exports com.mycompany.assignment2;
}
