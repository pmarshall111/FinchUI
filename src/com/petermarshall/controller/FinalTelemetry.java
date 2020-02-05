package com.petermarshall.controller;

import com.petermarshall.SpeedLightStats;
import com.petermarshall.TimeInStates;
import com.petermarshall.model.FinchLiveData;
import com.petermarshall.model.SummaryData;
import com.petermarshall.model.helpers.DoubleFormat;
import com.petermarshall.model.helpers.TimeFormat;
import com.petermarshall.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class FinalTelemetry {

    @FXML
    private Label runNumber;
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
    private HBox leftMinBox;
    @FXML
    private HBox rightMinBox;
    @FXML
    private HBox leftMaxBox;
    @FXML
    private HBox rightMaxBox;
    @FXML
    private Label leftAvg;
    @FXML
    private Label rightAvg;
    @FXML
    private Label ovrAvg;
    @FXML
    private PieChart stateTimePie;
    @FXML
    private LineChart<Double, Integer> lightGraph;
    @FXML
    private Button closeBtn;

    private SummaryData endData;
    private final String highlightedVal = "highlightedVal";

    @FXML
    private void closeClicked() {
        int _id = Integer.parseInt(runNumber.getText());
        ViewManager.closeSearchPopup(_id);
    }

    @FXML
    private void initialize() {
        setVals();
    }

    private void setVals() {
        endData = new SummaryData();
        final int DECIMAL_PLACES = 1;

        runNumber.setText(ViewManager.getNumbSearchPopups()+"");
        runTime.setText(FinchLiveData.timeElapsedInNsProperty().get());
        newLightDetections.setText(endData.getNumbLightDetections()+"");
        leftStarting.setText(endData.getLeftLightAtStart()+"");
        rightStarting.setText(endData.getRightLightAtStart()+"");
        leftMaxVal.setText(endData.getHighestLeftLight()+"");
        rightMaxVal.setText(endData.getHighestRightLight()+"");
        leftMinVal.setText(endData.getLowestLeftLight()+"");
        rightMinVal.setText(endData.getLowestRightLight()+"");
        leftAvg.setText(endData.getAvgLeftLight(DECIMAL_PLACES));
        rightAvg.setText(endData.getAvgRightLight(DECIMAL_PLACES));
        ovrAvg.setText(endData.getAvgOvrLight(DECIMAL_PLACES));
        setPieChart();
        setLineChart();

        highlightFields();
    }

    private void highlightFields() {
        int leftMin = endData.getLowestLeftLight();
        int rightMin = endData.getLowestRightLight();
        int leftMax = endData.getHighestLeftLight();
        int rightMax = endData.getHighestRightLight();

        highlightMaxVal(leftMax, rightMax);
        highlightMinVal(leftMin, rightMin);
    }

    private void highlightMaxVal(double leftMax, double rightMax) {
        if (leftMax > rightMax) {
            leftMaxBox.getStyleClass().add(highlightedVal);
        } else {
            rightMaxBox.getStyleClass().add(highlightedVal);
        }
    }

    private void highlightMinVal(double leftMin, double rightMin) {
        if (leftMin < rightMin) {
            leftMinBox.getStyleClass().add(highlightedVal);
        } else {
            rightMinBox.getStyleClass().add(highlightedVal);
        }
    }

    private void setPieChart() {
        TimeInStates time = endData.getTimeInStates();

        long totalTime = time.getTotalRecordedTime();

        //order of these statements is important for matching css colours to Finch beak colours.
        stateTimePie.getData().add(new PieChart.Data("Waiting to be level", time.getWaitingTime()));
        stateTimePie.getData().add(new PieChart.Data("Searching", time.getSearchingTime()));
        stateTimePie.getData().add(new PieChart.Data("Following", time.getFollowingTime()));
        addTooltips(totalTime);
    }

    private void addTooltips(long totalTime) {
        stateTimePie.getData().forEach(pieSection -> {
            long timeNs = (long) pieSection.getPieValue();
            double percentage = 100 * pieSection.getPieValue() / totalTime;

            Tooltip tooltip = new Tooltip();
            String textToDisplay = pieSection.getName() + "\n" +
                                    "Time:       " + TimeFormat.getMinsSecsFromNanoSecs(timeNs) + "\n" +
                                    "Percentage: " + DoubleFormat.getToXDecimalPlaces(percentage, 1) + "%";
            tooltip.setText(textToDisplay);
            Tooltip.install(pieSection.getNode(), tooltip);
        });
    }

    private void setLineChart() {
        //TODO: can potentially create a new class for this sequence? Then we just call new LightChart(linechart, data).generateChart()
        ArrayList<SpeedLightStats> allStats = endData.getAllStats();
        XYChart.Series<Double, Integer> leftLight = new XYChart.Series<>();
        XYChart.Series<Double, Integer> rightLight = new XYChart.Series<>();
        XYChart.Series<Double, Integer> light = new XYChart.Series<>();
        long initialTimestamp = allStats.get(0).getTimestamp();
        for (int i = 0; i<allStats.size(); i++) {
            SpeedLightStats s = allStats.get(i);
            double seconds = getSecondsIntoRun(initialTimestamp, s.getTimestamp());
            leftLight.getData().add(new XYChart.Data<>(seconds, s.getLeftLightIntensity()));
            rightLight.getData().add(new XYChart.Data<>(seconds, s.getRightLightIntensity()));
            light.getData().add(new XYChart.Data<>(seconds, (s.getRightLightIntensity()+s.getLeftLightIntensity())/2));
        }
        lightGraph.getData().add(light);
    }

    private double getSecondsIntoRun(long initialTimestamp, long currTimestamp) {
        long diff = currTimestamp - initialTimestamp;
        double seconds = diff/Math.pow(10,9);
        return seconds;
    }
}
