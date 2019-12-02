package controller;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;

import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;



import javafx.util.StringConverter;
import utils.Constants;
public class optionsController implements Initializable {

@FXML
private Button off_btn;
@FXML
private Slider scalingSlider;
@FXML
private Slider speedSlider;
    @FXML
    private Button on_btn;
    @FXML
    private Button back_btn;
    @FXML
    private Button cancel_btn;
    /**
     * set the scaling and speed sliders
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    	scalingSlider.setMin(0);
    	scalingSlider.setMax(5);
    	scalingSlider.setValue(0);
    	scalingSlider.setShowTickLabels(true);
    	scalingSlider.setShowTickMarks(true);
    	scalingSlider.setMajorTickUnit(1);
    	scalingSlider.setMinorTickCount(0);
    	scalingSlider.setBlockIncrement(1);
    	scalingSlider.setSnapToTicks(true);
    	
    	scalingSlider.setLabelFormatter(new StringConverter<Double>() {

			@Override
			public Double fromString(String s) {
				switch (s) {
                case "1.0":
                    return 0d;
                case "1.1":
                    return 1d;
                case "1.2":
                    return 2d;
                case "1.3":
                    return 3d;
                case "1.4":
                    return 4d;
                case "1.5":
                    return 5d;
                default:
                    return 0d;
              }
			}

			@Override
			public String toString(Double n) {
				 if (n < 1) return "1.0";
	                if (n >= 1 && n<2) return "1.1";
	                if (n >=2 && n<3) return "1.2";
	                if (n >=3 && n<4) return "1.3";
	                if (n >=4 && n<5) return "1.4";
	                if (n >=5) return "1.5";
	                return "error";
			}
    		
    	});
    	
    	if (Constants.SCALING_SIZE=="1.0")
        	scalingSlider.setValue(0);
        	else if (Constants.SCALING_SIZE=="1.1")
            	scalingSlider.setValue(1);
        	else if (Constants.SCALING_SIZE=="1.2")
            	scalingSlider.setValue(2);
        	else if (Constants.SCALING_SIZE=="1.3")
            	scalingSlider.setValue(3);
        	else if (Constants.SCALING_SIZE=="1.4")
            	scalingSlider.setValue(4);
        	else if (Constants.SCALING_SIZE=="1.5")
            	scalingSlider.setValue(5);
    	
    	scalingSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				 if (new_val.equals(0.0)) {
					Constants.SCALING_SIZE = "1.0";
					System.setProperty("sun.java2d.uiScale", Constants.SCALING_SIZE);
					createAlert("Board Resolution Scaling has been changed to " + Constants.SCALING_SIZE + "!");
				}
				else if (new_val.equals(1.0)) {
					Constants.SCALING_SIZE = "1.1";
					System.setProperty("sun.java2d.uiScale", Constants.SCALING_SIZE);
					createAlert("Board Resolution Scaling has been changed to " + Constants.SCALING_SIZE + "!");
				}
				else if (new_val.equals(2.0)) {
					Constants.SCALING_SIZE = "1.2";
					System.setProperty("sun.java2d.uiScale", Constants.SCALING_SIZE);
					createAlert("Board Resolution Scaling has been changed to " + Constants.SCALING_SIZE + "!");
				}
				else if (new_val.equals(3.0)) {
					Constants.SCALING_SIZE = "1.3";
					System.setProperty("sun.java2d.uiScale", Constants.SCALING_SIZE);
					createAlert("Board Resolution Scaling has been changed to " + Constants.SCALING_SIZE + "!");
				}
				else if (new_val.equals(4.0)) {
					Constants.SCALING_SIZE = "1.4";
					System.setProperty("sun.java2d.uiScale", Constants.SCALING_SIZE);
					createAlert("Board Resolution Scaling has been changed to " + Constants.SCALING_SIZE + "!");
				}
				else if (new_val.equals(5.0)) {
					Constants.SCALING_SIZE = "1.5";
					System.setProperty("sun.java2d.uiScale", Constants.SCALING_SIZE);
					createAlert("Board Resolution Scaling has been changed to " + Constants.SCALING_SIZE + "!");
				}
				
			}
        });
    	
    	speedSlider.setMin(0);
    	speedSlider.setMax(2);
    	speedSlider.setValue(0);
    	speedSlider.setShowTickLabels(true);
    	speedSlider.setShowTickMarks(true);
    	speedSlider.setMajorTickUnit(1);
    	speedSlider.setMinorTickCount(0);
    	speedSlider.setBlockIncrement(1);
    	speedSlider.setSnapToTicks(true);
    	
    	speedSlider.setLabelFormatter(new StringConverter<Double>() {

			@Override
			public Double fromString(String s) {
				switch (s) {
                case "Slower":
                    return 0d;
                case "Normal":
                    return 1d;
                case "Faster":
                    return 2d;
                default:
                    return 1d;
              }
			}

			@Override
			public String toString(Double n) {
				if (n < 1) return "Slower";
                if (n >= 1 && n<2) return "Normal";
                if (n >=2) return "Faster";
                return "error";
			}		
    	});
    	
    	   if (Constants.GAME_SPEED==23)
		        speedSlider.setValue(0);
    	   else if (Constants.GAME_SPEED==12)
    		    speedSlider.setValue(1);
        	else if (Constants.GAME_SPEED==1)
        		speedSlider.setValue(2);
    	
    	speedSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				 if (new_val.equals(1.0)) {
					Constants.GAME_SPEED = 12;
					createAlert("Game speed has been changed to normal mode!");
				}
				else if (new_val.equals(0.0)) {
					Constants.GAME_SPEED = 23;
					createAlert("Game speed has been changed to slower mode!");
				}	
				else if (new_val.equals(2.0)) {
					Constants.GAME_SPEED = 1;
					createAlert("Game speed has been changed to faster mode!");
				}	
			}
        });
    	
    	
    }
    

    @FXML
    /**
     * Handle Button Clicks
     * @param mouseEvent
     */
    private void handleButtonClicks(ActionEvent mouseEvent) {

        if (mouseEvent.getSource() == on_btn) {

            on_btn.setGraphic(new ImageView("/images/speakerOn2.png"));
            off_btn.setGraphic(new ImageView("/images/speakerOff.png"));
            if (!Controller.note.isPlaying())
            Controller.note.play();
        } else if (mouseEvent.getSource() == off_btn) {
            off_btn.setGraphic(new ImageView("/images/speakerOff2.png"));
            on_btn.setGraphic(new ImageView("/images/speakerOn.png"));
            Controller.note.stop();
        }else if (mouseEvent.getSource() == back_btn) {
            Controller.loadStage("/view/mainStyle.fxml");
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();

        }else if (mouseEvent.getSource() == cancel_btn) {
            Controller.createAlert();
        }
    }
    
    /**
     * Create Alert  
     * @param str
     */
    public static void createAlert(String str) {
  	  Alert alert = new Alert(Alert.AlertType.INFORMATION, str, ButtonType.OK);
      alert.setTitle("Information");
      alert.showAndWait();
    	  
    }



}
