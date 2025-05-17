package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.example.eventmanagement.utils.SceneManager;

import java.io.IOException;

public class AdminController {

    @FXML private AnchorPane mainContent_admin; // Reference to the pane in the FXML file

    @FXML
    public void initialize() {
        // Set the contentPane for view injections
        SceneManager.setContentPane(mainContent_admin);
        // Load default view (e.g., dashboard)
        SceneManager.switchView("/org/example/eventmanagement/View/admin/event_liste.fxml");
    }

    // Navigation methods to switch views within the admin layout

    @FXML
    private void toDashboard(MouseEvent event) {
        SceneManager.switchView("/org/example/eventmanagement/View/admin/admin-dashboard-view.fxml");
    }

    @FXML
    private void toOrganizer(MouseEvent event) {
        SceneManager.switchView("/org/example/eventmanagement/View/admin/admin_organizers.fxml");
    }

    @FXML
    private void toEvents(MouseEvent event) {
        SceneManager.switchView("/org/example/eventmanagement/View/admin/event_liste.fxml");
    }
    public void to_profile(MouseEvent mouseEvent) {
        SceneManager.switchView("/org/example/eventmanagement/View/admin/admin_profile.fxml");
    }
}
