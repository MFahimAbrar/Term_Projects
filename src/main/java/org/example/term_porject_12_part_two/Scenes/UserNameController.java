package org.example.term_porject_12_part_two.Scenes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;

public class UserNameController {

    private Stage stage;


    public UserNameController() throws IOException {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private SocketWrapper socketWrapper;
    public void setSocketWrapper(SocketWrapper socketWrapper) {this.socketWrapper = socketWrapper;}
    @FXML
    private Button LogInButton;

    @FXML
    private Button SignUpButton;

    @FXML
    private Text userNameField;

    @FXML
    private TextField userNameInput;

    @FXML
    private Text userNameTitle;

    @FXML
    void onLogInClick(ActionEvent event) throws IOException, ClassNotFoundException {
        System.out.println("socket status " + socketWrapper + socketWrapper.isConnected() + socketWrapper.isClosed());
        if(userNameInput.getText().isEmpty()) {AlertUtil.showErrorAlert("Invalid Input", "There is an empty field", "please write something"); return;}
        socketWrapper.write(("LOGINLOGIN#"+userNameInput.getText()));
//        System.out.println(userNameInput.getText());
        String s = (String) socketWrapper.read();
        System.out.println(s);
        if(s.equals("UNSUCCESSUNSUCCESS")){
            Platform.runLater(() -> {
                AlertUtil.showErrorAlert("Login Error", "There is no such username", "Please try again");
            });
        }
        else{
            Platform.runLater(() -> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/Password.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e);
                }

                // Get the controller for ShowUserController
                PasswordController passwordController = fxmlLoader.getController();
                passwordController.setStage(stage); // Pass the stage to the next controller
                passwordController.setSocketWrapper(socketWrapper);
                passwordController.setTeamName(userNameInput.getText());
                System.out.println(userNameInput.getText());

                stage.setScene(scene); // Set the new scene
                stage.setTitle("Password");
                stage.show();
            });
        }
    }

    @FXML
    void onSingUpClick(ActionEvent event) throws IOException, ClassNotFoundException {
        if(userNameInput.getText().isEmpty()) {AlertUtil.showErrorAlert("Invalid Input", "There is an empty field", "please write something"); return;}
        System.out.println("signing1");
        socketWrapper.write(("NEWUSERNEWUSER#"+userNameInput.getText()));
        System.out.println("signing2");
        String s = (String) socketWrapper.read();
        System.out.println("s");
        if(s.equals("ALREADYALREADY")){
            Platform.runLater(() -> {
            AlertUtil.showErrorAlert("Invalid Input", "This username already exist", "try again with a different username");} );
        }
        else if(s.equals("SUCCESSSUCCESS2")){
            Platform.runLater(() -> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/SetPassword.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    System.out.println(e);
                }

                // Get the controller for ShowUserController
                SetPasswordController setPasswordController = fxmlLoader.getController();
                setPasswordController.setStage(stage); // Pass the stage to the next controller
                setPasswordController.setSocketWrapper(socketWrapper);
                setPasswordController.setTeamName(userNameInput.getText());

                stage.setScene(scene); // Set the new scene
                stage.setTitle("Password");

                stage.show();
            });
        }
    }

}
