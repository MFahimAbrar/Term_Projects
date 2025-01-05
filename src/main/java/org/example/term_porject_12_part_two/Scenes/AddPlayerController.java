package org.example.term_porject_12_part_two.Scenes;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;
import org.example.term_porject_12_part_two.Utility.AddPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AddPlayerController {

    private Stage stage;

    private SocketWrapper socketWrapper;
    private String teamName = null;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setSocketWrapper(SocketWrapper socketWrapper) {this.socketWrapper = socketWrapper;}
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @FXML
    private TextField AgeInput;

    @FXML
    private Button BackButton;

    @FXML
    private TextField CountryInput;

    @FXML
    private TextField HeightInput;

    @FXML
    private TextField NameInput;

    @FXML
    private TextField NumberInput;

    @FXML
    private TextField PositionInput;

    @FXML
    private TextField SalaryInput;

    @FXML
    private Button SubmitButton;


    @FXML
    void onBackClick(ActionEvent event) {
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
        stage.setTitle("Home");
        stage.show();
    }

    @FXML
    void onSubmitClick(ActionEvent event) throws IOException, ClassNotFoundException{
        Long weeklySalary = 0L, age = 0L;
        Long number = -1L;
        Double height = 0.0;
        try{
            weeklySalary = Long.parseLong(SalaryInput.getText().trim(), 10);
        }
        catch(NullPointerException | NumberFormatException e){
            AlertUtil.showErrorAlert("Input Error", "Invalid Input Provided", "Please try again with proper input");
            return;
        }
        try{
            height = Double.parseDouble(HeightInput.getText().trim());
        }
        catch(NullPointerException | NumberFormatException e){
            AlertUtil.showErrorAlert("Input Error", "Invalid Input Provided", "Please try again with proper input");
            return;
        }
        try{
            number = Long.parseLong(NumberInput.getText().trim(), 10);
        }
        catch(NullPointerException | NumberFormatException e){
            AlertUtil.showErrorAlert("Input Error", "Invalid Input Provided", "Please try again with proper input");
            return;
        }
        try{
            age = Long.parseLong(AgeInput.getText().trim(), 10);
        }
        catch(NullPointerException | NumberFormatException e){
            AlertUtil.showErrorAlert("Input Error", "Invalid Input Provided", "Please try again with proper input");
            return;
        }
        if(NameInput.getText().isEmpty() || CountryInput.getText().isEmpty() || PositionInput.getText().isEmpty()){
            AlertUtil.showErrorAlert("Input Error", "Empty Input Provided", "Please try again with proper input");
            return;
        }
        socketWrapper.write("addplayer=");
        Player newPlayer = new Player(NameInput.getText(), CountryInput.getText(), teamName, PositionInput.getText(), age, number, weeklySalary, height);
        socketWrapper.write(newPlayer);
        int i = (int) socketWrapper.read();
        Platform.runLater(() -> {
            if(i == -1) AlertUtil.showErrorAlert("Input Error", "Invalid Input Provided", "Please try again with proper input");
            else if(i == -2) AlertUtil.showErrorAlert("Input Error", "Player Already Exists", "Please try again with proper input");
            else AlertUtil.showSuccessAlert("Input Successful", "New Player Added", "Click OK to continue");
        });
    }

}
