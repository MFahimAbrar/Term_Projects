package org.example.term_porject_12_part_two.Scenes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;

public class PasswordController {

    private SocketWrapper socketWrapper;
    private String teamName = null;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setSocketWrapper(SocketWrapper socketWrapper) {this.socketWrapper = socketWrapper;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    @FXML
    private PasswordField PasswordInput;

    @FXML
    private Button SUbmitButton;

    @FXML
    private Button BackButton;


    @FXML
    void onSubmitClick(ActionEvent event) throws IOException, ClassNotFoundException {
        socketWrapper.write(PasswordInput.getText());
        String s = (String) socketWrapper.read();
        System.out.println("sss " + s);
        if(s.equals("WRONGWRONG")){
            Platform.runLater(() -> {AlertUtil.showErrorAlert("Wrong Credentials", "The provided password is wrong", "Please try agian");});
        }
        else if(s.equals("CORRECTCORRECT")){
            Platform.runLater(() -> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/HomePageFXML.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Get the controller for ShowUserController
                HomePageController homePageController = fxmlLoader.getController();
                homePageController.setStage(stage); // Pass the stage to the next controller
                homePageController.setSocketWrapper(socketWrapper);
                homePageController.setTeamName(teamName);
                System.out.println(teamName);

                stage.setScene(scene); // Set the new scene
                stage.setTitle("Home Page");

                stage.show();
            });
        }
    }

    @FXML
    void onBackClick(ActionEvent event) {
        try {
            socketWrapper.write("BACKBACK");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(() -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/UserName.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Get the controller for ShowUserController
            UserNameController userNameController = fxmlLoader.getController();
            userNameController.setStage(stage); // Pass the stage to the next controller
            userNameController.setSocketWrapper(socketWrapper);


            stage.setScene(scene); // Set the new scene
            stage.setTitle("User Name");
            stage.show();
        });
    }

}
