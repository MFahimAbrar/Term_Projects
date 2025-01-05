package org.example.term_porject_12_part_two.Scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;

public class SearchByClubHomeController {

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
    private Button ByMaximumAgeButton;

    @FXML
    private Button ByMaximumHeightButton;

    @FXML
    private Button ByMaximumSalaryButton;

    @FXML
    private Text SearchByClubHomeTitle;

    @FXML
    private Button TotalSalaryButton;

    @FXML
    void onBackClick(ActionEvent event) {

        if(teamName.equals("Guest User")){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/GuestUser.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Get the controller for ShowUserController
            GuestUserController guestUserController = fxmlLoader.getController();
            guestUserController.setStage(stage); // Pass the stage to the next controller
            guestUserController.setTeamName(teamName);
            guestUserController.setSocketWrapper(socketWrapper);

            stage.setScene(scene); // Set the new scene
            stage.setTitle("Home Page");
            stage.show();
        }

        else{
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
    }}

    @FXML
    void onMaximumAgeClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/ClubQueryType3.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        ClubQueryType3Controller clubQueryType3Controller = fxmlLoader.getController();
        clubQueryType3Controller.setStage(stage); // Pass the stage to the next controller
        clubQueryType3Controller.setTeamName(teamName);
        clubQueryType3Controller.setSocketWrapper(socketWrapper);


        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Maximum Age");
        stage.show();
    }

    @FXML
    void onMaximumHeightClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/ClubQueryType2.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        ClubQueryType2Controller clubQueryType2Controller = fxmlLoader.getController();
        clubQueryType2Controller.setStage(stage); // Pass the stage to the next controller
        clubQueryType2Controller.setTeamName(teamName);
        clubQueryType2Controller.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Maximum Height");
        stage.show();
    }

    @FXML
    void onMaximumSalaryClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/ClubQueryType1.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        ClubQueryType1Controller clubQueryType1Controller = fxmlLoader.getController();
        clubQueryType1Controller.setStage(stage); // Pass the stage to the next controller
        clubQueryType1Controller.setTeamName(teamName);
        clubQueryType1Controller.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Maximum Salary");
        stage.show();
    }

    @FXML
    void onTotalSalaryClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/ClubQueryType4.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        ClubQueryType4Controller clubQueryType4Controller = fxmlLoader.getController();
        clubQueryType4Controller.setStage(stage); // Pass the stage to the next controller
        clubQueryType4Controller.setTeamName(teamName);
        clubQueryType4Controller.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Club Total Salary");
        stage.show();
    }

}
