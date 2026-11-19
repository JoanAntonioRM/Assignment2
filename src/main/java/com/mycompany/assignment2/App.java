package com.mycompany.assignment2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static CarRaceController controller;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 940, 620);
        stage.setScene(scene);
        stage.setTitle("CarRace Simulation");
        stage.setOnCloseRequest(e -> controller.shutdown());
        stage.show();
        controller.startSlideshow();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setControllerFactory(type -> {
            if (controller == null) {
                controller = new CarRaceController();
            }
            return controller;
        });
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}