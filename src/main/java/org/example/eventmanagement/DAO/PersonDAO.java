package org.example.eventmanagement.DAO;

import org.example.eventmanagement.Model.Person;
import org.example.eventmanagement.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public static Person getByEmail(String email) {
        String sql = "SELECT * FROM person WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractPerson(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Person getById(int id) {
        String sql = "SELECT * FROM person WHERE id_person = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractPerson(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insertPerson(Person person) {
        String sql = "INSERT INTO person (firstname, lastname, email, phone, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getPhone());
            ps.setString(5, person.getUsername());
            ps.setString(6, person.getPassword()); // already hashed
            ps.setString(7, person.getRole());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePerson(Person person) {
        String sql = "UPDATE person SET firstname = ?, lastname = ?, email = ?, phone = ?, username = ?, password = ?, role = ? WHERE id_person = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, person.getFirstName());
            ps.setString(2, person.getLastName());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getPhone());
            ps.setString(5, person.getUsername());
            ps.setString(6, person.getPassword());
            ps.setString(7, person.getRole());
            ps.setInt(8, person.getIdPerson());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deletePerson(int id) {
        String sql = "DELETE FROM person WHERE id_person = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM person";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                persons.add(extractPerson(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persons;
    }

    private static Person extractPerson(ResultSet rs) throws SQLException {
        return new Person(
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
