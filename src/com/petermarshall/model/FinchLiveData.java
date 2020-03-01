package com.petermarshall.model;

import com.petermarshall.*;
import com.petermarshall.main.FinchState;
import com.petermarshall.main.LightInterfaceThread;
import com.petermarshall.main.RawAndPecentage;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

//This is the interface class that the controller can call to communicate with the task interface. Class creates 2 threads.
//The DataRetrieval thread checks for changed values, as the current thread is needed by the GUI to listen for events.
//The program is also created in a new thread and not in the DataRetrieval thread so as to keep the GUI and the
//task logic as separate as possible.
public class FinchLiveData {
    private static SimpleBooleanProperty collectLiveData;
    private static int updateSpeed;
    private static SimpleStringProperty timeElapsed;
    private static SimpleObjectProperty<FinchState> currentState;
    private static SimpleObjectProperty<RawAndPecentage> currLeftVelStats;
    private static SimpleObjectProperty<RawAndPecentage> currRightVelStats;
    private static SimpleObjectProperty<RawAndPecentage> currLeftLightStats;
    private static SimpleObjectProperty<RawAndPecentage> currRightLightStats;
    private static LightInterfaceThread searchForLightThread;

    //private constructor as we do not want this class to be instantiated.
    private FinchLiveData() {}

    public static void startProgram(int speed) {
        updateSpeed = speed;
        initVariables();
        startProgramInNewThread();
        startDataRetrievalInNewThread();
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    }

    private static void startDataRetrievalInNewThread() {
        DataRetrievalThread drt = new DataRetrievalThread();
        drt.start();
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
        if (collectLiveData != null) {
            collectLiveData.set(false);
        }
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
