package org.example.eventmanagement.Controller.Event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.example.eventmanagement.Controller.Event.Components.NextEventRowController;
import org.example.eventmanagement.DAO.EventDAO;
import org.example.eventmanagement.DAO.OrganizerDAO;
import org.example.eventmanagement.Model.Event;
import org.example.eventmanagement.Model.Organizer;
import org.example.eventmanagement.utils.SceneManager;
import org.example.eventmanagement.utils.Session;

import java.io.IOException;
import java.util.List;

public class ShowEventsList {

    @FXML
    private AnchorPane eventsContainer;

    public void initialize() throws IOException {
        Organizer organizer = Session.getInstance().getLoggedInOrganizer(); // Get currently logged-in organizer
        if (organizer != null) {
            List<Event> myEvents = EventDAO.getEventsByOrganizer(organizer.getIdOrganizer());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventmanagement/View/Organizer/eventsList/nextEventsRow.fxml"));

            for (Event event : myEvents) {
                Parent eventRow = loader.load();  // Load the FXML for each event

                NextEventRowController controller = loader.getController();
                controller.setEventData(event);  // Pass the event object to the controller

                eventsContainer.getChildren().add(eventRow);  // Add each event row to the container
            }
        } else {
            System.out.println("No organizer logged in.");
        }
    }


    public void AddEventLink(MouseEvent event) {
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/add_event.fxml");
    }
}
