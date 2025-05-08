package org.example.eventmanagement.DAO;

import org.example.eventmanagement.Model.Person;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.*;

public class PersonDAO {

    public static Person getByEmail(String email) {
        Person person = null;
        String sql = "SELECT * FROM person WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                person = new Person(
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
        return person;
    }

    public boolean insertPerson(Person person) {
        String sql = "INSERT INTO person (firstname, lastname, email, phone, username, password, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getPhone());
            ps.setString(5, person.getUsername());
            ps.setString(6, person.getPassword());  // Should already be hashed
            ps.setString(7, person.getRole());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Person getById(int id) {
        Person person = null;
        String sql = "SELECT * FROM person WHERE id_person = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                person = new Person(
                        rs.getInt("id_person"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    // Optional: add updatePerson, deletePerson, etc.
}
