package org.firstinspires.ftc.teamcode.source;

public class utility {
    public static boolean inRange(double input, double target, double margin) {
        if ((target - margin) < input && input < (target + margin)) {
            return true;
        } else {
            return false;
        }
    } /// Basic Range (Using same high margin and low margin)

    public static boolean withinSetRange(double input, double min, double max) {
        if (input > min && input < max) {
            return true;
        }
        else {
            return false;
        }
    } /// Check if Input is between 2 Values

    public static double booleanToDouble(boolean input, double True, double False) {
        if (input == true) {
            return True;
        }
        else {
            return False;
        }
    } /// Boolean to Double (Custom Set)

    public static int advancedBooleanToInt(boolean input, int True, int False) {
        if (input == true) {
            return True;
        }
        else {
            return False;
        }

    } /// Boolean to Integer (Custom Set)

    public static boolean isPositive(double input) {
        if (input > 0) {
            return true;
        }
        else {
            return false;
        }
    } /// Returns if an input is positive

    public static boolean isNegative(double input) {
        if (input < 0) {
            return true;
        }
        else {
            return false;
        }
    } /// Returns if an input is negative


}
