package view;

import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Constants;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Controller.class.getResource("/view/mainStyle.fxml"));
        primaryStage.setTitle("Pacman-Viper");
        primaryStage.setScene(new Scene(root, 1013.3, 570));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((ae) -> {
        	Controller.note.stop();
        	Platform.setImplicitExit(true);
            Platform.exit();
            System.exit(1);
        });
    }


    public static void main(String[] args) {
    	Platform.setImplicitExit(false);
    	System.setProperty("sun.java2d.opengl", "true");
		System.setProperty("sun.java2d.uiScale", Constants.SCALING_SIZE);
        launch(args);
    }

}