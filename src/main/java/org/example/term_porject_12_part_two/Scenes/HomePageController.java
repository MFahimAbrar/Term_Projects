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

public class HomePageController {

    private Stage stage;
    private SocketWrapper socketWrapper;
    private String teamName = null;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setSocketWrapper(SocketWrapper socketWrapper) {this.socketWrapper = socketWrapper;}
    public void setTeamName(String teamName) {
        this.teamName = teamName;
        TeamNameText.setText("Team " + teamName);
    }

    @FXML
    private Button AddPlayerButton;

    @FXML
    private Button BuyPlayersButton;

    @FXML
    private Button ChangePasswordButton;

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
    private Button SellOwnPlayerButton;

    @FXML
    private Text TeamNameText;

    @FXML
    private Button ViewTeamPlayersButton;

    @FXML
    void onAddPlayerClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/AddPlayer.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        AddPlayerController addPlayerController = fxmlLoader.getController();
        addPlayerController.setStage(stage); // Pass the stage to the next controller
        addPlayerController.setTeamName(teamName);
        addPlayerController.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Add Player");
        stage.show();
    }

    @FXML
    void onBuyPlayerClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/BuyPlayer.fxml"));

        if (fxmlLoader == null) {
            System.out.println("FXMLLoader is null!");
        }

// Set the controller factory to inject dependencies before `initialize()` is called
        fxmlLoader.setControllerFactory(controllerClass -> {
            if (controllerClass == BuyPlayerController.class) {
                BuyPlayerController controller = new BuyPlayerController();
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
        stage.setTitle("Buy Player");
        stage.show();
    }

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

    @FXML
    void onChangePasswordClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/ChangePassword.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        ChangePasswordController changePasswordController = fxmlLoader.getController();
        changePasswordController.setStage(stage); // Pass the stage to the next controller
        changePasswordController.setTeamName(teamName);
        changePasswordController.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Change Password");
        stage.show();
    }

    @FXML
    void onSellOwnPlayerClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/SellPlayer.fxml"));

        if (fxmlLoader == null) {
            System.out.println("FXMLLoader is null!");
        }

// Set the controller factory to inject dependencies before `initialize()` is called
        fxmlLoader.setControllerFactory(controllerClass -> {
            if (controllerClass == SellPlayerController.class) {
                SellPlayerController controller = new SellPlayerController();
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
        stage.setTitle("Sell Own Player");
        stage.show();
    }

    @FXML
    void onViewTeamPlayersClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/ViewTeamPlayer.fxml"));

        if (fxmlLoader == null) {
            System.out.println("FXMLLoader is null!");
        }

// Set the controller factory to inject dependencies before `initialize()` is called
        fxmlLoader.setControllerFactory(controllerClass -> {
            if (controllerClass == ViewTeamPlayerController.class) {
                ViewTeamPlayerController controller = new ViewTeamPlayerController();
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
        stage.setTitle("View Team Player");
        stage.show();
    }

}
