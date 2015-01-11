package org.grothedev.fooddelivery.dbtasks;

import android.os.AsyncTask;
import android.util.Log;

import org.grothedev.fooddelivery.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thomas on 11/01/15.
 */
public class NumUsersTask extends AsyncTask {


    JSONParser jsonParser = new JSONParser();
    String url_num_users = "http://96.42.75.21/android/food/db/num_users.php";

    @Override
    protected Object doInBackground(Object[] objects) {


        JSONObject json = jsonParser.makeHttpRequest(url_num_users, "POST", null);

        try {
            int users = json.getInt("users");
            Log.d("users", Integer.toString(users));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
