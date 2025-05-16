package org.example.eventmanagement.Controller.Auth;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.example.eventmanagement.utils.DatabaseConnection;
import org.example.eventmanagement.utils.SceneManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class RegisterController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField userNameField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private PasswordField companynamefield;
    @FXML private PasswordField companyfieldfield;
    @FXML private Label statusLabel;
    @FXML private Label register_success;
    @FXML private Pane register_success_pane;
    @FXML private Text register_failed;
    @FXML private Pane register_failed_pane;


    @FXML private RadioButton radioorganizer;
    @FXML private RadioButton userorganizer;
    @FXML private ToggleGroup role;

    private String selectedRole = null;

    @FXML
    public void handleRadioButtonAction() {
        RadioButton selected = (RadioButton) role.getSelectedToggle();
        if (selected != null) {
            selectedRole = selected.getText();
        }
    }

    @FXML
    public void handleRegister() {
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();
        String email = emailField.getText();
        String username = userNameField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (firstname.isEmpty() || email.isEmpty() || password.isEmpty() || selectedRole == null) {
            statusLabel.setText("All fields are required.");
            register_failed.visibleProperty().set(true);
            register_failed.setText("Please fill in all fields");
            return;
        }

        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match.");
            register_failed_pane.visibleProperty().set(true);
            register_failed.visibleProperty().set(true);
            register_failed.setText("Passwords do not match.");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String insertPerson = "INSERT INTO person (firstname, lastname, email, phone, password, username, role) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_person";
            PreparedStatement ps = conn.prepareStatement(insertPerson);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, hashedPassword);
            ps.setString(6, username);
            ps.setString(7, selectedRole);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int personId = rs.getInt("id_person");

                switch (selectedRole) {
                    case "organizer" -> registerOrganizer(conn, personId);
                    case "user" -> registerUser(conn, personId);
                }

                register_success.visibleProperty().set(true);
                register_success_pane.visibleProperty().set(true);
                register_success.setText("Registration successful!");
                statusLabel.setText("Registration successful!");
                wait(4);
                toLogin();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Registration failed. Email may already exist.");
            register_failed_pane.visibleProperty().set(true);
            register_failed.visibleProperty().set(true);
            register_failed.setText("Registration failed. Email may already exist.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerOrganizer(Connection conn, int personId) throws SQLException {
        String companyName = companynamefield.getText();
        String companyField = companyfieldfield.getText();

        String sql = "INSERT INTO organizer (id_person, name, field) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, personId);
        ps.setString(2, companyName.isEmpty() ? "N/A" : companyName);
        ps.setString(3, companyField.isEmpty() ? "General" : companyField);
        ps.executeUpdate();
    }

    private void registerUser(Connection conn, int personId) throws SQLException {
        String sql = "INSERT INTO \"user\" (id_person, address) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, personId);
        ps.setString(2, "N/A");
        ps.executeUpdate();
    }

    public void toLogin(){
        SceneManager.switchScene("/org/example/eventmanagement/View/Auth/login-view.fxml");
    }

}
