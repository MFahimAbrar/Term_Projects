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
import javafx.util.Duration;
import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;
import org.example.term_porject_12_part_two.Utility.SearchByPlayer;

import java.io.IOException;
import java.util.ArrayList;

public class PlayerQueryType4Controller {

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
    private Text LowerSalaryField;

    @FXML
    private TextField LowerSalaryInput;

    @FXML
    private ListView<Player> PlayerQueryType4Results;

    @FXML
    private Text UpperSalaryField;

    @FXML
    private TextField UpperSalaryInput;

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
    static String str1, str2;
    static Timeline timeline;
    @FXML
    void onSubmitClick(ActionEvent event) {
        // Start a Timeline to execute the query logic every 3 seconds
        if(flag) {str1 = LowerSalaryInput.getText(); str2 = UpperSalaryInput.getText(); return;}
        if(LowerSalaryInput.getText().isEmpty() || UpperSalaryInput.getText().isEmpty()){
            AlertUtil.showErrorAlert("Input Error", "Empty Input Provided", "Please try again with proper input");
            return;
        }
        Long lowerSalary, upperSalary;
        try{
            lowerSalary = Long.parseLong(LowerSalaryInput.getText().trim(), 10);
            upperSalary = Long.parseLong(UpperSalaryInput.getText().trim(), 10);
        }
        catch(NullPointerException | NumberFormatException e){
            AlertUtil.showErrorAlert("Input Error", "Invalid Input Provided", "Please try again with proper input");
            return;
        }
        if(lowerSalary < 0 || upperSalary < 0){
            AlertUtil.showErrorAlert("Input Error", "Invalid Input Provided", "Please try again with proper input");
            return;
        }
        str1 = LowerSalaryInput.getText(); str2 = UpperSalaryInput.getText();
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
        socketWrapper.write("playersalary="+str1+"="+str2);
        ArrayList<Player> queryResult = (ArrayList<Player>) socketWrapper.read();
        if (true) {
            // Create sample data
            ObservableList<Player> playerList = FXCollections.observableArrayList(queryResult);

            // Set items for the ListView
            PlayerQueryType4Results.setItems(playerList);

            // Set custom cell factory
            PlayerQueryType4Results.setCellFactory(param -> new ListCell<>() {
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
