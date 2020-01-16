package com.petermarshall.model.helpers;

public abstract class DoubleFormat {
    public static String getToXDecimalPlaces(double d, int dp) {
        return String.format("%." + dp + "f", d);
    }
}
