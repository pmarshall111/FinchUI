package com.petermarshall.controller;

import com.petermarshall.main.RawAndPecentage;
import com.petermarshall.model.FinchLiveData;
import com.petermarshall.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

//The controller for view/searchForLightTask.fxml
public class SearchForLightTask {
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
    private HBox waitingCircleBox;
    @FXML
    private HBox searchCircleBox;
    @FXML
    private HBox followCircleBox;
    @FXML
    private Label waitingLabel;
    @FXML
    private Label searchLabel;
    @FXML
    private Label followLabel;
    private final String CIRCLE_STYLE = "selectedCircleBox";
    private final String LABEL_STYLE = "selectedLabel";

    @FXML
    void goToMainMenu() {
        endSearch();
        ViewManager.showMainMenu();
    }

    @FXML
    void startSearching() {
        stopBtn.setVisible(true);
        startBtn.setVisible(false);
        FinchLiveData.startProgram(100); //default val of 1s update time.
        bindData();
        setInitialVals();
    }

    private void setInitialVals() {
        waitingCircleBox.getStyleClass().add(CIRCLE_STYLE);
        waitingLabel.getStyleClass().add(LABEL_STYLE);
    }

    @FXML
    private void bindData() {
        timeElapsed.textProperty().bind(FinchLiveData.timeElapsedInNsProperty());
        bindState();
        bindLeftWheel();
        bindLeftLight();
        bindRightWheel();
        bindRightLight();
        bindProgramRunning();
    }

    private void bindState() {
        FinchLiveData.currentStateProperty().addListener((observable, oldValue, newValue) -> {
            removeStateHighlightBox();
            switch (newValue){
                case WAITING_TO_BE_LEVEL:
                    waitingCircleBox.getStyleClass().add(CIRCLE_STYLE);
                    waitingLabel.getStyleClass().add(LABEL_STYLE);
                    break;
                case FOLLOWING:
                    followCircleBox.getStyleClass().add(CIRCLE_STYLE);
                    followLabel.getStyleClass().add(LABEL_STYLE);
                    break;
                case SEARCH:
                    searchCircleBox.getStyleClass().add(CIRCLE_STYLE);
                    searchLabel.getStyleClass().add(LABEL_STYLE);
                    break;
            }
        });
    }

    private void bindLeftWheel() {
        FinchLiveData.currLeftVelStatsProperty().addListener((observable, oldValue, newValue) -> {
            updateVals(leftWheel, leftWheelBar, newValue);
        });
    }

    private void bindLeftLight() {
        FinchLiveData.currLeftLightStatsProperty().addListener((observable, oldValue, newValue) -> {
            updateVals(leftLight, leftLightBar, newValue);
        });
    }

    private void bindRightWheel() {
        FinchLiveData.currRightVelStatsProperty().addListener((observable, oldValue, newValue) -> {
            updateVals(rightWheel, rightWheelBar, newValue);
        });
    }

    private void bindRightLight() {
        FinchLiveData.currRightLightStatsProperty().addListener((observable, oldValue, newValue) -> {
            updateVals(rightLight, rightLightBar, newValue);
        });
    }

    private void bindProgramRunning() {
        FinchLiveData.collectLiveDataProperty().addListener((observable, oldValue, programRunning) -> {
            System.out.println("Program running: " + programRunning);
            if (!programRunning) {
                showProgramEnded();
            }
        });
    }

    @FXML
    private void removeStateHighlightBox() {
        waitingCircleBox.getStyleClass().remove(CIRCLE_STYLE);
        followCircleBox.getStyleClass().remove(CIRCLE_STYLE);
        searchCircleBox.getStyleClass().remove(CIRCLE_STYLE);
        waitingLabel.getStyleClass().remove(LABEL_STYLE);
        followLabel.getStyleClass().remove(LABEL_STYLE);
        searchLabel.getStyleClass().remove(LABEL_STYLE);
    }

    private void updateVals(Label label, ProgressBar progressBar, RawAndPecentage newValue) {
        if (rawSelected.isSelected()) {
            label.textProperty().setValue(newValue.getRaw()+"");
        } else {
            label.textProperty().setValue(newValue.getPosNegPercentage()+"%");
        }
        progressBar.setProgress((double)newValue.getPercentage()/100);
    }

    @FXML
    void endSearch() {
        try {
            if (FinchLiveData.isProgramRunning()) {
                FinchLiveData.stopProgram();
            }
        } catch (NullPointerException e) {
            //do nothing. means the program has not yet been started and FinchLiveData has not been initialised
        }
    }

    private void showProgramEnded() {
        startBtn.setVisible(true);
        stopBtn.setVisible(false);
        resetLiveData();
        if (showTelemetry.isSelected()) {
            ViewManager.showFinalTelemetry();
        }
    }

    private void resetLiveData() {
        removeStateHighlightBox();
        setValsToDefault();
    }

    private void setValsToDefault() {
        unbindData();
        timeElapsed.setText("0m 0s");
        leftLight.setText("-1");
        rightLight.setText("-1");
        leftWheel.setText("-1");
        rightWheel.setText("-1");
        rightLightBar.setProgress(-1);
        leftLightBar.setProgress(-1);
        rightWheelBar.setProgress(-1);
        leftWheelBar.setProgress(-1);
    }

    private void unbindData() {
        timeElapsed.textProperty().unbind();
        leftLight.textProperty().unbind();
        rightLight.textProperty().unbind();
        leftWheel.textProperty().unbind();
        rightWheel.textProperty().unbind();
    }

}
