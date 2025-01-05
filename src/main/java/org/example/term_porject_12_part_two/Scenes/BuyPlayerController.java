package org.example.term_porject_12_part_two.Scenes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import org.example.term_porject_12_part_two.Data.Player;
import org.example.term_porject_12_part_two.Data.PlayerForSale;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;
import java.util.ArrayList;

public class BuyPlayerController {

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
    private ListView<PlayerForSale> DisplayTeamPlayer;

    @FXML
    private Text SelectedRowContent;

    @FXML
    private Button SubmitButton;

    @FXML
    void onBackClick(ActionEvent event) {
        timeline.stop();
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
    void onSubmitClick(ActionEvent event) throws IOException, ClassNotFoundException{
        if(SelectedRowContent.getText().isEmpty()) {
            AlertUtil.showErrorAlert("Invalid Input", "No player is selected", "Select a player and try again"); return;
        }
        boolean flag=AlertUtil.showConfirmationAlert("Confirm buy", "Do you want to buy the selected player");
        if(flag){
            socketWrapper.write("buyfrommarket="+SelectedRowContent.getText()+"="+teamName);
            String response = (String) socketWrapper.read();
            if(response.equals("sameteamerror")){
                AlertUtil.showErrorAlert("Invalid Operation", "You are trying to buy your own player", "Please select different player and try again");
            }
            else if(response.equals("notfound")){
                AlertUtil.showErrorAlert("Invalid Operation", "Sorry, the player is already bought or withdrawn from the market", "Please select different player and try again");
            }
            else{
                AlertUtil.showSuccessAlert("Success", "You successfully bought the player", "Press ok to continue");
            }
        }
    }

    static Timeline timeline;
    public void initialize() throws IOException, ClassNotFoundException{
        // Start a Timeline to execute the query logic every 3 seconds
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> {
                    try {
                        updatePlayersForSaleResults();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        timeline.play();

    }

    private void updatePlayersForSaleResults() throws IOException, ClassNotFoundException{
        socketWrapper.write("viewallplayerforsell=" + teamName);
        ArrayList<PlayerForSale> teamPlayers = (ArrayList<PlayerForSale>) socketWrapper.read();
        Platform.runLater(() -> {
            // Create sample data
            ObservableList<PlayerForSale> playerList = FXCollections.observableArrayList(teamPlayers);

            // Set items for the ListView
            DisplayTeamPlayer.setItems(playerList);

            // Set custom cell factory
            DisplayTeamPlayer.setCellFactory(param -> new ListCell<PlayerForSale>() {
                @Override
                protected void updateItem(PlayerForSale player, boolean empty) {
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
                        Text sellingPriceText = new Text(String.valueOf(player.getSellPrice()));

                        // Set fixed widths for each Text node
                        nameText.setWrappingWidth(140);
                        countryText.setWrappingWidth(90);
                        clubText.setWrappingWidth(150);
                        positionText.setWrappingWidth(130);
                        ageText.setWrappingWidth(130);
                        numberText.setWrappingWidth(130);
                        salaryText.setWrappingWidth(140);
                        heightText.setWrappingWidth(60);
                        sellingPriceText.setWrappingWidth(90);

                        // Add Text nodes to HBox
                        hBox.getChildren().addAll(nameText, countryText, clubText, positionText, ageText, numberText, salaryText, heightText, sellingPriceText);

                        // Set HBox as the graphic for this cell
                        setGraphic(hBox);
                    }
                }
            });
            // Add listener for row selection
            addRowSelectionListener();
        });
    }

    private void addRowSelectionListener() {
        DisplayTeamPlayer.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Concatenate the content of the selected row
                String concatenatedContent = concatenateRowContent(newSelection);
                SelectedRowContent.setText(concatenatedContent); // Display the concatenated content
            }
        });
    }

    // Function to concatenate the content of a selected row
    private String concatenateRowContent(PlayerForSale player) {
        StringBuilder content = new StringBuilder();
        content.append(player.getName()).append(",");
        content.append(player.getCountry()).append(",");
        content.append(player.getAge()).append(",");
        content.append(player.getHeight()).append(",");
        content.append(player.getClub()).append(",");
        content.append(player.getPosition()).append(",");
        if (player.getNumber() != 0) {
            content.append(player.getNumber());
        }
        content.append(",");
        content.append(player.getWeeklySalary()).append(",");
        content.append(player.getSellPrice());
        return content.toString();
    }

}
