package org.example.term_porject_12_part_two.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;

public class SearchByPlayerHomeController {

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
    private Button CountryWiseCountButton;

    @FXML
    private AnchorPane SearchByClubAndCountryButton;

    @FXML
    private Button SearchByNameButton;

    @FXML
    private Text SearchByPlayerHomeTitle;

    @FXML
    private Button SearchByPositionButton;

    @FXML
    private Button SearchBySalaryRangeButton;

    @FXML
    private Button SearchByTeamButton;

    @FXML
    void onBackClick(ActionEvent event) {

        if(teamName.equals("Guest User")){
            System.out.println("entered guest main");
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
    void onSearchByClubAndCountryClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/PlayerQueryType2.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        PlayerQueryType2Controller playerQueryType2Controller = fxmlLoader.getController();
        playerQueryType2Controller.setStage(stage); // Pass the stage to the next controller
        playerQueryType2Controller.setTeamName(teamName);
        playerQueryType2Controller.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Country & Club");
        stage.show();
    }


    @FXML
    void onSearchByNameClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/PlayerQueryType1.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        PlayerQueryType1Controller playerQueryType1Controller = fxmlLoader.getController();
        playerQueryType1Controller.setStage(stage); // Pass the stage to the next controller
        playerQueryType1Controller.setTeamName(teamName);
        playerQueryType1Controller.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Name");
        stage.show();
    }

    @FXML
    void onSearchByPostionClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/PlayerQueryType3.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        PlayerQueryType3Controller playerQueryType3Controller = fxmlLoader.getController();
        playerQueryType3Controller.setStage(stage); // Pass the stage to the next controller
        playerQueryType3Controller.setTeamName(teamName);
        playerQueryType3Controller.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Position");
        stage.show();
    }

    @FXML
    void onSearchBySalaryClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/PlayerQueryType4.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        PlayerQueryType4Controller playerQueryType4Controller = fxmlLoader.getController();
        playerQueryType4Controller.setStage(stage); // Pass the stage to the next controller
        playerQueryType4Controller.setTeamName(teamName);
        playerQueryType4Controller.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Salary");
        stage.show();
    }

    public void onCountryWIseCountClick(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/PlayerQueryType5.fxml"));

        if (fxmlLoader == null) {
            System.out.println("FXMLLoader is null!");
        }

// Set the controller factory to inject dependencies before `initialize()` is called
        fxmlLoader.setControllerFactory(controllerClass -> {
            if (controllerClass == PlayerQueryType5Controller.class) {
                PlayerQueryType5Controller controller = new PlayerQueryType5Controller();
                controller.setStage(stage); // Set the stage
                controller.setTeamName(teamName); // Set the team name
                controller.setSocketWrapper(socketWrapper); // Set the socket wrapper
                return controller;
            } else {
                try {
                    return controllerClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML", e);
        }

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Countrywise Count");
        stage.show();
    }
}
