package org.example.eventmanagement.Controller.Event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.eventmanagement.DAO.EventDAO;
import org.example.eventmanagement.Model.Event;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class EventsController {

    @FXML
    private ListView<Event> eventListView;
    private ObservableList<Event> eventList = FXCollections.observableArrayList();
    private EventDAO eventDAO = new EventDAO();
    @FXML
    private TextField cityField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField locationField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField startTimeField;
    @FXML
    private TextField endTimeField;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField priceField;


    @FXML
    public void initialize() {


    }

    private void loadEvents() {
        eventList.clear();
        eventList.addAll(eventDAO.getAllEvents());
        eventListView.setItems(eventList);
    }

    @FXML
    private void handleCreateEvent() {
        // Extract form field values
        String city = cityField.getText();
        String country = countryField.getText();
        String location = locationField.getText();
        LocalDate date = datePicker.getValue();
        String startTime = startTimeField.getText();
        String endTime = endTimeField.getText();
        int capacity = Integer.parseInt(capacityField.getText());  // Assuming user enters a valid integer
        double price = Double.parseDouble(priceField.getText());  // Assuming user enters a valid double

        // Create a new Event object and set the extracted data
        Event event = new Event();
        event.setCity(city);
        event.setCountry(country);
        event.setLocation(location);
        event.setDate(date);
        event.setStartTime(LocalTime.parse(startTime));
        event.setEndTime(LocalTime.parse(endTime));
        event.setCapacity(capacity);
        event.setPrice(price);

        // Insert the event into the database
        EventDAO.insertEvent(event);

        // Optionally, you could reset the form fields after creating the event
        cityField.clear();
        countryField.clear();
        locationField.clear();
        datePicker.setValue(null);
        startTimeField.clear();
        endTimeField.clear();
        capacityField.clear();
        priceField.clear();
    }


    @FXML
    private void handleUpdateEvent() {
        Event selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("Aucun événement sélectionné", "Veuillez sélectionner un événement à mettre à jour.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventmanagement/View/Event/edit_event_form.fxml"));
            Parent root = loader.load();
            EditEventController controller = loader.getController();
            controller.setEvent(selectedEvent);
            Stage stage = new Stage();
            stage.setTitle("Modifier l'événement");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadEvents();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir la fenêtre de modification d'événement.");
        }
    }

    @FXML
    private void handleDeleteEvent() {
        Event selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("Aucun événement sélectionné", "Veuillez sélectionner un événement à supprimer.");
            return;
        }
        boolean success = eventDAO.deleteEvent(selectedEvent.getId());
        if (success) {
            showAlert("Suppression réussie", "Événement supprimé avec succès !");
            loadEvents();
        } else {
            showAlert("Erreur", "Échec de la suppression de l'événement.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
