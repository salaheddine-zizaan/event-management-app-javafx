package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.example.eventmanagement.utils.SceneManager;

public class UserController {

    @FXML
    private AnchorPane mainContent_admin;


    public void initialize(){

        // Set the contentPane for view injections
        SceneManager.setContentPane(mainContent_admin);
        // Load default view (e.g., dashboard)
        SceneManager.switchView("/org/example/eventmanagement/View/user/user-dashboard-view.fxml");

    }


}
