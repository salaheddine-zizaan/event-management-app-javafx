package org.example.eventmanagement.DAO;

import org.example.eventmanagement.Model.Organizer;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizerDAO {

    public static Organizer getByIdPerson(int idPerson) {
        String sql = """
            SELECT o.id_organizer, o.name AS organizer_name, o.field,
                   p.id_person, p.firstname, p.lastname, p.email, p.phone,
                   p.password, p.role, p.username
            FROM organizer o
            JOIN person p ON o.id_person = p.id_person
            WHERE o.id_person = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPerson);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractOrganizer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Organizer getById(int idOrganizer) {
        String sql = """
            SELECT o.id_organizer, o.name AS organizer_name, o.field,
                   p.id_person, p.firstname, p.lastname, p.email, p.phone,
                   p.password, p.role, p.username
            FROM organizer o
            JOIN person p ON o.id_person = p.id_person
            WHERE o.id_organizer = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOrganizer);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractOrganizer(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean insertOrganizer(int personId, String name, String field) {
        String sql = "INSERT INTO organizer (id_person, name, field) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personId);
            ps.setString(2, name);
            ps.setString(3, field);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateOrganizer(int idOrganizer, String name, String field) {
        String sql = "UPDATE organizer SET name = ?, field = ? WHERE id_organizer = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, field);
            ps.setInt(3, idOrganizer);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrganizer(int idOrganizer) {
        String sql = "DELETE FROM organizer WHERE id_organizer = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idOrganizer);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Organizer> getAllOrganizers() {
        List<Organizer> organizers = new ArrayList<>();
        String sql = """
            SELECT o.id_organizer, o.name AS organizer_name, o.field,
                   p.id_person, p.firstname, p.lastname, p.email, p.phone,
                   p.password, p.role, p.username
            FROM organizer o
            JOIN person p ON o.id_person = p.id_person
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                organizers.add(extractOrganizer(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return organizers;
    }

    private static Organizer extractOrganizer(ResultSet rs) throws SQLException {
        Organizer organizer = new Organizer(
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
        return organizer;
    }
}
