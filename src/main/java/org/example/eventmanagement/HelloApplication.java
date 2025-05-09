package org.example.eventmanagement;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.eventmanagement.utils.SceneManager;



public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        SceneManager.setStage(stage);
        SceneManager.switchScene("/org/example/eventmanagement/View/hello-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
