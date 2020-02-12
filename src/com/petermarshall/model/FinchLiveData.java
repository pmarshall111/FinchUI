package com.petermarshall.model;

import com.petermarshall.*;
import com.petermarshall.main.FinchState;
import com.petermarshall.main.LightInterfaceThread;
import com.petermarshall.main.RawAndPecentage;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class FinchLiveData { //TODO decide if we can simplify this file by extending from LightInterfaceThread. Can do this and not worry about extending thread because we've made the class abstract so it can't be instantiated and thus run
    private static SimpleBooleanProperty collectLiveData;
    private static int updateSpeed;

    private static SimpleStringProperty timeElapsed;
    private static SimpleObjectProperty<FinchState> currentState;
    private static SimpleObjectProperty<RawAndPecentage> currLeftVelStats;
    private static SimpleObjectProperty<RawAndPecentage> currRightVelStats;
    private static SimpleObjectProperty<RawAndPecentage> currLeftLightStats;
    private static SimpleObjectProperty<RawAndPecentage> currRightLightStats;
    private static LightInterfaceThread searchForLightThread;

    public static void startProgram(int speed) {
        updateSpeed = speed;
        initVariables();
        startProgramInNewThread();
        startDataRetrievalInNewThread();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    }

    private static void startDataRetrievalInNewThread() {
        //made in a new thread so we keep program and UI as separate as possible. Don't want to rigidly build the program for this UI
        //when things can change in the future.
        //UI needs it's own thread. Program needs own thread. Data retrieval to bridge between the 2.

        //NOTE: DataRetrievalThread is highly coupled to this file
        DataRetrievalThread t = new DataRetrievalThread();
        t.start();
    }

    private static void startProgramInNewThread() {
        searchForLightThread = new LightInterfaceThread(Launcher.initialisedFinch);
        searchForLightThread.start();
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

    static LightInterfaceThread getSearchForLightThread() {
        return searchForLightThread;
    }

    public static boolean isCollectingLiveData() {
        return collectLiveData.get();
    }

    public static int getUpdateSpeed() {
        return updateSpeed;
    }
}
