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

public class SetPasswordController {

    private SocketWrapper socketWrapper;
    private String teamName = null;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setSocketWrapper(SocketWrapper socketWrapper) {this.socketWrapper = socketWrapper;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    @FXML
    private Button BackButton;

    @FXML
    private PasswordField PasswordInput;

    @FXML
    private PasswordField PasswordInput1;

    @FXML
    private Button SUbmitButton;

    @FXML
    void onSubmitClick(ActionEvent event) throws IOException {
        if(PasswordInput.getText().equals(PasswordInput1.getText()) == false){
            Platform.runLater(() -> {AlertUtil.showErrorAlert("Inconsistent Input", "Passwords do not match", "please try again");});

        }
        else {
            try {
                socketWrapper.write(PasswordInput.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

                stage.setScene(scene); // Set the new scene
                stage.setTitle("Home Page");
                stage.show();
            });
        }
    }

    @FXML
    void onBackClick(ActionEvent event) throws IOException {
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
