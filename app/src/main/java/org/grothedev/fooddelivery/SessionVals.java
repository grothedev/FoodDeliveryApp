package org.grothedev.fooddelivery;

/**
 * Created by thomas on 08/02/15.
 */
public class SessionVals {
    //this class is to keep track of what data has already been retrieved during this session

    public static boolean userLocationObtained = false;
    public static boolean businessesObtained = false;
    public static int radius = 20; //this is the default radius (always in km)
    public static double prevRadius = -1;
    public static enum DistanceUnit {
        KM, MI
    }
    public static DistanceUnit radiusUnit = DistanceUnit.KM; //this just tells what unit is being inputed, not the unit of the radius variable
}
