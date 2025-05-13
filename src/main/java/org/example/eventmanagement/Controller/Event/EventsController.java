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
    public void initialize() {
        loadEvents();
        eventListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Event event, boolean empty) {
                super.updateItem(event, empty);
                if (empty || event == null) {
                    setText(null);
                } else {
                    setText("📍 " + event.getLocation() + ", " + event.getCity() + ", " + event.getCountry()
                            + "\n📅 " + event.getDate() + " 🕒 " + event.getStartTime() + " - " + event.getEndTime()
                            + "\n💰 " + event.getPrice() + " € | 👥 " + event.getCapacity() + " places");
                }
            }
        });
    }

    private void loadEvents() {
        eventList.clear();
        eventList.addAll(eventDAO.getAllEvents());
        eventListView.setItems(eventList);
    }

    @FXML
    private void handleCreateEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventmanagement/View/Event/EventForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Créer un événement");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadEvents();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir la fenêtre de création d'événement.");
        }
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
