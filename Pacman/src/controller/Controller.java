package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.ButtonType;

import javafx.scene.media.AudioClip;

import javafx.stage.Modality;
import javafx.stage.Stage;



import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;




/**
 * 
 * Main Controller
 *
 */
public class Controller implements Initializable {
    @FXML
private Button playGame_btn;
    @FXML
    private Button leaderboard_btn;
    @FXML
    private Button manageQ_btn;
    @FXML
    private Button options_btn;
    @FXML
    private Button exit_btn;
    @FXML
    private Button help_btn;


    public static AudioClip note = new AudioClip("file:MadnessHalloween.mp3");

    private static boolean alreadyPlaying = false;

    


    
    

  /**
   * handles button clicks to open windows
   */
    @FXML
/**
 * Handle Button Clicks
 * @param mouseEvent
 */
        private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
            if (mouseEvent.getSource() == playGame_btn) {
                loadStage("/view/playerNicknameScreen.fxml");
                closeButtonAction(playGame_btn);
            } else if (mouseEvent.getSource() == leaderboard_btn) {
                loadStage("/view/leaderboard.fxml");
                closeButtonAction(leaderboard_btn);
            } else if (mouseEvent.getSource() == manageQ_btn) {
                loadStage("/view/manageQ.fxml");
                closeButtonAction(manageQ_btn);
            } else if (mouseEvent.getSource() == options_btn) {
                loadStage("/view/options.fxml");
                closeButtonAction(options_btn);
            } else if (mouseEvent.getSource() == exit_btn) {
            	note.stop();
            	Platform.setImplicitExit(true);
                Platform.exit();
                closeButtonAction(exit_btn);
                System.exit(1);
            }else if (mouseEvent.getSource() == help_btn) {
                loadStage("/view/instructions.fxml");
                closeButtonAction(help_btn);
            }
    }
    
    /**
     * close window
     * @param b
     */
    @FXML
    private void closeButtonAction(Button b){
        // get a handle to the stage
        Stage stage = (Stage) b.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    


/**
 * Load the fxml file
 * @param fxml
 */
    public static void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(Controller.class.getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pacman-Viper");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.setResizable(false);
            stage.setOnCloseRequest((ae) -> {
            	Platform.setImplicitExit(true);
            	note.stop();
                Platform.exit();
                System.exit(1);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    /**
     * Create Alert when exiting the window 
     */
    public static void createAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
        	Platform.setImplicitExit(true);
            Platform.exit();
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle arg1) {
		if (alreadyPlaying==false) {
    		note.play();

    		note.setCycleCount(note.INDEFINITE);

    		note.setCycleCount(note.INDEFINITE);

		    alreadyPlaying=true;
    	}
		
	}

}
