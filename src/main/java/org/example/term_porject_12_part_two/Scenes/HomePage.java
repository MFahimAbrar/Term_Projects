package org.example.term_porject_12_part_two.Scenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePage extends Application{

    Stage stage;
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/HomePageFXML.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        HomePageController homePageController = fxmlLoader.getController();
        homePageController.setStage(stage); // Pass the primary stage to the controller

        stage.setTitle("Hello User!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
