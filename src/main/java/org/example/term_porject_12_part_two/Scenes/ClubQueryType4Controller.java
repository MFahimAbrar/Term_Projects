package org.example.term_porject_12_part_two.Scenes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.term_porject_12_part_two.Network_Util.SocketWrapper;

import java.io.IOException;

public class ClubQueryType4Controller {

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
    private Text resultText;

    @FXML
    void onBackClick(ActionEvent event) {
        if(flag) timeline.stop();
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
        searchByClubHomeController.setSocketWrapper(socketWrapper);
        searchByClubHomeController.setTeamName(teamName);


        stage.setScene(scene); // Set the new scene
        stage.setTitle("Search by Club");
        stage.show();
    }

    static boolean flag = false;
    static String str1;
    static Timeline timeline;
    @FXML
    void onSubmitClick(ActionEvent event) throws IOException, ClassNotFoundException{

        if(flag) {str1 = NameInput.getText(); return;}
        str1 = NameInput.getText();
        if(NameInput.getText().isEmpty()){
            AlertUtil.showErrorAlert("Input Error", "Empty Input Provided", "Please try again with proper input");
            return;
        }
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5), e -> {
                    try {
                        updateClubQueryResults();
                    } catch (IOException | ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        timeline.play();
        flag = true;
    }

    private void updateClubQueryResults() throws IOException, ClassNotFoundException{
        socketWrapper.write("clubtotalsalary=" + NameInput.getText());
        String totalSalary = (String) socketWrapper.read();
        if(totalSalary == null) resultText.setText("The total salary of club "+NameInput.getText()+" is 0"+totalSalary);
        else resultText.setText("The total salary of club "+NameInput.getText()+" is "+totalSalary);
    }

}
