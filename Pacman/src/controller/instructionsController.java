package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class instructionsController implements Initializable {

    @FXML
    private Button back_btn;
    @FXML
    private Button cancel_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

/**
 * handles button clicks to open windows
 */
    }
    @FXML
    private void handleButtonClicks(ActionEvent mouseEvent) {


        if (mouseEvent.getSource() == back_btn) {
            Controller.loadStage("/view/mainStyle.fxml");
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        }else if (mouseEvent.getSource() == cancel_btn) {
            Controller.createAlert();
        }
    }

}
