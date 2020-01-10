package com.petermarshall.controller;

import com.petermarshall.LightInterfaceThread;
import com.petermarshall.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class SearchForLight {

    @FXML
    private Button backBtn;

    @FXML
    private Button stopBtn;

    @FXML
    private Button startBtn;

    @FXML
    private CheckBox showTelemetry;

    @FXML
    private Label timeElapsed;

    @FXML
    private Label leftLight;

    @FXML
    private ProgressBar leftLightBar;

    @FXML
    private Label rightLight;

    @FXML
    private ProgressBar rightLightBar;

    @FXML
    private Label leftWheel;

    @FXML
    private ProgressBar leftWheelBar;

    @FXML
    private Label rightWheel;

    @FXML
    private ProgressBar rightWheelBar;

    private LightInterfaceThread programThread;

    @FXML
    void goToMainMenu() {
        ViewManager.showMainMenu();
    }

    @FXML
    void startSearching() {
        stopBtn.setVisible(true);
        startBtn.setVisible(false);

        programThread = new LightInterfaceThread();
        programThread.start();

//        LightInterfaceThread.startSearch();
    }

    @FXML
    void endSearch() {
        startBtn.setVisible(true);
        stopBtn.setVisible(false);

        programThread.stopProgram();
//        try {
//            programThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }




    //need to implement finch state & telemetry.


}
