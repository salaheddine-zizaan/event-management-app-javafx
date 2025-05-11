package org.example.eventmanagement.utils;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {
    private static Stage primaryStage;
    private static AnchorPane contentPane;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void setContentPane(AnchorPane pane) {
        contentPane = pane;
    }

    public static void switchScene(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            // Load CSS safely
            String cssPath = "/org/example/eventmanagement/View/css/stylesheet.css";
            URL cssUrl = SceneManager.class.getResource(cssPath);
            if (cssUrl != null) {
                String css = cssUrl.toExternalForm();
                System.out.println("Loading CSS from: " + css);
                scene.getStylesheets().add(css);
            } else {
                System.out.println("Warning: CSS file not found at " + cssPath);
                // Try alternative path
                cssPath = "org/example/eventmanagement/View/css/stylesheet.css";
                cssUrl = SceneManager.class.getClassLoader().getResource(cssPath);
                if (cssUrl != null) {
                    String css = cssUrl.toExternalForm();
                    System.out.println("Loading CSS from alternative path: " + css);
                    scene.getStylesheets().add(css);
                } else {
                    System.out.println("Warning: CSS file not found at alternative path: " + cssPath);
                }
            }
            
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            System.out.println("Error: scene switch");
            e.printStackTrace();
        }
    }

    public static void switchView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxml));
            Parent view = loader.load();

            contentPane.getChildren().clear();
            contentPane.getChildren().add(view);

            // Anchor the view to all edges
            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

/*
            contentPane.getChildren().setAll(view);
*/
        } catch (IOException e) {
            System.out.println("Error: view switch");
            e.printStackTrace();
        }
    }
}
