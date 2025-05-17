package org.example.eventmanagement.Controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.eventmanagement.utils.DatabaseConnection;
import org.example.eventmanagement.utils.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminProfileController {
    // FXML fields mapped to UI elements
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label statusLabel;

    private int userId; // Assuming the user's ID will be fetched and stored for later use

    // Method to initialize the controller with user details (for example, from the database)
    public void initialize() {
        // Here, you would typically fetch the current user's details from the database
        // and set them to the corresponding fields, for example:
        userId = Session.getInstance().getLoggedInPerson().getIdPerson();
        loadUserProfile();
    }

    // Fetch user profile from the database and populate the fields
    private void loadUserProfile() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT firstname, lastname, email, phone, username \n" +
                    "            FROM person \n" +
                    "            WHERE id_person = ?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId); // Use actual user ID
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                firstNameField.setText(rs.getString("firstname"));
                lastNameField.setText(rs.getString("lastname"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone"));
                usernameField.setText(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading profile.");
        }
    }
}
