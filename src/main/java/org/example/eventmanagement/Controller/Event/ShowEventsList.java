package org.example.eventmanagement.Controller.Event;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private VBox eventsContainer;  // This is now a VBox instead of AnchorPane

    public void initialize() throws IOException {
        Organizer organizer = Session.getInstance().getLoggedInOrganizer(); // Get currently logged-in organizer

        if (organizer != null) {
            // Fetch events for the logged-in organizer
            List<Event> myEvents = EventDAO.getEventsByOrganizer(organizer.getIdOrganizer());
            // Loop through each event and create an event row
            for (Event event : myEvents) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventmanagement/View/Organizer/eventsList/nextEventsRow.fxml"));

                // Load the FXML for each event row
                Parent eventRow = loader.load();

                // Get the controller for the event row
                NextEventRowController controller = loader.getController();

                // Pass the event data to the controller
                controller.setEventData(event);

                // Add the event row to the VBox container
                eventsContainer.getChildren().add(eventRow);
            }
        } else {
            System.out.println("No organizer logged in.");
        }
    }


    public void AddEventLink() {
        SceneManager.switchView("/org/example/eventmanagement/View/organizer/add_event.fxml");
    }
}
