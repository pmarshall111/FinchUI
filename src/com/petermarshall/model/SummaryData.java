package com.petermarshall.model;

import com.petermarshall.LightInterfaceThread;
import com.petermarshall.SpeedLightStats;
import com.petermarshall.TimeInStates;

import java.util.ArrayList;

//thinking behind making this an instanciable class is that the live data can only have 1 thing, but there can be many runs and therefore many summaries.
//... not that it matters too much.
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
        leftLightAtStart = LightInterfaceThread.getLeftLightAtStart();
        rightLightAtStart = LightInterfaceThread.getRightLightAtStart();
        highestLeftLight = LightInterfaceThread.getHighestLeftLightReading();
        highestRightLight = LightInterfaceThread.getHighestRightLightReading();
        lowestLeftLight = LightInterfaceThread.getLowestLeftLightReading();
        lowestRightLight = LightInterfaceThread.getLowestRightLightReading();
        avgLeftLight = LightInterfaceThread.getAverageLeftLightSensorReading();
        avgRightLight = LightInterfaceThread.getAverageRightLightSensorReading();
        avgOvrLight = LightInterfaceThread.getAverageLightSensorReading();
        numbLightDetections = LightInterfaceThread.getNumbLightDetections();
        timeInStates = LightInterfaceThread.getTimeInEachState();
        allStats = LightInterfaceThread.getStats();
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
