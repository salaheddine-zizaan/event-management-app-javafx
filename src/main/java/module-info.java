module org.example.eventmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.sql;
    requires jbcrypt;

    opens org.example.eventmanagement to javafx.fxml;
    exports org.example.eventmanagement;
    exports org.example.eventmanagement.Controller;
    opens org.example.eventmanagement.Controller to javafx.fxml;
    opens org.example.eventmanagement.Controller.Auth to javafx.fxml;

}