package org.example.term_porject_12_part_two.Scenes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

import javafx.util.Duration;
import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Network_Util.MessageType1;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;
import org.example.term_porject_12_part_two.Utility.SearchByPlayer;

public class PlayerQueryType1Controller {

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
    private Button InputSubmissionButton;

    @FXML
    private Text NameField;

    @FXML
    private TextField NameInput;

    @FXML
    private ListView<Player> PlayerQueryType1Results;

    @FXML
    void onBackClick(ActionEvent event) {
        if(flag) timeline.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/term_porject_12_part_two/SearchByPlayerFXML.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the controller for ShowUserController
        SearchByPlayerHomeController searchByPlayerHomeController = fxmlLoader.getController();
        searchByPlayerHomeController.setStage(stage); // Pass the stage to the next controller
        searchByPlayerHomeController.setTeamName(teamName);
        searchByPlayerHomeController.setSocketWrapper(socketWrapper);

        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Player");
        stage.show();
    }

    static boolean flag = false;
    static String str1;
    static Timeline timeline;
    @FXML
    void onSubmitClick(ActionEvent event){
        // Start a Timeline to execute the query logic every 3 seconds
        if(flag) {str1 = NameInput.getText(); return;}
        if(NameInput.getText().isEmpty()){
            AlertUtil.showErrorAlert("Input Error", "Empty Input Provided", "Please try again with proper input");
            return;
        }
        str1 = NameInput.getText();
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5), e -> {
                    try {
                        updatePlayerQueryResults();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        timeline.play();
        flag = true;
    }

    private void updatePlayerQueryResults() throws IOException, ClassNotFoundException{
        socketWrapper.write("playername="+str1);
        ArrayList<Player> queryResult = (ArrayList<Player>) socketWrapper.read();
        if (true) {
            // Create sample data
            ObservableList<Player> playerList = FXCollections.observableArrayList(queryResult);

            // Set items for the ListView
            PlayerQueryType1Results.setItems(playerList);

            // Set custom cell factory
            PlayerQueryType1Results.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Player player, boolean empty) {
                    super.updateItem(player, empty);

                    if (empty || player == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Create an HBox to hold the columns
                        HBox hBox = new HBox(10); // 10 is spacing between columns

                        // Create Text nodes for each variable
                        Text nameText = new Text(player.getName());
                        Text countryText = new Text(player.getCountry());
                        Text clubText = new Text(player.getClub());
                        Text positionText = new Text(player.getPosition());
                        Text ageText = new Text(String.valueOf(player.getAge()));
                        Text numberText;
                        if (player.getNumber() != 0) numberText = new Text(String.valueOf(player.getNumber()));
                        else numberText = new Text();
                        Text salaryText = new Text("R" + player.getWeeklySalary());
                        Text heightText = new Text(player.getHeight() + "m");

                        // Set fixed widths for each Text node
                        nameText.setWrappingWidth(150);
                        countryText.setWrappingWidth(100);
                        clubText.setWrappingWidth(170);
                        positionText.setWrappingWidth(130);
                        ageText.setWrappingWidth(130);
                        numberText.setWrappingWidth(150);
                        salaryText.setWrappingWidth(150);
                        heightText.setWrappingWidth(70);

                        // Add Text nodes to HBox
                        hBox.getChildren().addAll(nameText, countryText, clubText, positionText, ageText, numberText, salaryText, heightText);

                        // Set HBox as the graphic for this cell
                        setGraphic(hBox);
                    }
                }
            });
        }
    }
}

