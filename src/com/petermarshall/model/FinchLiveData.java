package com.petermarshall.model;

import com.petermarshall.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class FinchLiveData { //TODO decide if we can simplify this file by extending from LightInterfaceThread. Can do this and not worry about extending thread because we've made the class abstract so it can't be instantiated and thus run
    static SimpleBooleanProperty collectLiveData;
    static int updateSpeed;

    static SimpleStringProperty timeElapsed;
    static SimpleObjectProperty<FinchState> currentState;
    static SimpleObjectProperty<RawAndPecentage> currLeftVelStats;
    static SimpleObjectProperty<RawAndPecentage> currRightVelStats;
    static SimpleObjectProperty<RawAndPecentage> currLeftLightStats;
    static SimpleObjectProperty<RawAndPecentage> currRightLightStats;
    private static LightInterfaceThread searchForLightThread;

    public static void startProgram(int speed) {
        updateSpeed = speed;
        initVariables();
        startProgramInNewThread();
        startDataRetrievalInNewThread();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    }

    public static void changeUpdateSpeed(int speedInMs) {
        if (speedInMs > 0 && speedInMs < 5000) {
            updateSpeed = speedInMs;
        }
    }

    private static void startDataRetrievalInNewThread() {
        //made in a new thread so we keep program and UI as separate as possible. Don't want to rigidly build the program for this UI
        //when things can change in the future.
        //UI needs it's own thread. Program needs own thread. Data retrieval to bridge between the 2.

        //NOTE: DataRetrievalThread relies heavily on this file. Many variables with no access identifier are used in DataRetrievalThread
        DataRetrievalThread t = new DataRetrievalThread();
        t.start();
        t.setPriority(Thread.MIN_PRIORITY); //TODO: perhaps these priority vals should all be set together so it's clear what we're doing.
    }

    private static void startProgramInNewThread() {
        searchForLightThread = new LightInterfaceThread();
        searchForLightThread.start();
        searchForLightThread.setPriority(Thread.MAX_PRIORITY); //so our Finch is as responsive as possible. Without this line, we're getting periods of up to 2.5s of no thread time.
    }
    
    private static void initVariables() {
        collectLiveData = new SimpleBooleanProperty(true);
        timeElapsed = new SimpleStringProperty();
        currentState = new SimpleObjectProperty<>();
        currLeftVelStats = new SimpleObjectProperty<>();
        currRightVelStats = new SimpleObjectProperty<>();
        currLeftLightStats = new SimpleObjectProperty<>();
        currRightLightStats = new SimpleObjectProperty<>();
    }
    
    public static void stopProgram() {
        collectLiveData.set(false);
        if (searchForLightThread != null) {
            searchForLightThread.stopProgram();
        }
    }

    public static boolean isProgramRunning() {
        return collectLiveData.get();
    }

    public static SimpleBooleanProperty collectLiveDataProperty() {
        return collectLiveData;
    }

    public static SimpleStringProperty timeElapsedInNsProperty() {
        return timeElapsed;
    }

    public static SimpleObjectProperty<FinchState> currentStateProperty() {
        return currentState;
    }

    public static SimpleObjectProperty<RawAndPecentage> currLeftVelStatsProperty() {
        return currLeftVelStats;
    }

    public static SimpleObjectProperty<RawAndPecentage> currRightVelStatsProperty() {
        return currRightVelStats;
    }

    public static SimpleObjectProperty<RawAndPecentage> currLeftLightStatsProperty() {
        return currLeftLightStats;
    }

    public static SimpleObjectProperty<RawAndPecentage> currRightLightStatsProperty() {
        return currRightLightStats;
    }

    public static String getTimeElapsed() {
        return timeElapsed.get();
    }

    public static FinchState getCurrentState() {
        return currentState.get();
    }

    public static RawAndPecentage getCurrLeftVelStats() {
        return currLeftVelStats.get();
    }

    public static RawAndPecentage getCurrRightVelStats() {
        return currRightVelStats.get();
    }

    public static RawAndPecentage getCurrLeftLightStats() {
        return currLeftLightStats.get();
    }

    public static RawAndPecentage getCurrRightLightStats() {
        return currRightLightStats.get();
    }
}
