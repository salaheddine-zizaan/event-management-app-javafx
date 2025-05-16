package org.example.eventmanagement.DAO;

import org.example.eventmanagement.Model.Event;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    // Get all events
    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM event";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                events.add(extractEventFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }


    // Get event by ID
    public Event getEventById(int id) {
        String query = "SELECT * FROM event WHERE id_event = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractEventFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Get event by ID and Organizer ID
    public static List<Event> getEventsByOrganizer(int organizerId) {
        List<Event> events = new ArrayList<>();

        String query = "SELECT * FROM event WHERE id_organizer = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, organizerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                events.add(extractEventFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    // Insert event
    public static boolean insertEvent(Event event) {
        String query = "INSERT INTO event (title, description, location, city, country, price, capacity, date, start_time, end_time, id_organizer) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            setEventParams(ps, event);
            ps.setInt(11, event.getOrganizerId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update event
    public boolean updateEvent(Event event) {
        String query = "UPDATE event SET title=?, description=?, location=?, city=?, country=?, price=?, capacity=?, date=?, start_time=?, end_time=? WHERE id_event=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            setEventParams(ps, event);
            ps.setInt(11, event.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete event
    public boolean deleteEvent(int eventId) {
        String query = "DELETE FROM event WHERE id_event = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, eventId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Extract Event from ResultSet
    private static Event extractEventFromResultSet(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setId(rs.getInt("id_event"));
        event.setTitle(rs.getString("title"));
        event.setDescription(rs.getString("description"));
        event.setLocation(rs.getString("location"));
        event.setCity(rs.getString("city"));
        event.setCountry(rs.getString("country"));
        event.setPrice(rs.getDouble("price"));
        event.setCapacity(rs.getInt("capacity"));
        event.setDate(rs.getDate("date").toLocalDate());
        event.setStartTime(rs.getTime("start_time").toLocalTime());
        event.setEndTime(rs.getTime("end_time").toLocalTime());
        event.setOrganizerId(rs.getInt("id_organizer"));
        event.setApproved(rs.getBoolean("approved"));
        return event;
    }

    // Set Event parameters in PreparedStatement
    private static void setEventParams(PreparedStatement ps, Event event) throws SQLException {
        ps.setString(1, event.getTitle());
        ps.setString(2, event.getDescription());
        ps.setString(3, event.getLocation());
        ps.setString(4, event.getCity());
        ps.setString(5, event.getCountry());
        ps.setDouble(6, event.getPrice());
        ps.setInt(7, event.getCapacity());
        ps.setDate(8, Date.valueOf(event.getDate()));
        ps.setTime(9, Time.valueOf(event.getStartTime()));
        ps.setTime(10, Time.valueOf(event.getEndTime()));
    }
}
