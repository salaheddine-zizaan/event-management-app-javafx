package org.example.eventmanagement.DAO;

import org.example.eventmanagement.Model.Admin;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    public static Admin getByIdPerson(int idPerson) {
        String query = """
            SELECT a.id_admin, p.*
            FROM admin a
            JOIN person p ON a.id_person = p.id_person
            WHERE a.id_person = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idPerson);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractAdmin(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Admin getById(int idAdmin) {
        String query = """
            SELECT a.id_admin, p.*
            FROM admin a
            JOIN person p ON a.id_person = p.id_person
            WHERE a.id_admin = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idAdmin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractAdmin(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean insertAdmin(int personId) {
        String sql = "INSERT INTO admin (id_person) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateAdmin(int idAdmin, int newPersonId) {
        String sql = "UPDATE admin SET id_person = ? WHERE id_admin = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newPersonId);
            ps.setInt(2, idAdmin);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAdmin(int idAdmin) {
        String sql = "DELETE FROM admin WHERE id_admin = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idAdmin);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        String query = """
            SELECT a.id_admin, p.*
            FROM admin a
            JOIN person p ON a.id_person = p.id_person
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                admins.add(extractAdmin(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admins;
    }

    private static Admin extractAdmin(ResultSet rs) throws SQLException {
        return new Admin(
                rs.getInt("id_admin"),
                rs.getInt("id_person"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("password"),
                rs.getString("role"),
                rs.getString("username")
        );
    }
}
