package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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
    @FXML private AnchorPane mainContent_admin;


    Organizer organizer = Session.getInstance().getLoggedInOrganizer();

    @FXML
    public void initialize() {
/*
        FirstnameField.setText(organizer.getFirstName());
*/
        // Set the contentPane for view injections
        SceneManager.setContentPane(mainContent_admin);
        // Load default view (e.g., dashboard)
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/organizer-dashboard-view.fxml");
    }


    // Navigation methods to switch views within the admin layout
    @FXML
    private void showDashboard() {
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/testOrg.fxml");
    }

    @FXML
    private void showUsers() {
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/testOrg.fxml");
    }
}


