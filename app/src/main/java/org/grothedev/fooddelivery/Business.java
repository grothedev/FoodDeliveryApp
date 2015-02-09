package org.grothedev.fooddelivery;

import android.location.Location;

/**
 * Created by thomas on 08/02/15.
 */
public class Business {

    int id;
    String name;
    String address;
    String phone; //may need to change data type
    String website;
    double lat, lon;

    public Business(int id, String name, double lat, double lon){
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }


}
