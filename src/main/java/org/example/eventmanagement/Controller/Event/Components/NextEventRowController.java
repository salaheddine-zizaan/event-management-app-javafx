package org.example.eventmanagement.Controller.Event.Components;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.eventmanagement.Model.Event;

public class NextEventRowController {

        @FXML private Label eventNameLabel;
        @FXML private Label cityLabel;
        @FXML private Label dateLabel;
        @FXML private Label statusLabel;
        @FXML private Button detailsButton;
        @FXML private Button editButton;
        @FXML private Button deleteButton;

        public void setEventData(Event event) {
            eventNameLabel.setText(event.getTitle());
            cityLabel.setText(event.getCity());
            dateLabel.setText(event.getDate().toString());
            statusLabel.setText("accepted"); // Or dynamically from DB

            // Set up action handlers
            detailsButton.setOnAction(e -> System.out.println("Show details of: " + event.getId()));
            editButton.setOnAction(e -> System.out.println("Edit event: " + event.getId()));
            deleteButton.setOnAction(e -> System.out.println("Delete event: " + event.getId()));
        }

}
