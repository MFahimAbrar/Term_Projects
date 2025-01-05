package org.example.term_porject_12_part_two.Scenes;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;
import java.util.ArrayList;

public class ViewTeamPlayerController {

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
    private Text NumberText;

    @FXML
    private Text SalaryText;

    @FXML
    private Text TeamText;

    @FXML
    private ListView<Player> DisplayTeamPlayer;

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
        stage.setTitle("Home Page");
        stage.show();
    }

    public void initialize() throws IOException, ClassNotFoundException {
        socketWrapper.write("viewteamplayer=" + teamName);
        ArrayList<Player> teamPlayers = (ArrayList<Player>) socketWrapper.read();
        String totalSalary = (String) socketWrapper.read();
        TeamText.setText(teamName);
        NumberText.setText("Number of Players: " + teamPlayers.size());
        SalaryText.setText("Total Salary of Players: " + totalSalary);
        Platform.runLater(() -> {
            // Create sample data
            ObservableList<Player> playerList = FXCollections.observableArrayList(teamPlayers);

            // Set items for the ListView
            DisplayTeamPlayer.setItems(playerList);

            // Set custom cell factory
            DisplayTeamPlayer.setCellFactory(param -> new ListCell<>() {
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
        });
    }

}
