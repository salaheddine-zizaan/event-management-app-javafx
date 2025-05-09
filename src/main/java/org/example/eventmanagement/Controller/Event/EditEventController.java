package org.example.eventmanagement.Controller.Event;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.eventmanagement.Model.Event;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditEventController {

    @FXML private TextField locationField, cityField, countryField, capacityField, priceField;
    @FXML private DatePicker datePicker;
    @FXML private ListView<Event> eventListView; // ListView to display events
    @FXML private TextField locationTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField countryTextField;
    @FXML private DatePicker eventDatePicker;
    @FXML private Spinner<Integer> capacitySpinner;
    @FXML private TextField priceTextField;
    @FXML private TextField startTimeTextField;
    @FXML private TextField endTimeTextField;
    @FXML private CheckBox approvedCheckBox;

    private Event event;

    private ObservableList<Event> eventList = FXCollections.observableArrayList(); // List for events

    private Event eventToEdit;

    // Method to initialize the form with the selected event data
    public void initFormWithEvent(Event event) {
        this.eventToEdit = event;

        // Fill the form fields with data from the event
        locationField.setText(event.getLocation());
        cityField.setText(event.getCity());
        countryField.setText(event.getCountry());
        datePicker.setValue(event.getDate());
        capacityField.setText(String.valueOf(event.getCapacity()));
        priceField.setText(String.valueOf(event.getPrice()));
    }

    public void setEvent(Event event) {
        this.event = event;

        // Initialisation des champs avec les valeurs de l'événement

        locationTextField.setText(event.getLocation());
        cityTextField.setText(event.getCity());
        countryTextField.setText(event.getCountry());

        if (event.getDate() != null)
            eventDatePicker.setValue(event.getDate());

        if (event.getCapacity() != null)
            capacitySpinner.getValueFactory().setValue(event.getCapacity());

        if (event.getPrice() != null)
            priceTextField.setText(event.getPrice().toString());

        startTimeTextField.setText(event.getStartTime().toString());
        endTimeTextField.setText(event.getEndTime().toString());

        approvedCheckBox.setSelected(event.isApproved());
    }
    // Method to handle the "Save Changes" button action
    @FXML
    public void handleSaveChanges(ActionEvent event) {
        try {
            // Collect the data from the form fields
            String location = locationField.getText();
            String city = cityField.getText();
            String country = countryField.getText();
            double price = Double.parseDouble(priceField.getText());
            int capacity = Integer.parseInt(capacityField.getText());
            java.time.LocalDate date = datePicker.getValue();

            // Update the event in the database
            String sql = "UPDATE event SET location = ?, city = ?, country = ?, date = ?, capacity = ?, price = ? WHERE id_event = ?";
            try (Connection conn = DatabaseConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, location);
                ps.setString(2, city);
                ps.setString(3, country);
                ps.setDate(4, java.sql.Date.valueOf(date));
                ps.setInt(5, capacity);
                ps.setDouble(6, price);
                ps.setInt(7, eventToEdit.getId());

                ps.executeUpdate();

                // Show success message and close the window
                showAlert("Event Updated", "Event details have been updated successfully.");
                ((Stage) locationField.getScene().getWindow()).close();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Database Error", "Error updating the event in the database.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please ensure all fields are filled correctly.");
        }
    }

    // Method to handle the "Cancel" button action
    @FXML
    public void handleCancel(ActionEvent event) {
        // Simply close the window without saving
        ((Stage) locationField.getScene().getWindow()).close();
    }


    // Utility method to show an alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
