package org.example.eventmanagement.Controller.Auth;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.eventmanagement.Controller.AdminController;
import org.example.eventmanagement.Controller.OrganizerController;
import org.example.eventmanagement.Controller.UserController;
import org.example.eventmanagement.DAO.AdminDAO;
import org.example.eventmanagement.DAO.OrganizerDAO;
import org.example.eventmanagement.DAO.PersonDAO;
import org.example.eventmanagement.DAO.UserDAO;
import org.example.eventmanagement.Model.Admin;
import org.example.eventmanagement.Model.Organizer;
import org.example.eventmanagement.Model.Person;
import org.example.eventmanagement.Model.User;
import org.example.eventmanagement.utils.Session;
import org.example.eventmanagement.utils.SceneManager;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Text statusLabel;

    public void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            Person person = PersonDAO.getByEmail(email);

            if (person == null) {
                statusLabel.setVisible(true);
                statusLabel.setText("User not found.");
                return;
            }

            if (!BCrypt.checkpw(password, person.getPassword())) {
                statusLabel.setVisible(true);
                statusLabel.setText("Incorrect password.");
                return;
            }

            String role = person.getRole();
            Session.getInstance().setLoggedInPerson(person); // Save base person data


            switch (role) {
                case "admin" -> {
                    Admin admin = AdminDAO.getByIdPerson(person.getIdPerson());
                    Session.getInstance().setLoggedInAdmin(admin);
                    SceneManager.switchScene("/org/example/eventmanagement/View/admin/admin-profile-view.fxml");
                }
                case "organizer" -> {
                    Organizer organizer = OrganizerDAO.getByIdPerson(person.getIdPerson());
                    Session.getInstance().setLoggedInOrganizer(organizer);
                    SceneManager.switchScene("/org/example/eventmanagement/View/event/EventForm.fxml");
                }
                case "user" -> {
                    User user = UserDAO.getByIdPerson(person.getIdPerson());
                    Session.getInstance().setLoggedInUser(user);
                    SceneManager.switchScene("/org/example/eventmanagement/View/user/user-profile-view.fxml");
                }
                default -> statusLabel.setText("Unknown role: " + role);
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Login error: " + e.getMessage());
        }
    }

/*    private void loadDashboard(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/eventmanagement/View" + fxml));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Unable to load dashboard.");
        }
    }*/
}
