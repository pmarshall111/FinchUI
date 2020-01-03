package com.petermarshall;

import com.petermarshall.view.ViewManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ViewManager.init(primaryStage);
        ViewManager.showMainMenu();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
