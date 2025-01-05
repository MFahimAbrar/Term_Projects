package org.example.term_porject_12_part_two.Scenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

public class UserName extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Process ID: " + ProcessHandle.current().pid());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/UserName.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        UserNameController userNameController = fxmlLoader.getController();
        userNameController.setStage(stage); // Pass the primary stage to the controller
        userNameController.setSocketWrapper(new SocketWrapper("127.0.0.1", 44444));

        stage.setTitle("Hello User!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
