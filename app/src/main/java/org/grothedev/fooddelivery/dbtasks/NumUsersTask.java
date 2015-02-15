package org.grothedev.fooddelivery.dbtasks;

import android.os.AsyncTask;
import android.util.Log;

import org.grothedev.fooddelivery.JSONParser;
import org.grothedev.fooddelivery.Settings;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by thomas on 11/01/15.
 */
public class NumUsersTask extends AsyncTask {


    JSONParser jsonParser = new JSONParser();
    String url_num_users = Settings.url + "num_users.php";
    int users;

    @Override
    protected Integer doInBackground(Object[] objects) {


        JSONObject json = jsonParser.makeHttpRequest(url_num_users, "GET", null);

        try {
            users = json.getInt("users");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

}
