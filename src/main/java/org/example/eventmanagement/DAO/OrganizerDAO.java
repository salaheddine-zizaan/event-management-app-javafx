package org.example.eventmanagement.DAO;

import org.example.eventmanagement.Model.Organizer;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizerDAO {

    public static Organizer getByIdPerson(int idPerson) throws SQLException {
        Organizer organizer = null;
        String sql = "SELECT o.id_organizer, o.name AS organizer_name, o.field, " +
                "p.id_person, p.firstname, p.lastname, p.email, p.phone, " +
                "p.password, p.role, p.username " +
                "FROM organizer o " +
                "JOIN person p ON o.id_person = p.id_person " +
                "WHERE o.id_person = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPerson);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                organizer = new Organizer(
                        rs.getInt("id_person"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("username"),
                        rs.getString("organizer_name"),
                        rs.getString("field")
                );
                organizer.setIdOrganizer(rs.getInt("id_organizer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return organizer;
    }

    public static boolean insertOrganizer(int personId, String name, String field) {
        String sql = "INSERT INTO organizer (id_person, name, field) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personId);
            ps.setString(2, "Default Name"); // You can ask for this in your UI
            ps.setString(3, "General");

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
