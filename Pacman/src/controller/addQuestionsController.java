package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.SysData;

import java.net.URL;
import java.util.ResourceBundle;

public class addQuestionsController implements Initializable {

    @FXML
    private Button add_btn1;
    @FXML
    private ComboBox <String> difficulty_cb;
    @FXML
    private ComboBox <String> correctAns_cb;
    @FXML
    private TextField a_txt;
    @FXML
    private TextField b_txt;
    @FXML
    private TextField c_txt;
    @FXML
    private TextField d_txt;
    @FXML
    private TextArea q_txt;
    @FXML
    private Button back_btn;
    @FXML
    private Button cancel_btn;
    private SysData instance;
    private int qID;

	@Override
    /**
     * Initialize the 
     */
    public void initialize(URL location, ResourceBundle resources) {
        correctAns_cb.getItems().addAll("1","2","3","4");
        difficulty_cb.getItems().addAll("EASY","MEDIUM","HARD");
    	 instance = SysData.getInstance();

    }
    @FXML
    /**
     * Handle Button Clicks
     * @param mouseEvent
     */
    private void handleButtonClicks(ActionEvent mouseEvent) {

        if (mouseEvent.getSource() == add_btn1) {
        	boolean flag = true;
        	if(difficulty_cb.getValue()==null || difficulty_cb.getValue().toString().isEmpty())
        	{
        		flag = false;
        		createAlert("Please enter Difficulty level");
        	}
        	else if (correctAns_cb.getValue()==null || correctAns_cb.getValue().toString().isEmpty()){
        		flag = false;
        		createAlert("Please enter which answer is the correct answer ");
        	}
        	else if (q_txt.getText()==null || q_txt.getText().toString().isEmpty()){
        		flag = false;
        		createAlert("Please enter the text of the question inside the text box ");
        	}
        	else if ((a_txt.getText()==null || a_txt.getText().isEmpty()) || (b_txt.getText()==null || b_txt.getText().isEmpty())){
        		flag = false;
        		createAlert("Please enter at least 2 answer ");
        	}

        	switch (Integer.parseInt(correctAns_cb.getValue().toString())) {
			case 3:
				if(c_txt.getText()==null || c_txt.getText().isEmpty()){
					flag = false;
					createAlert("Please choose correct answer between 1-2 ");
				}
				break;
			case 4:
				if(d_txt.getText()==null || d_txt.getText().isEmpty()){
					flag = false;
					createAlert("Please choose correct answer between 1-3 ");
				}
				break;
			default:
				break;
			}
        	if(flag){
        		instance.addQuestion(q_txt.getText().toString(),difficulty_cb.getValue().toString(),a_txt.getText(),b_txt.getText(),c_txt.getText(),d_txt.getText(),correctAns_cb.getValue().toString());
        		createAlert2("Question added successfully!", mouseEvent);
        	}

        }else if (mouseEvent.getSource() == back_btn) {
            Controller.loadStage("/view/manageQ.fxml");
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();

        }else if (mouseEvent.getSource() == cancel_btn) {
            Controller.createAlert();
        }
    }
    @FXML

    private void handleKeyBindings(ActionEvent mouseEvent) {


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
	public int getqID() {
		return qID;
	}
	public void setqID(int qID) {
		this.qID = qID;
	}
    /**
     * Create Alert  
     * @param str
     */
    public static void createAlert2(String str,ActionEvent mouseEvent ) {
  	  Alert alert = new Alert(Alert.AlertType.INFORMATION, str, ButtonType.OK);
      alert.setTitle("Information");
      alert.showAndWait();

      if (alert.getResult() == alert.getButtonTypes().get(0)) {

       	 ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
       	Controller.loadStage("/view/manageQ.fxml");
      }
    	  
    }

}
