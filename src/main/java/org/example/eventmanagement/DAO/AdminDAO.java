package org.example.eventmanagement.DAO;

import org.example.eventmanagement.Model.Admin;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public static Admin getByIdPerson(int id_person) {
        Admin admin = null;
        String query = """
                SELECT a.id_admin, p.*
                FROM admin a
                JOIN person p ON a.id_person = p.id_person
                WHERE a.id_person = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id_person);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                admin = new Admin(
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admin;
    }
}
