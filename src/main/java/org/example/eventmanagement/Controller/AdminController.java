package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
        SceneManager.switchView("/org/example/eventmanagement/View/admin/test.fxml");
    }

    // Navigation methods to switch views within the admin layout
    @FXML
    private void showDashboard() {
        SceneManager.switchView("/org/example/eventmanagement/View/admin/test.fxml");
    }

    @FXML
    private void showUsers() {
        SceneManager.switchView("/org/example/eventmanagement/View/admin/test.fxml");
    }
}
