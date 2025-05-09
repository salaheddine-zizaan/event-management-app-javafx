package org.example.eventmanagement.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.example.eventmanagement.Controller.Event.EditEventController;
import org.example.eventmanagement.Model.Event;
import org.example.eventmanagement.Model.Organizer;
import org.example.eventmanagement.utils.Session;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.example.eventmanagement.utils.DatabaseConnection.getConnection;

public class OrganizerController {

    @FXML private TextField FirstnameField;
    @FXML private TextField LastnameField;
    @FXML private TextField EmailField;
    @FXML private TextField CompanyField;
    @FXML private TextField PhoneField;
    @FXML private TextField UsernameField;
    @FXML private TextField FieldField;
    @FXML private ListView<Event> eventListView; // ListView to display events
    @FXML private TextField locationField, cityField, countryField, priceField, capacityField;
    @FXML private DatePicker datePicker;
    @FXML private TextField startTimeField, endTimeField;

    private ObservableList<Event> eventList = FXCollections.observableArrayList(); // List for events
    private String organizerEmail;

    // Constructor to handle session and get logged-in organizer
    Organizer organizer = Session.getInstance().getLoggedInOrganizer();

    // Initialize method for setting up the event list view
    @FXML
    public void initialize() {
        if (eventListView != null) {
            loadEvents(); // Load events when the view is initialized
        } else {
            System.err.println("eventListView is null.");
        }
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

    // Method to load events from the database and populate the ListView
    private void loadEvents() {
        eventList.clear(); // Clear existing list before loading new events
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM event")) {

            // Loop through the result set and add events to the observable list
            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id_event"));
                e.setLocation(rs.getString("location"));
                e.setCity(rs.getString("city"));
                e.setCountry(rs.getString("country"));
                e.setPrice(rs.getDouble("price"));
                e.setCapacity(rs.getInt("capacity"));
                e.setDate(rs.getDate("date").toLocalDate());
                e.setStartTime(rs.getTime("start_time").toLocalTime());
                e.setEndTime(rs.getTime("end_time").toLocalTime());
                eventList.add(e); // Add each event to the list
            }

            // Bind the ListView with the event list
            if (eventListView != null) {
                eventListView.setItems(eventList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur de chargement", "Erreur lors du chargement des événements.");
        }
    }

    // Method to handle event creation (opens EventForm window)
    @FXML
    private void handleCreateEvent(ActionEvent event) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO event (location, city, country, price, capacity, date, start_time, end_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, locationField.getText());
            ps.setString(2, cityField.getText());
            ps.setString(3, countryField.getText());
            ps.setBigDecimal(4, new BigDecimal(priceField.getText()));
            ps.setInt(5, Integer.parseInt(capacityField.getText()));
            ps.setDate(6, Date.valueOf(datePicker.getValue()));
            ps.setTime(7, Time.valueOf(LocalTime.parse(startTimeField.getText())));
            ps.setTime(8, Time.valueOf(LocalTime.parse(endTimeField.getText())));

            ps.executeUpdate();

            // Clear fields after creation
            locationField.clear();
            cityField.clear();
            countryField.clear();
            priceField.clear();
            capacityField.clear();
            startTimeField.clear();
            endTimeField.clear();
            datePicker.setValue(null);

            loadEvents();  // Reload events to refresh the ListView
            showAlert("Succès", "Événement créé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de créer l'événement : " + e.getMessage());
        }
    }


    private Event selectedEvent;

    @FXML
    public void handleUpdateEvent() {
        selectedEvent = eventListView.getSelectionModel().getSelectedItem();
        if (selectedEvent == null) {
            showAlert("Aucun événement sélectionné", "Veuillez sélectionner un événement à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/eventmanagement/View/Event/edit_event_form.fxml"));
            Parent root = loader.load();

            EditEventController controller = loader.getController();
            controller.setEvent(selectedEvent); // ✔️ Passer l'objet Event directement

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier l'événement");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            loadEvents(); // Recharge la liste après modification

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible d'ouvrir la fenêtre de modification.");
        }
    }





    // Method to handle deleting an event
    @FXML
    private void handleDeleteEvent() {
        Event selected = eventListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Aucun événement sélectionné", "Veuillez sélectionner un événement à supprimer.");
            return;
        }

        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM event WHERE id_event = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, selected.getId());
            ps.executeUpdate();
            loadEvents(); // Reload event list after deletion
            showAlert("Suppression réussie", "Événement supprimé avec succès !");
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage());
        }
    }

    // Utility method to show an alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Setter for organizer's email (used to identify the logged-in organizer)
    public void setOrganizerEmail(String email) {
        this.organizerEmail = email;
        System.out.println("Organisateur connecté : " + organizerEmail);
    }
}
