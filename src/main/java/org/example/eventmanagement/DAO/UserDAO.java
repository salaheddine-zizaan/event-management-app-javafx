package org.example.eventmanagement.DAO;

import org.example.eventmanagement.Model.User;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static User getByIdPerson(int id_person) {
        User user = null;
        String query = """
                SELECT u.id_user, u.address, p.*
                FROM user u
                JOIN person p ON u.id_person = p.id_person
                WHERE u.id_person = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id_person);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("id_user"),
                        rs.getInt("id_person"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("username"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean insertUser(String address, int personId) {
        String sql = "INSERT INTO \"user\" (id_person, address) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, personId);
            ps.setString(2, address);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
