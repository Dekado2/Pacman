package controller;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.scene.control.Button;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.PacmanPlayer;
import model.PacmanPlayerAdapter;
import utils.E_Direction;
import view.GameSession;
import java.net.URL;
import java.util.ResourceBundle;

public class playerNicknameController implements Initializable {
    @FXML
    private StackPane stackPane_nickname;
@FXML
private Pane p1_panel;
    @FXML
    private Pane p2_panel;
@FXML
private Button oneP_btn;
    @FXML
    private Button twoP_btn;
    @FXML
    private Button back_btn;
    @FXML
    private Button cancel_btn;
    @FXML
    private Button start_btn;
    @FXML
    private TextField txt_oneP;
    @FXML
    private TextField txt_twoP_1;
    @FXML
    private TextField txt_twoP_2;
    boolean onePisclicked=false;
    boolean twoPisclicked=false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    
    }
    @FXML
    /**
     * Handle Button Clicks
     * @param mouseEvent
     */
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) throws InterruptedException {
        stackPane_nickname.setVisible(true);
       
        if (mouseEvent.getSource() == oneP_btn) {
            oneP_btn.setStyle(" -fx-background-color:  rgba(186, 186, 186,0.8)");
            twoP_btn.setStyle("-fx-background-color: rgba(186, 186, 186, 0.3)");
            p1_panel.setDisable(false);
            p1_panel.setVisible(true);
            p2_panel.setDisable(true);
            p2_panel.setVisible(false);
            onePisclicked=true;
            twoPisclicked=false;
        } else if (mouseEvent.getSource() == twoP_btn) {
            twoP_btn.setStyle(" -fx-background-color:  rgba(186, 186, 186,0.8)");
            oneP_btn.setStyle("-fx-background-color: rgba(186, 186, 186, 0.3)");
            p1_panel.setDisable(true);
            p1_panel.setVisible(false);
            p2_panel.setDisable(false);
            p2_panel.setVisible(true);
            twoPisclicked=true;
            onePisclicked=false;
        }else if (mouseEvent.getSource() == back_btn) {
            Controller.loadStage("/view/mainStyle.fxml");
            closeButtonAction(start_btn);
        }else if (mouseEvent.getSource() == cancel_btn) {
            Controller.createAlert();
        }
        else if(onePisclicked){
        	if(mouseEvent.getSource() == start_btn) {
	        		if(txt_oneP.getText()==null || txt_oneP.getText().toString().isEmpty() || txt_oneP.getText().equals("Please insert your nickname") ) 
	        			createAlert("Please insert your nickname");
	        		else {
	        			PacmanPlayer player1=new PacmanPlayer(E_Direction.RIGHT, txt_oneP.getText());
	        			PacmanPlayerAdapter playerA= new PacmanPlayerAdapter(player1);
	        			new GameSession(playerA, null);
	        			closeButtonAction(start_btn);
	        		}
	        }
        }
	    else if(twoPisclicked){
	    	if(mouseEvent.getSource() == start_btn) {
			    		if((txt_twoP_1.getText().toString().isEmpty() && txt_twoP_2.getText().toString().isEmpty()) ||(txt_twoP_1.getText().equals("Please insert your nickname") && txt_twoP_2.getText().equals("Please insert your nickname")))
		        			createAlert("Please insert nicknames");
			    		else if(txt_twoP_1.getText().toString().isEmpty() || txt_twoP_1.getText().equals("Please insert your nickname"))
	            			createAlert("Please insert nickname for Player 1 ");
	            		else if(txt_twoP_2.getText().isEmpty() || txt_twoP_2.getText().equals("Please insert your nickname"))
	            			createAlert("Please insert nickname for Player 2 ");
	            		
	            		else {
	            			PacmanPlayer player1=new PacmanPlayer(E_Direction.RIGHT, txt_twoP_1.getText());
	            			PacmanPlayerAdapter playerA= new PacmanPlayerAdapter(player1);
		        			PacmanPlayer player2 =new PacmanPlayer(E_Direction.RIGHT, txt_twoP_2.getText());
		        			PacmanPlayerAdapter playerB= new PacmanPlayerAdapter(player2);
		        			new GameSession(playerA, playerB);
		        			closeButtonAction(start_btn);
	            		}
	    			}
	        	}
    }
    
    /**
     * Close windows
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
     * Create Alert  
     * @param str
     */
    public static void createAlert(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, str, ButtonType.OK);
        alert.setTitle("Information");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

}
