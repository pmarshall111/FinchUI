package com.petermarshall.model.helpers;

public class DoubleFormat {
    //private constructor as this class should not be instantiated.
    private DoubleFormat() {}
    public static String getToXDecimalPlaces(double d, int dp) {
        return String.format("%." + dp + "f", d);
    }
}
