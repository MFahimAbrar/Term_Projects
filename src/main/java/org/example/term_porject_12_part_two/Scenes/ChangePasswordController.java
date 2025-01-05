package org.example.term_porject_12_part_two.Scenes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;

public class ChangePasswordController {

    private Stage stage;

    private SocketWrapper socketWrapper;
    private String teamName = null;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setSocketWrapper(SocketWrapper socketWrapper) {this.socketWrapper = socketWrapper;}
    public void setTeamName(String teamName) {this.teamName = teamName;}

    @FXML
    private Button BackButton;

    @FXML
    private PasswordField NewPass2Input;

    @FXML
    private PasswordField NewPass1Input;

    @FXML
    private Button SubmitButton;

    @FXML
    void onBackClick(ActionEvent event) throws IOException{
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
        homePageController.setTeamName(teamName);
        homePageController.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Home Page");
        stage.show();
    }

    @FXML
    void onSubmitClick(ActionEvent event) throws IOException{
        if(NewPass1Input.getText().equals(NewPass2Input.getText())){
            socketWrapper.write("changepassword");
            socketWrapper.write(teamName+"="+NewPass1Input.getText());
            Platform.runLater(() -> {
                try {
                    onBackClick(event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else AlertUtil.showErrorAlert("Incorrect Input", "The passwords are different", "Please try again");
    }

}
