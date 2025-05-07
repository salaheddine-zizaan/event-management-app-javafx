package org.example.eventmanagement.Controller.Auth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;


public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Text statusLabel;


    public void handleLogin(){
        String email = emailField.getText();
        String password = passwordField.getText();

        String DB_URL = "jdbc:postgresql://localhost:5432/event_management";
        String DB_USER = "postgres";
        String DB_PASSWORD = "password";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM person WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                String role = rs.getString("role");

                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Successful login
                    statusLabel.setText("Login successful as " + role);
                } else {
                    statusLabel.setText("Incorrect password");
                }
            } else {
                statusLabel.setText("User not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Database error");
        }
    }

    private void loadDashboardByRole(String role) {
        String fxml = switch (role) {
            case "admin" -> "admin_dashboard.fxml";
            case "organizer" -> "organizer_dashboard.fxml";
            case "user" -> "user_dashboard.fxml";
            default -> null;
        };
        if (fxml != null) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource(fxml));
                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
