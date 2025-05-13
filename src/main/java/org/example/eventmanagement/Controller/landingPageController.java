package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.eventmanagement.utils.SceneManager;

public class landingPageController {

    @FXML
    private AnchorPane mainContent_admin; // Reference to the pane in the FXML file

    @FXML
    public void initialize() {
        // Set the contentPane for view injections
        SceneManager.setContentPane(mainContent_admin);
        // Load default view (e.g., dashboard)
        SceneManager.switchView("/org/example/eventmanagement/View/home/landing_content.fxml");
    }


    public void toLogin(MouseEvent mouseEvent) {
        SceneManager.switchScene("/org/example/eventmanagement/View/Auth/login-view.fxml");
    }
}
