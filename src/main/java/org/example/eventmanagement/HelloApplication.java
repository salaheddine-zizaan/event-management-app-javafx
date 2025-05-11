package org.example.eventmanagement;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.eventmanagement.Controller.Auth.LoginController;
import org.example.eventmanagement.utils.SceneManager;
import org.example.eventmanagement.Controller.Auth.LoginController;
import org.example.eventmanagement.utils.SceneManager;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
/*
        SceneManager.switchScene("/org/example/eventmanagement/View/Auth/login-view.fxml");
*/
        SceneManager.switchScene("/org/example/eventmanagement/View/home/landing_page.fxml");
    }

    @Override
    public void stop() {

        // hna bach t cleani session ola chi haja mora ma tsed l app
        System.out.println("Closing application");
    }

    public static void main(String[] args) {
        launch();
    }
}
