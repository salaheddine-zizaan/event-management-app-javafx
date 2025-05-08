package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.eventmanagement.Controller.Event.EventController;
import org.example.eventmanagement.Model.Organizer;
import org.example.eventmanagement.utils.Session;

import java.io.IOException;

public class OrganizerController {
    @FXML private TextField FirstnameField;
    @FXML private TextField LastnameField;
    @FXML private TextField EmailField;
    @FXML private TextField CompanyField;
    @FXML private TextField PhoneField;
    @FXML private TextField UsernameField;
    @FXML private TextField FieldField;


    Organizer organizer = Session.getInstance().getLoggedInOrganizer();

        private String organizerEmail;

        public void setOrganizerEmail(String email) {
            this.organizerEmail = email;
            System.out.println("Logged in organizer: " + organizerEmail);
        }

        @FXML
        public void initialize() {
            // Optional: init logic
        }

        @FXML
        private void handleCreateEvent() {
            System.out.println("Create Event clicked!");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventmanagement/View/Event/EventForm.fxml"));
                Parent root = loader.load();
                EventController controller = loader.getController();

                Stage stage = new Stage();
                stage.setTitle("Create Event");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                // Optionally show an alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not open the Create Event window");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

}


