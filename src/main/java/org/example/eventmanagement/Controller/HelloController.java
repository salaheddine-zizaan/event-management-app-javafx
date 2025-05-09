package org.example.eventmanagement.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.example.eventmanagement.Controller.Auth.LoginController;
import org.example.eventmanagement.Controller.Auth.RegisterController;
import org.example.eventmanagement.utils.SceneManager;

import java.io.IOException;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        SceneManager.switchScene("/org/example/eventmanagement/view/auth/login-view.fxml");
    }
    @FXML
    private void goToRegister(ActionEvent event) throws IOException {
        SceneManager.switchScene("/org/example/eventmanagement/view/auth/register-view.fxml");
    }
}