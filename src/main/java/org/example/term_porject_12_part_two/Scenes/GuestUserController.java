package org.example.term_porject_12_part_two.Scenes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;

import static java.lang.System.exit;

public class GuestUserController {

    private Stage stage;
    private SocketWrapper socketWrapper;
    private String teamName = "Guest User";

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setSocketWrapper(SocketWrapper socketWrapper) {this.socketWrapper = socketWrapper;}
    public void setTeamName(String teamName) {
        this.teamName = teamName;
        TeamNameText.setText("Team " + teamName);
    }

    @FXML
    private AnchorPane HomePageAnchor;

    @FXML
    private Text HomePageTItleText;

    @FXML
    private Button LogOutButton;

    @FXML
    private Button SearchByPlayersButton;

    @FXML
    private Button SearchByTeamButton;

    @FXML
    private Text TeamNameText;


    @FXML
    void onLogOutClick(ActionEvent event) {
        boolean flag = AlertUtil.showConfirmationAlert("Exit System", "Do you want to exit the system?");
        if(flag) exit(1);
    }

    @FXML
    void onSearchByPlayerClick(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/SearchByPlayerFXML.fxml"));
        Scene scene = null;
        System.out.println("ss " + socketWrapper);
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        SearchByPlayerHomeController searchByPlayerHomeController = fxmlLoader.getController();
        searchByPlayerHomeController.setStage(stage); // Pass the stage to the next controller
        searchByPlayerHomeController.setSocketWrapper(socketWrapper);
        searchByPlayerHomeController.setTeamName(teamName);
        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Player");
        stage.show();
    }

    @FXML
    void onSearchByTeamClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/SearchByClubFXML.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        SearchByClubHomeController searchByClubHomeController = fxmlLoader.getController();
        searchByClubHomeController.setStage(stage); // Pass the stage to the next controller
        searchByClubHomeController.setTeamName(teamName);
        searchByClubHomeController.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Club");
        stage.show();
    }

}
