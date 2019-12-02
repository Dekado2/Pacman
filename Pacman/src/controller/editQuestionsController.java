package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.Question;
import model.SysData;

import java.net.URL;
import java.util.ResourceBundle;

public class editQuestionsController implements Initializable {

    @FXML
    private Button edit_btn;
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

    private Question q;
    private TableView<Question> managQ_tbl;

	public void setManagQ_tbl(TableView<Question> managQ_tbl) {
		this.managQ_tbl = managQ_tbl;
	}
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
        correctAns_cb.getItems().addAll("1","2","3","4");
        difficulty_cb.getItems().addAll("EASY","MEDIUM","HARD");
   
        instance = SysData.getInstance();
    
    }
	
	/**
	 * handles clicks on different buttons
	 * @param mouseEvent
	 */
    @FXML
    private void handleButtonClicks(ActionEvent mouseEvent) {

        if (mouseEvent.getSource() == edit_btn) {
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
        	instance.editQuestion(q.getQID(),q_txt.getText().toString(),difficulty_cb.getValue().toString(),a_txt.getText(),b_txt.getText(),c_txt.getText(),d_txt.getText(),correctAns_cb.getValue().toString()); 
   
      		instance.removeQuestion(q.getQID());
      		managQ_tbl.getItems().remove(q);
      		createAlert2("Question updated successfully!", mouseEvent);
      	
        }
        	
        

        }else if (mouseEvent.getSource() == back_btn) {
            Controller.loadStage("/view/manageQ.fxml");
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();

        }else if (mouseEvent.getSource() == cancel_btn) {
            Controller.createAlert();
            
        }
        
    }
    public void setDifficulty_cb(String difficulty) {
		this.difficulty_cb.setValue(difficulty);
	}
	public void setCorrectAns_cb(String correctAns_cb) {
		this.correctAns_cb.setValue(correctAns_cb);
	}
	public void setA_txt(String a_txt) {
		this.a_txt.setText(a_txt);
	}
	public void setB_txt(String b_txt) {
		this.b_txt.setText(b_txt);
	}
	public void setC_txt(String c_txt) {
		this.c_txt.setText(c_txt);
	}
	public void setD_txt(String d_txt) {
		this.d_txt.setText(d_txt);
	}
	public void setQ_txt(String q_txt) {
		this.q_txt.setText(q_txt);
	}
	

	public ComboBox<String> getDifficulty_cb() {
		return difficulty_cb;
	}
	public ComboBox<String> getCorrectAns_cb() {
		return correctAns_cb;
	}
	public TextField getA_txt() {
		return a_txt;
	}
	public TextField getB_txt() {
		return b_txt;
	}
	public TextField getC_txt() {
		return c_txt;
	}
	public TextField getD_txt() {
		return d_txt;
	}
	public TextArea getQ_txt() {
		return q_txt;
	}


	public Question getQ() {
		return q;
	}
	public void setQ(Question q) {
		this.q = q;
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
    /**
     * Create Alert after updating question
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
