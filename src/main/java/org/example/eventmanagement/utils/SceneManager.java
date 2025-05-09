package org.example.eventmanagement.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private static Stage primaryStage;


    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchScene(String fxml, Object controller) {
        try{
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxml));
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
