package org.example.eventmanagement.Controller.Auth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import org.example.eventmanagement.Controller.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import javafx.event.ActionEvent;


import org.example.eventmanagement.Controller.OrganizerController;


public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Text statusLabel;


    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM person WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                String role = rs.getString("role");

                if (BCrypt.checkpw(password, hashedPassword)) {
                    statusLabel.setText("Login successful as " + role);

                    // Redirection vers dashboard
                    Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/org/example/eventmanagement/View/organizer/organizer-dashboard-view.fxml"));
                    Scene dashboardScene = new Scene(dashboardRoot);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(dashboardScene);
                    window.show();

                } else {
                    statusLabel.setText("Incorrect password");
                }
            } else {
                statusLabel.setText("User not found");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            statusLabel.setText("Login failed: " + e.getMessage());
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
