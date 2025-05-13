package org.example.eventmanagement.Controller.Auth;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.example.eventmanagement.utils.DatabaseConnection;
import org.example.eventmanagement.utils.SceneManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

import static org.example.eventmanagement.DAO.OrganizerDAO.insertOrganizer;
import static org.example.eventmanagement.DAO.UserDAO.insertUser;

public class RegisterController {

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField userNameField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField companynamefield;
    @FXML private TextField companyfieldfield;
    @FXML private Label statusLabel;
    @FXML private Text register_success;
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
            register_failed.setText("Please fill in all fields.");
            register_failed_pane.setVisible(true);
            register_failed.setVisible(true);
            return;
        }

        if (!password.equals(confirmPassword)) {
            statusLabel.setText("Passwords do not match.");
            register_failed.setText("Passwords do not match.");
            register_failed_pane.setVisible(true);
            register_failed.setVisible(true);
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

                if (selectedRole.equals("user")) {
                    insertUser("address", personId);
                } else if (selectedRole.equals("organizer")) {
                    insertOrganizer(personId, "company name", "company field");
                }

                register_success.setText("Registration successful!");
                register_success_pane.setVisible(true);
                register_success.setVisible(true);

                statusLabel.setText("Registration successful!");
                // Wait and switch to login screen
                Thread.sleep(4000);
                toLogin();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Registration failed. Email may already exist.");
            register_failed.setText("Registration failed. Email may already exist.");
            register_failed_pane.setVisible(true);
            register_failed.setVisible(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void toLogin() {
        SceneManager.switchScene("/org/example/eventmanagement/View/Auth/login-view.fxml");
    }

    public TextField getCompanynamefield() {
        return companynamefield;
    }

    public void setCompanynamefield(TextField companynamefield) {
        this.companynamefield = companynamefield;
    }

    public TextField getCompanyfieldfield() {
        return companyfieldfield;
    }

    public void setCompanyfieldfield(TextField companyfieldfield) {
        this.companyfieldfield = companyfieldfield;
    }
}
