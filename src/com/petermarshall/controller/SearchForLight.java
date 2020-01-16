package com.petermarshall.controller;

import com.petermarshall.LightInterfaceThread;
import com.petermarshall.RawAndPecentage;
import com.petermarshall.model.FinchLiveData;
import com.petermarshall.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

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

    @FXML
    private ToggleButton rawSelected;

    @FXML
    private HBox waitingBox;

    @FXML
    private HBox searchBox;

    @FXML
    private HBox followBox;




    private final String CIRCLE_STYLE = "selectedCircle";
    private final String LABEL_STYLE = "selectedLabel";
    @FXML
    private Circle waitingCircle;
    @FXML
    private Circle searchCircle;
    @FXML
    private Circle followCircle;
    @FXML
    private Label waitingLabel;
    @FXML
    private Label searchLabel;
    @FXML
    private Label followLabel;

    private LightInterfaceThread programThread;

    private int NUM_DECIMAL_PLACES = 0;

    @FXML
    void goToMainMenu() {
        if (FinchLiveData.isProgramRunning()) {
            endSearch();
        }
        ViewManager.showMainMenu();
    }

    @FXML
    void startSearching() {
        stopBtn.setVisible(true);
        startBtn.setVisible(false);

        FinchLiveData.startProgram(1000); //default val of 1s update time.
        bindData();
        setInitialVals();

//        programThread = new LightInterfaceThread();
//        programThread.start();
    }

    private void setInitialVals() {
        waitingCircle.getStyleClass().add(CIRCLE_STYLE);
        waitingLabel.getStyleClass().add(LABEL_STYLE);
    }

    @FXML
    private void bindData() {
        timeElapsed.textProperty().bind(FinchLiveData.timeElapsedInNsProperty()); //TODO: since we're moving display logic to here we should put the time func in here too.
        //need to figure out how to bind based on a flag.


        FinchLiveData.currentStateProperty().addListener((observable, oldValue, newValue) -> {
//            String style = "selectedBorder";
//            waitingBox.getStyleClass().remove(style);
//            followBox.getStyleClass().remove(style);
//            searchBox.getStyleClass().remove(style);
//
//            switch (newValue){
//                case WAITING_TO_BE_LEVEL:
//                    waitingBox.getStyleClass().add(style);
//                    break;
//                case FOLLOWING:
//                    followBox.getStyleClass().add(style);
//                    break;
//                case SEARCH:
//                    searchBox.getStyleClass().add(style);
//            }

            waitingCircle.getStyleClass().remove(CIRCLE_STYLE);
            followCircle.getStyleClass().remove(CIRCLE_STYLE);
            searchCircle.getStyleClass().remove(CIRCLE_STYLE);
            waitingLabel.getStyleClass().remove(LABEL_STYLE);
            followLabel.getStyleClass().remove(LABEL_STYLE);
            searchLabel.getStyleClass().remove(LABEL_STYLE);

            switch (newValue){
                case WAITING_TO_BE_LEVEL:
                    waitingCircle.getStyleClass().add(CIRCLE_STYLE);
                    waitingLabel.getStyleClass().add(LABEL_STYLE);
                    break;
                case FOLLOWING:
                    followCircle.getStyleClass().add(CIRCLE_STYLE);
                    followLabel.getStyleClass().add(LABEL_STYLE);
                    break;
                case SEARCH:
                    searchCircle.getStyleClass().add(CIRCLE_STYLE);
                    searchLabel.getStyleClass().add(LABEL_STYLE);
                    break;
            }
        });

        FinchLiveData.currLeftVelStatsProperty().addListener((observable, oldValue, newValue) -> {
            updateVals(leftWheel, leftWheelBar, newValue);
        });

        FinchLiveData.currLeftLightStatsProperty().addListener((observable, oldValue, newValue) -> {
            updateVals(leftLight, leftLightBar, newValue);
        });

        FinchLiveData.currRightVelStatsProperty().addListener((observable, oldValue, newValue) -> {
            updateVals(rightWheel, rightWheelBar, newValue);
        });

        FinchLiveData.currRightLightStatsProperty().addListener((observable, oldValue, newValue) -> {
            updateVals(rightLight, rightLightBar, newValue);
        });

        rawSelected.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                leftLight.textProperty().setValue(FinchLiveData.getCurrLeftLightStats().getRaw() + "");
                leftWheel.textProperty().setValue(FinchLiveData.getCurrLeftVelStats().getRaw() + "");
                rightLight.textProperty().setValue(FinchLiveData.getCurrRightLightStats().getRaw() + "");
                rightWheel.textProperty().setValue(FinchLiveData.getCurrRightVelStats().getRaw() + "");
            } else {
                leftLight.textProperty().setValue(FinchLiveData.getCurrLeftLightStats().getPercentage() + "%");
                leftWheel.textProperty().setValue(FinchLiveData.getCurrLeftVelStats().getPercentage() + "%");
                rightLight.textProperty().setValue(FinchLiveData.getCurrRightLightStats().getPercentage() + "%");
                rightWheel.textProperty().setValue(FinchLiveData.getCurrRightVelStats().getPercentage() + "%");
            }
        });
    }

    private void updateVals(Label label, ProgressBar progressBar, RawAndPecentage newValue) {
        if (rawSelected.isSelected()) {
            label.textProperty().setValue(newValue.getRaw()+"");
        } else {
            label.textProperty().setValue(newValue.getPercentage()+"%");
        }
        progressBar.setProgress((double)newValue.getPercentage()/100);
    }

    @FXML
    void endSearch() {
        startBtn.setVisible(true);
        stopBtn.setVisible(false);

        FinchLiveData.stopProgram();
        if (showTelemetry.isSelected()) {
            ViewManager.showFinalTelemetry();
        }
    }




    //need to implement finch state & telemetry.


}
