package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.example.eventmanagement.DAO.EventDAO;
import org.example.eventmanagement.Model.Event;

import java.io.IOException;
import java.util.List;

public class HomeController {

    @FXML
    private VBox eventListContainer;

    public void initialize(){
        loadEvents();
    }

    public void loadEvents() {
        List<Event> events = EventDAO.getAllEvents(); // Your DAO method

        for (Event event : events) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventmanagement/View/home/event_card.fxml"));
                AnchorPane card = loader.load();

                EventCardController controller = loader.getController();
                controller.setEventData(
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate() != null ? event.getDate().toString() : "",
                        event.getLocation(),
                        event.getCity(),
                        event.getCountry(),
                        event.getStartTime() != null ? event.getStartTime().toString() : "",
                        event.getEndTime() != null ? event.getEndTime().toString() : "",
                        event.getPrice() != null ? String.valueOf(event.getPrice()) : "Free"
                );


                eventListContainer.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
