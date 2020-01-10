package com.petermarshall.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FinalTelemetry {

    @FXML
    private Label runTime;
    @FXML
    private Label newLightDetections;
    @FXML
    private Label leftStarting;
    @FXML
    private Label rightStarting;
    @FXML
    private Label leftMaxVal;
    @FXML
    private Label rightMaxVal;
    @FXML
    private Label leftMinVal;
    @FXML
    private Label rightMinVal;
    @FXML
    private Label leftAvg;
    @FXML
    private Label rightAvg;
    @FXML
    private Label ovrAvg;
    @FXML
    private PieChart stateTimePi;
    @FXML
    private LineChart lightGraph;
    @FXML
    private Button closeBtn;

    @FXML
    private void closeClicked() {

    }
}
