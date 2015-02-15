package org.grothedev.fooddelivery.dbtasks;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.grothedev.fooddelivery.Business;
import org.grothedev.fooddelivery.Businesses;
import org.grothedev.fooddelivery.JSONParser;
import org.grothedev.fooddelivery.R;
import org.grothedev.fooddelivery.SessionVals;
import org.grothedev.fooddelivery.Settings;
import org.grothedev.fooddelivery.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 07/02/15.
 */
public class GetBusinessesTask extends DBTask {

    JSONParser jsonParser = new JSONParser();
    String url_get_businesses = Settings.url + "get_businesses.php";

   @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        activity = (Activity) objects[0];
        int timeout = Integer.parseInt(objects[1].toString());

        List<NameValuePair> params = new ArrayList<NameValuePair>(4);
        params.add(new BasicNameValuePair("region", User.region.toString()));
        params.add(new BasicNameValuePair("lat", Double.toString(User.userLocation.getLatitude())));
        params.add(new BasicNameValuePair("lon", Double.toString(User.userLocation.getLongitude())));
        params.add(new BasicNameValuePair("radius", Double.toString(SessionVals.radius)));

        if (Settings.DEBUG){
            Log.d("region", User.region.toString());
            Log.d("lat", Double.toString(User.userLocation.getLatitude()));
            Log.d("lon", Double.toString(User.userLocation.getLongitude()));
            Log.d("radius", Double.toString(SessionVals.radius));
        }

        JSONArray businesses = null;


        JSONObject json = jsonParser.makeHttpRequest(url_get_businesses, "GET", params);
        Log.d("bussinesses", json.toString());

        try {
            if (json.getInt("success") == 1){
                businesses = json.getJSONArray("businesses");

                for (int i = 0; i < businesses.length(); i++){
                    JSONObject b = businesses.getJSONObject(i);
                    Businesses.businessList.add(new Business(b.getInt("id"), b.getString("name"), b.getDouble("lat"), b.getDouble("lon"), b.getString("address"), b.getString("website"), b.getString("phone")));
                }

                success = true;
            } else {
                success = false;
            }
        } catch (JSONException e) {
            success = false;
            e.printStackTrace();
        }


        success = true;
        SessionVals.businessesObtained = true;
        SessionVals.prevRadius = SessionVals.radius;


        return null;
    }


}
