package org.example.eventmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.eventmanagement.Controller.Auth.LoginController;
import org.example.eventmanagement.utils.sceneManager;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        sceneManager.setStage(stage);
        sceneManager.switchScene("/View/Auth/login-view.fxml", new LoginController());
    }

    public static void main(String[] args) {
        launch();
    }
}