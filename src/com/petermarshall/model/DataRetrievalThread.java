package com.petermarshall.model;

import com.petermarshall.model.helpers.TimeFormat;
import javafx.application.Platform;

//Class's purpose is to update beans that update on the GUI when a change occurs.
public class DataRetrievalThread extends Thread {
    @Override
    public void run() {
        while (FinchLiveData.isCollectingLiveData()) {
            setState();
            setLeftVel();
            setRightVel();
            setLeftLight();
            setRightLight();
            setTime();
            sleep(FinchLiveData.getUpdateSpeed());
            checkFinchIsStillRunning();
        }
    }

    private void checkFinchIsStillRunning() {
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
                //do nothing. Means we've tried to get state before there are any records
            }
        });
    }

    private void setLeftLight() {
        Platform.runLater(() -> {
            try {
                FinchLiveData.currLeftLightStatsProperty().set(FinchLiveData.getSearchForLightThread().getLatestLeftLightStats());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                //do nothing. Means we've tried to get state before there are any records
            }
        });
    }

    private void setRightLight() {
        Platform.runLater(() -> {
            try {
                FinchLiveData.currRightLightStatsProperty().set(FinchLiveData.getSearchForLightThread().getLatestRightLightStats());
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                //do nothing. Means we've tried to get state before there are any records
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
                //do nothing. Means we've tried to get state before there are any records
            }
        });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
