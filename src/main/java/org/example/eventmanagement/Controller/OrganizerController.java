package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.example.eventmanagement.Controller.Event.EventController;
import org.example.eventmanagement.Model.Organizer;
import org.example.eventmanagement.utils.SceneManager;
import org.example.eventmanagement.utils.Session;

public class OrganizerController {
    @FXML private TextField FirstnameField;
    @FXML private TextField LastnameField;
    @FXML private TextField EmailField;
    @FXML private TextField CompanyField;
    @FXML private TextField PhoneField;
    @FXML private TextField UsernameField;
    @FXML private TextField FieldField;


    Organizer organizer = Session.getInstance().getLoggedInOrganizer();

    public void initialize() {

    }

    public void gotoEvents() {
        SceneManager.switchScene("/org/example/eventmanagement/View/Event/EventForm.fxml");
    }

    public void gotoEditEvents() {
        SceneManager.switchScene("/org/example/eventmanagement/View/Event/edit_event_form.fxml");
    }
}


