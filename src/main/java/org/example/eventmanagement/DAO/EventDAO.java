package org.example.eventmanagement.DAO;

import org.example.eventmanagement.Model.Event;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM event";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event event = new Event(
                        rs.getInt("id_event"),
                        rs.getObject("id_organizer", Integer.class),
                        rs.getObject("id_admin", Integer.class),
                        rs.getBoolean("approved"),
                        rs.getObject("capacity", Integer.class),
                        rs.getBigDecimal("price").doubleValue(),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("location"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getTime("start_time").toLocalTime(),
                        rs.getTime("end_time").toLocalTime(),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("title"),
                        rs.getString("description")
                );
                events.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    // You can also add insert/update/delete methods
}
