package org.grothedev.fooddelivery.dbtasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.grothedev.fooddelivery.JSONParser;
import org.grothedev.fooddelivery.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 19/01/15.
 */
public class BecomeDelivererTask extends DBTask {

    JSONParser jsonParser = new JSONParser();
    String url_make_deliverer = "http://96.42.75.21/android/food/db/make_deliverer.php";


    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected Object doInBackground(Object[] objects) {


        activity = (Activity)objects[0];
        int timeout = Integer.parseInt(objects[1].toString());

        List<NameValuePair> params = new ArrayList<NameValuePair>(1);
        params.add(new BasicNameValuePair("id", Integer.toString(User.userId)));



        JSONObject json = jsonParser.makeHttpRequest(url_make_deliverer, "POST", params);

        int counter = 0;
        while (json == null){
            counter++;
            if (counter == timeout){
                activity.finish();
            }
        } //wait for request to finish

        try {
            if (json.getInt("success") == 1){
                success = true;
            } else {
                success = false;
            }
        } catch (JSONException e) {
            success = false;
            e.printStackTrace();
        }


        return null;
    }

}
