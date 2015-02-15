package org.grothedev.fooddelivery;

import android.location.Location;

/**
 * Created by thomas on 08/02/15.
 */
public class Business {

    int id;
    public String name;
    public String address;
    public String phone; //may need to change data type
    public String website;
    public double lat, lon;

    public Business(int id, String name, double lat, double lon, String address, String website, String phone){
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.address = address;
        this.website = website;
        this.phone = phone;
    }


}
