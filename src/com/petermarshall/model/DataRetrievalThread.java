package com.petermarshall.model;

import com.petermarshall.main.LightInterfaceThread;
import com.petermarshall.model.helpers.TimeFormat;
import javafx.application.Platform;

//Class's sole purpose is to update values in FinchLiveData.
import static com.petermarshall.model.FinchLiveData.*;

public class DataRetrievalThread extends Thread {
    @Override
    public void run() {
        while (FinchLiveData.isCollectingLiveData()) {
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
                Thread.sleep(FinchLiveData.getUpdateSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            checkFinchIsStillRunning();
        }
    }

    private void checkFinchIsStillRunning() {
        //User can stop the program by placing the Finch upwards. So we need to update the flag from both the UI and the program.
        if (!FinchLiveData.getSearchForLightThread().isRunning()) {
            Platform.runLater(() -> {
                try {
                    FinchLiveData.collectLiveDataProperty().setValue(false);
                } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void setState() {
        Platform.runLater(() -> {
            try {
                FinchLiveData.currentStateProperty().set(FinchLiveData.getSearchForLightThread().getCurrentFinchState());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                //do nothing. Means we've tried to get state before there are any records
            }
        });
    }

    private void setLeftVel() {
        Platform.runLater(() -> {
            try {
                FinchLiveData.currLeftVelStatsProperty().set(FinchLiveData.getSearchForLightThread().getLatestLeftVelStats());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                //do nothing. Means we've tried to get state before there are any records
            }
        });
    }

    private void setRightVel() {
        Platform.runLater(() -> {
            try {
                FinchLiveData.currRightVelStatsProperty().set(FinchLiveData.getSearchForLightThread().getLatestRightVelStats());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        });
    }

    private void setLeftLight() {
        Platform.runLater(() -> {
            try {
                FinchLiveData.currLeftLightStatsProperty().set(FinchLiveData.getSearchForLightThread().getLatestLeftLightStats());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        });
    }

    private void setRightLight() {
        Platform.runLater(() -> {
            try {
                FinchLiveData.currRightLightStatsProperty().set(FinchLiveData.getSearchForLightThread().getLatestRightLightStats());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        });
    }

    private void setTime() {
        Platform.runLater(() -> {
            try {
                FinchLiveData.timeElapsedInNsProperty().set(
                        TimeFormat.getMinsSecsFromNanoSecs(FinchLiveData.getSearchForLightThread().getTimeElapsedInNS())
                );
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        });
    }
}
