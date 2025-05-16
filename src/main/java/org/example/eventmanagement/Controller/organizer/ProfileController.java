package org.example.eventmanagement.Controller.organizer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.eventmanagement.utils.DatabaseConnection;
import org.example.eventmanagement.utils.SceneManager;
import java.sql.*;

import org.example.eventmanagement.utils.Session;
import org.mindrot.jbcrypt.BCrypt;


public class ProfileController {

    // FXML fields mapped to UI elements
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField companyNameField;
    @FXML private TextField roleField;

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
            String query = "SELECT p.firstname, p.lastname, p.email, p.phone, p.username, p.role, o.name, o.field\n" +
                    "            FROM person p\n" +
                    "            JOIN organizer o ON o.id_person = o.id_person\n" +
                    "            WHERE p.id_person = ?;";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId); // Use actual user ID
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                firstNameField.setText(rs.getString("firstname"));
                lastNameField.setText(rs.getString("lastname"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone"));
                usernameField.setText(rs.getString("username"));
                roleField.setText(rs.getString("role"));
                companyNameField.setText(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading profile.");
        }
    }

    // Handle saving the updated profile information
    @FXML
    public void handleSave() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText(); // Hash password if changing it
        String companyName = companyNameField.getText();
        String role = roleField.getText(); // Role is typically not editable

        // Validate fields
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || username.isEmpty()) {
            statusLabel.setText("All fields must be filled.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String updateQuery = "UPDATE person SET first_name = ?, last_name = ?, email = ?, phone = ?, username = ?, password = ?, company_name = ?, role = ? WHERE id_person = ?";
            PreparedStatement ps = conn.prepareStatement(updateQuery);

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, username);

            // If password is changed, hash it
            if (!password.isEmpty()) {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                ps.setString(6, hashedPassword);
            } else {
                // Use current password if not changed
                ps.setNull(6, Types.VARCHAR);
            }

            ps.setString(7, companyName);
            ps.setString(8, role); // Role should be from the database, so it's likely not editable
            ps.setInt(9, userId); // Update based on the user's ID

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                statusLabel.setText("Profile updated successfully.");
            } else {
                statusLabel.setText("Failed to update profile.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error updating profile.");
        }
    }

    // Set user ID (this could be done after login, for example)
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    public void toHomePage() {
        // Navigate back to the home page or the previous screen
        SceneManager.switchScene("/org/example/eventmanagement/View/organizer/home-view.fxml");
    }
}
