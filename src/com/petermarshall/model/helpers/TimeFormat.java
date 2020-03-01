package com.petermarshall.model.helpers;

public class TimeFormat {
    //private constructor as this class should not be instantiated.
    private TimeFormat() {}

    //format "XXXXm XXs"
    public static String getMinsSecsFromNanoSecs(long nanoSecs) {
        int seconds = (int) (nanoSecs / Math.pow(10, 9));
        int mins = seconds / 60;
        seconds = seconds % 60;
        return mins + "m " + seconds + "s";
    }
}
