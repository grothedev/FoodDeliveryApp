package org.grothedev.fooddelivery.dbtasks;

import android.app.Activity;
import android.os.AsyncTask;
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
public class UpdateUserLocationTask extends DBTask {


    JSONParser jsonParser = new JSONParser();
    String url_update_user_location = "http://96.42.75.21/android/food/db/update_user_location.php";

    @Override
    protected Object doInBackground(Object[] objects) {
        activity = (Activity) objects[0];
        int timeout = (Integer) objects[1];

        while(User.userLocation == null){} //wait



        List<NameValuePair> params = new ArrayList<NameValuePair>(4);
        params.add(new BasicNameValuePair("id", Integer.toString(User.userId)));
        params.add(new BasicNameValuePair("lat", Double.toString(User.userLocation.getLatitude())));
        params.add(new BasicNameValuePair("long", Double.toString(User.userLocation.getLongitude())));
        params.add(new BasicNameValuePair("token", User.token)); //for authentication

        if (Settings.DEBUG){
            Log.d("id", Integer.toString(User.userId));
            Log.d("lat, long for server", Double.toString(User.userLocation.getLatitude()) + ", " + Double.toString(User.userLocation.getLongitude()));
            Log.d("token for server", User.token);
        }

        JSONObject json = jsonParser.makeHttpRequest(url_update_user_location, "POST", params);


        int counter = 0;
        while (json == null){
            counter++;
            if (counter == timeout){
                activity.finish();
            }
        } //wait for request to finish



        return null;
    }
}
