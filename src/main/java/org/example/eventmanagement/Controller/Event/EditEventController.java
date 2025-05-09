package org.example.eventmanagement.Controller.Event;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.eventmanagement.Model.Event;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.LocalTime;



public class EditEventController {

    @FXML private TextField locationField;
    @FXML private TextField cityField;
    @FXML private TextField countryField;
    @FXML private DatePicker datePicker;
    @FXML private TextField priceField;
    @FXML private TextField capacityField;
    @FXML private TextField startTimeField;
    @FXML private TextField endTimeField;
    @FXML private CheckBox approvedCheckBox;

    private int eventId; // ID of the event to update

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    private Event event;

    public void setEvent(Event event) {
        this.event = event;
        this.eventId = event.getId();

        // Préremplir les champs avec les données de l’événement
        locationField.setText(event.getLocation());
        cityField.setText(event.getCity());
        countryField.setText(event.getCountry());
        datePicker.setValue(event.getDate());
        priceField.setText(String.valueOf(event.getPrice()));
        capacityField.setText(String.valueOf(event.getCapacity()));
        startTimeField.setText(event.getStartTime().toString());
        endTimeField.setText(event.getEndTime().toString());
        approvedCheckBox.setSelected(event.isApproved());
    }


    @FXML
    public void handleUpdateEvent(ActionEvent actionEvent) {
        String location = locationField.getText().trim();
        String city = cityField.getText().trim();
        String country = countryField.getText().trim();
        LocalDate date = datePicker.getValue();
        String priceText = priceField.getText().trim();
        String capacityText = capacityField.getText().trim();
        String startTimeText = startTimeField.getText().trim();
        String endTimeText = endTimeField.getText().trim();
        boolean approved = approvedCheckBox.isSelected();

        if (location.isEmpty() || city.isEmpty() || country.isEmpty() || date == null
                || priceText.isEmpty() || capacityText.isEmpty()
                || startTimeText.isEmpty() || endTimeText.isEmpty()) {
            showAlert("Champs manquants", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int capacity = Integer.parseInt(capacityText);

            // Vérification des formats d'heure
            LocalTime startTime = LocalTime.parse(startTimeText);
            LocalTime endTime = LocalTime.parse(endTimeText);

            if (eventId <= 0) {
                showAlert("Erreur", "ID de l'événement invalide.");
                return;
            }

            String sql = "UPDATE event SET location = ?, city = ?, country = ?, date = ?, price = ?, capacity = ?, start_time = ?, end_time = ?, approved = ? WHERE id_event = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, location);
                ps.setString(2, city);
                ps.setString(3, country);
                ps.setDate(4, java.sql.Date.valueOf(date));
                ps.setDouble(5, price);
                ps.setInt(6, capacity);
                ps.setTime(7, java.sql.Time.valueOf(startTime));
                ps.setTime(8, java.sql.Time.valueOf(endTime));
                ps.setBoolean(9, approved);
                ps.setInt(10, eventId);

                int updated = ps.executeUpdate();
                if (updated > 0) {
                    showAlert("Succès", "Événement mis à jour avec succès.");
                    closeWindow();
                } else {
                    showAlert("Erreur", "Aucune mise à jour effectuée. Vérifiez l'ID de l'événement.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Erreur SQL", "Erreur lors de la mise à jour : " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            showAlert("Erreur de format", "Veuillez entrer un nombre valide pour le prix et la capacité.");
        } catch (DateTimeParseException e) {
            showAlert("Erreur de format d'heure", "Veuillez entrer l'heure au format HH:mm:ss.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) locationField.getScene().getWindow();
        stage.close();
    }
}
