package org.example.eventmanagement.Controller.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.eventmanagement.Controller.DatabaseConnection;
import org.example.eventmanagement.utils.SceneManager;
import org.example.eventmanagement.utils.SceneManager;
import org.mindrot.jbcrypt.BCrypt;
import org.example.eventmanagement.DAO.UserDAO;

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
    @FXML private ComboBox<String> roleComboBox;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label statusLabel;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll( "organizer", "user", "admin");
    }



    @FXML
    public void handleRegister() {
        String firstname = firstNameField.getText();
        String lastname = lastNameField.getText();
        String email = emailField.getText();
        String username = userNameField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        if (firstname.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            statusLabel.setText("All fields are required.");
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());



        try (Connection conn = DatabaseConnection.getConnection()) {
            // Insert into person
            String insertPerson = "INSERT INTO person (firstname, lastname, email, phone, password, username, role) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id_person";
            PreparedStatement ps = conn.prepareStatement(insertPerson);
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, hashedPassword);
            ps.setString(6, username);
            ps.setString(7, role);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int personId = rs.getInt("id_person");

                switch (role) {
                    case "user" -> insertUser("address",personId);

                    case "organizer" -> insertOrganizer(personId,"company name", "company field");
                }
                statusLabel.setText("Registration successful!");
                SceneManager.switchScene("/org/example/eventmanagement/View/auth/login-view.fxml");

            }

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Registration failed. Email may already exist.");
        }

    }

}
