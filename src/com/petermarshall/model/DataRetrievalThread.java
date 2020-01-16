package com.petermarshall.model;

import com.petermarshall.LightInterfaceThread;
import com.petermarshall.model.helpers.TimeFormat;
import javafx.application.Platform;

//Class relies heavily on variables in FinchLiveData. Variables changed there affect this class.
//However, this class is only designed for use with that class. Originally part of FinchLiveData, this class was made to make the class shorter
//TODO: is this the best way of doing things???
import static com.petermarshall.model.FinchLiveData.*;

public class DataRetrievalThread extends Thread {
    @Override
    public void run() {
        while (collectLiveData.get()) {
            //thinking that we may only need this thread to start the program.
            //would need access to the Finch. Pretty easy though as we just initialise the new Finch.
            //This model would need to be passed into the controller, which could then control a flag to stop or start the program.
            setState();
            setLeftVel();
            setRightVel();
            setLeftLight();
            setRightLight();
            setTime();

            try {
                Thread.sleep(updateSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            checkFinchIsStillRunning();
        }
    }

    private void checkFinchIsStillRunning() {
        //User can stop the program by placing the Finch upwards. So we need to update the flag from both the UI and the program.
        if (!LightInterfaceThread.isRunning()) {
            Platform.runLater(() -> {
                try {
                    collectLiveData.setValue(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void setState() {
        Platform.runLater(() -> {
            try {
                currentState.set(LightInterfaceThread.getCurrentFinchState());
            } catch (NullPointerException e) {
                //do nothing. Means we've tried to get state before there are any records
            }
        });
    }

    private void setLeftVel() {
        Platform.runLater(() -> {
            try {
                currLeftVelStats.set(LightInterfaceThread.getLatestLeftVelStats());
            } catch (NullPointerException e) {
                //do nothing. Means we've tried to get state before there are any records
            }
        });
    }

    private void setRightVel() {
        Platform.runLater(() -> {
            try {
                currRightVelStats.set(LightInterfaceThread.getLatestRightVelStats());
            } catch (NullPointerException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void setLeftLight() {
        Platform.runLater(() -> {
            try {
                currLeftLightStats.set(LightInterfaceThread.getLatestLeftLightStats());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    private void setRightLight() {
        Platform.runLater(() -> {
            try {
                currRightLightStats.set(LightInterfaceThread.getLatestRightLightStats());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }

    private void setTime() {
        Platform.runLater(() -> {
            try {
                timeElapsed.set(
                        TimeFormat.getMinsSecsFromNanoSecs(LightInterfaceThread.getTimeElapsedInNS())
                );
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        });
    }
}
