package controller;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Answer;
import model.Question;
import model.SysData;
import utils.E_DifficultyLevel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.ResourceBundle;
import java.util.logging.*;

public class questionsController implements Initializable {

	private SysData instance;
	@FXML
	private TableColumn<Question, Integer> questionID;
    @FXML
    private TableView<Question> managQ_tbl;
    @FXML
    public TableColumn<Question, String> question;

    @FXML
    public TableColumn<Question, ArrayList<Answer>> options;

    @FXML
    public TableColumn<Answer,String> answer;
    @FXML
    public TableColumn<Question, E_DifficultyLevel> difficulty;
    @FXML
    private Button back_btn;
    @FXML
    private Button cancel_btn;
    @FXML
    private Button add_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button remove_btn;


    public questionsController(){

    }
    @Override
    /**
     * Initializes the manage Questions window
     */
    public void initialize(URL location, ResourceBundle resources) {
    	
    	
    	questionID.setCellValueFactory(new PropertyValueFactory<>("qID"));

        question.setCellValueFactory(new PropertyValueFactory<>("qText"));

        options.setCellValueFactory(new PropertyValueFactory<Question, ArrayList<Answer>>("possibleAnswers"));

        answer.setCellValueFactory(new PropertyValueFactory<Answer, String>("correctAnswer"));

        difficulty.setCellValueFactory(new PropertyValueFactory<>("difficultyLevel"));

        managQ_tbl.setItems(QuestionsModels);
        
        instance = SysData.getInstance();

    }
    //
    private ObservableList<Question> QuestionsModels =  FXCollections.observableArrayList(SysData.getInstance().getQuestionsList());
    @FXML
    /**
     * Handle Button Clicks
     * @param mouseEvent
     */
    private void handleButtonClicks(ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == back_btn) {
            Controller.loadStage("/view/mainStyle.fxml");
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        }
        else if (mouseEvent.getSource() == cancel_btn) {
            Controller.createAlert();
        }
        else if (mouseEvent.getSource() == remove_btn){
        	Question q = managQ_tbl.getSelectionModel().getSelectedItem();
        	if(q!=null){
        		instance.removeQuestion((managQ_tbl.getSelectionModel().getSelectedItem().getQID()));
            	managQ_tbl.getItems().remove(q);
        	}
        	else createAlert("You must choose a question to remove from the table");

        }else if ( mouseEvent.getSource() == add_btn) {
            Controller.loadStage("/view/addQ.fxml");
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
	    }
        else if (mouseEvent.getSource() == edit_btn) {
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EditQ.fxml"));
            try {
            	
            	loader.load();
            }catch(IOException e) {
            	Logger.getLogger(questionsController.class.getName()).log(Level.SEVERE,null,e);
            }
            editQuestionsController editq= loader.getController();
           if( managQ_tbl.getSelectionModel().getSelectedItem()!=null) {
           String aTxt=managQ_tbl.getSelectionModel().getSelectedItem().getPossibleAnswers().get(0).getAText();
           String bTxt=managQ_tbl.getSelectionModel().getSelectedItem().getPossibleAnswers().get(1).getAText();
           editq.setA_txt(aTxt);
           editq.setB_txt(bTxt);
           if(managQ_tbl.getSelectionModel().getSelectedItem().getPossibleAnswers().size()==3) {
	           String cTxt=managQ_tbl.getSelectionModel().getSelectedItem().getPossibleAnswers().get(2).getAText();
	           editq.setC_txt(cTxt);
           }
           else if(managQ_tbl.getSelectionModel().getSelectedItem().getPossibleAnswers().size()>3) {
           String cTxt=managQ_tbl.getSelectionModel().getSelectedItem().getPossibleAnswers().get(2).getAText();
	       editq.setC_txt(cTxt);
           String dTxt=managQ_tbl.getSelectionModel().getSelectedItem().getPossibleAnswers().get(3).getAText();
           editq.setD_txt(dTxt);
           }
           String qTxt=managQ_tbl.getSelectionModel().getSelectedItem().getQText();
            editq.setQ_txt(qTxt);
            int difficulty=managQ_tbl.getSelectionModel().getSelectedItem().getDifficultyLevel().getDiff();
            E_DifficultyLevel levelName = E_DifficultyLevel.EASY;
    		levelName=levelName.toStringDifficultyLevel(difficulty);
            editq.setDifficulty_cb(levelName.toString());
            Answer ans= managQ_tbl.getSelectionModel().getSelectedItem().getCorrectAnswer();
            editq.setCorrectAns_cb(Integer.toString(ans.getAID()));           
        	     Question question=managQ_tbl.getSelectionModel().getSelectedItem();
    	     editq.setQ(question);
      	     editq.setManagQ_tbl(managQ_tbl);
          
            Parent p = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.setTitle("Pacman-Viper");
            stage.show();
            stage.setResizable(false);
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
            stage.setOnCloseRequest((ae) -> {
            	Controller.note.stop();
            	Platform.setImplicitExit(true);
                Platform.exit();
                System.exit(1);
            });
        }
       	else createAlert("You must choose a question to edit from the table");
	    }
    }
    /**
     * Creates Alert
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
