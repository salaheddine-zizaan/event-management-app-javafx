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

    @FXML
    private ListView<Event> eventListView;
    @FXML
    private TextField locationField, cityField, countryField, priceField, capacityField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField startTimeField, endTimeField;

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


}
