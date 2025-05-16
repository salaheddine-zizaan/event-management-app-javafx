module org.example.eventmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;
    requires java.sql;
    requires jbcrypt;
    requires jdk.jdi;

    opens org.example.eventmanagement to javafx.fxml;
    exports org.example.eventmanagement;
    exports org.example.eventmanagement.Controller;
    opens org.example.eventmanagement.Controller to javafx.fxml;
    opens org.example.eventmanagement.Controller.Auth to javafx.fxml;
    exports org.example.eventmanagement.utils;
    opens org.example.eventmanagement.utils to javafx.fxml;
    opens org.example.eventmanagement.Model to javafx.fxml;
    exports org.example.eventmanagement.Model;
    opens org.example.eventmanagement.Controller.Event to javafx.fxml;
    opens org.example.eventmanagement.Controller.Event.Components to javafx.fxml;
    opens org.example.eventmanagement.Controller.organizer to javafx.fxml;
    exports org.example.eventmanagement.Controller.Event ;
    exports org.example.eventmanagement.Controller.Event.Components;
    exports org.example.eventmanagement.Controller.organizer to javafx.fxml;

}