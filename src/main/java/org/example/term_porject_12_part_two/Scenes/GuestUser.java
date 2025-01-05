package org.example.term_porject_12_part_two.Scenes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

public class GuestUser extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Process ID: " + ProcessHandle.current().pid());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/GuestUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        GuestUserController guestUserController = fxmlLoader.getController();
        guestUserController.setStage(stage); // Pass the primary stage to the controller
        SocketWrapper socketWrapper = new SocketWrapper("127.0.0.1", 44444);
        socketWrapper.write("GUESTUSER");
        guestUserController.setSocketWrapper(socketWrapper);

        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
