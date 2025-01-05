package org.example.term_porject_12_part_two.Scenes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
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
import java.util.HashMap;

public class PlayerQueryType5Controller {

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
    private ListView<String> PlayerQueryType5Results;

    static Timeline timeline;
    public void initialize() throws IOException, ClassNotFoundException {
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

    }

    private void updatePlayerQueryResults() throws IOException, ClassNotFoundException{
        socketWrapper.write("countrywisecount");
        HashMap<String, Long> countryWiseCount = (HashMap<String, Long>) socketWrapper.read();

        Platform.runLater(() -> {
            ObservableList<String> personKeys = FXCollections.observableArrayList(countryWiseCount.keySet());

            // Set items for the ListView
            PlayerQueryType5Results.setItems(personKeys);

            // Set custom cell factory
            PlayerQueryType5Results.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(String key, boolean empty) {
                    super.updateItem(key, empty);

                    if (empty || key == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Create an HBox to display key-value pairs
                        HBox hBox = new HBox(10); // 10 is spacing between columns

                        // Create Text nodes for key and value
                        Text keyText = new Text(key + ": ");
                        Text valueText = new Text(String.valueOf(countryWiseCount.get(key)));

                        keyText.setWrappingWidth(470);
                        valueText.setWrappingWidth(470);

                        keyText.setTextAlignment(TextAlignment.CENTER);
                        valueText.setTextAlignment(TextAlignment.CENTER);

                        // Add Text nodes to HBox
                        hBox.getChildren().addAll(keyText, valueText);

                        // Set HBox as the graphic for this cell
                        setGraphic(hBox);
                    }
                }
            });
        });
    }

    @FXML
    void onBackClick(ActionEvent event) {
        timeline.stop();
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


}
