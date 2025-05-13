package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.eventmanagement.DAO.EventDAO;
import org.example.eventmanagement.Model.Event;
import org.example.eventmanagement.Model.Organizer;
import org.example.eventmanagement.utils.SceneManager;
import org.example.eventmanagement.utils.Session;

import java.awt.event.ActionEvent;
import java.util.List;

public class OrganizerController {

    @FXML
    private AnchorPane mainContent_admin;

    @FXML
    public void initialize() {
        SceneManager.setContentPane(mainContent_admin);
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/organizer-dashboard-view.fxml");

    }

    @FXML
    private void toDashboard(MouseEvent event) {
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/organizer-dashboard-view.fxml");
    }

    @FXML
    private void toCollaborator(MouseEvent event) {
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/organizer_collaborators.fxml");
    }

    @FXML
    private void toEvents(MouseEvent event) {
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/organizer_event_list.fxml");
    }

    public void toProfile(MouseEvent event) {
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/organizer-profile.fxml");
    }
}
