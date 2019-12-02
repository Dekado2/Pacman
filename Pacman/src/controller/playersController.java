package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Leaderboard;
import model.PacmanPlayer;
import model.Question;
import model.SysData;
import model.Timer;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;




public class playersController implements Initializable {


    @FXML
    private TableView<Leaderboard> leaderboard_tbl;
 
    
    @FXML
    public TableColumn<Leaderboard,String> nickname;

    @FXML
    public TableColumn<Leaderboard, Integer> score;

    @FXML
    public TableColumn<Leaderboard, Integer> level;
    @FXML
    public TableColumn<Leaderboard, String> gameDuration;
    @FXML
    public TableColumn<Leaderboard, String> gameDate;
    @FXML
    private Button back_btn;
    @FXML
    private Button cancel_btn;
    private SysData instance;


    @Override
    /**
     * Initialize the leaderboard window
     */
    public void initialize(URL location, ResourceBundle resources) {
    	gameDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    	nickname.setCellValueFactory(cellData -> 
          	new SimpleStringProperty(cellData.getValue().getPlayer().getNickName()));
    	score.setCellValueFactory(cellData -> 
    		new SimpleIntegerProperty(cellData.getValue().getPlayer().getPoints()).asObject());
    	level.setCellValueFactory(cellData -> 
			new SimpleIntegerProperty(cellData.getValue().getPlayer().getLevel()).asObject());
    	 gameDuration.setCellValueFactory(cellData -> 
		new SimpleStringProperty(cellData.getValue().getPlayer().getGameLength().formatInterval()));
        leaderboard_tbl.setItems(LeaderboardModels);
        instance = SysData.getInstance();
    }
    
    
    private ObservableList<Leaderboard> LeaderboardModels =  FXCollections.observableArrayList(SysData.getInstance().getLeaderboardList());
    
    @FXML
    /**
     * Handle Button Clicks
     * @param mouseEvent
     */
    private void handleButtonClicks(ActionEvent mouseEvent) {

        if (mouseEvent.getSource() == back_btn) {
            Controller.loadStage("/view/mainStyle.fxml");
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();

        } else if (mouseEvent.getSource() == cancel_btn) {
           Controller.createAlert();
        }
    }




}
