package com.petermarshall;

import com.petermarshall.view.ViewManager;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        passFinchToSubroutines();
//
        ViewManager.init(primaryStage);
        ViewManager.showMainMenu();

//        ViewManager.init(primaryStage);
//        ViewManager.showSearchForLight();
    }

    private void passFinchToSubroutines() {
        Finch sharedFinch = new Finch();

        LightInterfaceThread.init(sharedFinch);
    }




    public static void main(String[] args) {
        launch(args);
    }
}
