package org.grothedev.fooddelivery.dbtasks;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.grothedev.fooddelivery.JSONParser;
import org.grothedev.fooddelivery.Settings;
import org.grothedev.fooddelivery.User;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 08/02/15.
 */
public class GetUserLocationTask extends DBTask {

    //this class is obsolete

    Location userLocation;


    @Override
    protected Object doInBackground(Object[] objects) {

        activity = (Activity) objects[0];

        //get location through gps

        while (userLocation == null){} //wait for location to be retrieved before continuing

        //update location on client


        return null;
    }




}


