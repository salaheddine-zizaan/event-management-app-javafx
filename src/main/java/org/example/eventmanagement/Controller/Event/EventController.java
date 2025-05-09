package org.example.eventmanagement.Controller.Event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.eventmanagement.Model.Event;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalTime;

public class EventController {

    @FXML private ListView<Event> eventListView;
    @FXML private TextField locationField, cityField, countryField, priceField, capacityField;
    @FXML private DatePicker datePicker;
    @FXML private TextField startTimeField, endTimeField;

    private final String URL = "jdbc:postgresql://localhost:5432/event_management";
    private final String USER = "postgres";
    private final String PASSWORD = "password";

    private ObservableList<Event> eventList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadEvents();
        eventListView.setCellFactory(param -> new ListCell<Event>() {
            @Override
            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Customize event display in ListView
                    setText("Event: " + item.getLocation() + " (" + item.getCity() + ", " + item.getCountry() + ") - " +
                            item.getDate() + " from " + item.getStartTime() + " to " + item.getEndTime());
                }
            }
        });
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private void loadEvents() {
        eventList.clear();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM event")) {

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
                eventList.add(e);
            }

            if (eventListView != null) {
                eventListView.setItems(eventList);
            } else {
                System.err.println("eventListView is null.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    @FXML
    private void handleUpdateEvent() {
        Event selected = eventListView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Aucun événement sélectionné", "Veuillez sélectionner un événement à modifier.");
            return;
        }

        try (Connection conn = getConnection()) {
            String sql = "UPDATE event SET location = ?, city = ?, country = ?, price = ?, capacity = ?, date = ?, start_time = ?, end_time = ? WHERE id_event = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, locationField.getText());
            ps.setString(2, cityField.getText());
            ps.setString(3, countryField.getText());
            ps.setDouble(4, Double.parseDouble(priceField.getText()));
            ps.setInt(5, Integer.parseInt(capacityField.getText()));
            ps.setDate(6, Date.valueOf(datePicker.getValue()));
            ps.setTime(7, Time.valueOf(LocalTime.parse(startTimeField.getText())));
            ps.setTime(8, Time.valueOf(LocalTime.parse(endTimeField.getText())));
            ps.setInt(9, selected.getId());

            ps.executeUpdate();
            loadEvents();
            showAlert("Succès", "Événement mis à jour avec succès !");
        } catch (Exception e) {
            showAlert("Erreur lors de la mise à jour", e.getMessage());
        }
    }

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
            loadEvents();
            showAlert("Suppression réussie", "Événement supprimé !");
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage());
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
