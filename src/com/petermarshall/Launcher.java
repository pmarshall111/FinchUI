package com.petermarshall;

import com.petermarshall.view.ViewManager;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import javafx.application.Application;
import javafx.stage.Stage;

//The main class that starts the GUI application.
public class Launcher extends Application {
    public static Finch initialisedFinch;

    @Override
    public void start(Stage primaryStage) throws Exception{
        initialisedFinch = new Finch();
        System.out.println("initialised");
        ViewManager.init(primaryStage);
        ViewManager.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
