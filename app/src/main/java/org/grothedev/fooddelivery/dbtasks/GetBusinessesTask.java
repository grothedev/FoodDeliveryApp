package org.grothedev.fooddelivery.dbtasks;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;

import org.grothedev.fooddelivery.Business;
import org.grothedev.fooddelivery.Businesses;
import org.grothedev.fooddelivery.SessionVals;
import org.grothedev.fooddelivery.User;

/**
 * Created by thomas on 07/02/15.
 */
public class GetBusinessesTask extends DBTask {
    @Override
    protected Object doInBackground(Object[] objects) {

        activity = (Activity) objects[0];

        //get data from db
        Businesses.businessList.add(new Business(23, "chipotle", 20, -10)); //placeholder data

        //TODO

        success = true;
        SessionVals.businessesObtained = true;

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
