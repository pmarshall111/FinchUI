package com.petermarshall.model;

import com.petermarshall.main.LightInterfaceThread;
import com.petermarshall.main.SpeedLightStats;
import com.petermarshall.main.TimeInStates;

import java.util.ArrayList;

//Class is created at the end of a run to contain all wanted pieces of data.
public class SummaryData {
    private int leftLightAtStart;
    private int rightLightAtStart;
    private int highestLeftLight;
    private int highestRightLight;
    private int lowestLeftLight;
    private int lowestRightLight;
    private double avgLeftLight;
    private double avgRightLight;
    private double avgOvrLight;
    private int numbLightDetections;
    private TimeInStates timeInStates;
    private ArrayList<SpeedLightStats> allStats;

    public SummaryData() {
        LightInterfaceThread lightTask = FinchLiveData.getSearchForLightThread();

        leftLightAtStart = lightTask.getLeftLightAtStart();
        rightLightAtStart = lightTask.getRightLightAtStart();
        highestLeftLight = lightTask.getHighestLeftLightReading();
        highestRightLight = lightTask.getHighestRightLightReading();
        lowestLeftLight = lightTask.getLowestLeftLightReading();
        lowestRightLight = lightTask.getLowestRightLightReading();
        avgLeftLight = lightTask.getAverageLeftLightSensorReading();
        avgRightLight = lightTask.getAverageRightLightSensorReading();
        avgOvrLight = lightTask.getAverageLightSensorReading();
        numbLightDetections = lightTask.getNumbLightDetections();
        timeInStates = lightTask.getTimeInEachState();
        allStats = lightTask.getStats();
    }

    public ArrayList<SpeedLightStats> getAllStats() {
        return allStats;
    }

    public int getLeftLightAtStart() {
        return leftLightAtStart;
    }

    public int getRightLightAtStart() {
        return rightLightAtStart;
    }

    public int getHighestLeftLight() {
        return highestLeftLight;
    }

    public int getHighestRightLight() {
        return highestRightLight;
    }

    public int getLowestLeftLight() {
        return lowestLeftLight;
    }

    public int getLowestRightLight() {
        return lowestRightLight;
    }

    public String getAvgLeftLight(int decimalPlaces) {
        return formatDouble(avgLeftLight, decimalPlaces);
    }

    public String getAvgRightLight(int decimalPlaces) {
        return formatDouble(avgRightLight, decimalPlaces);
    }

    public String getAvgOvrLight(int decimalPlaces) {
        return formatDouble(avgOvrLight, decimalPlaces);
    }

    public int getNumbLightDetections() {
        return numbLightDetections;
    }

    public TimeInStates getTimeInStates() {
        return timeInStates;
    }

    private String formatDouble(double d, int decimalPlaces) {
        return String.format("%." + decimalPlaces + "f", d);
    }
}
