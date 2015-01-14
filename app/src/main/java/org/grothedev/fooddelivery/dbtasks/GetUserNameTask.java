package org.grothedev.fooddelivery.dbtasks;

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
 * Created by thomas on 13/01/15.
 */
public class GetUserNameTask extends AsyncTask {

    JSONParser jsonParser = new JSONParser();
    String url_get_user_name = "http://96.42.75.21/android/food/db/get_user_name.php";

    @Override
    protected Object doInBackground(Object[] objects) {

        int id = Integer.parseInt(objects[0].toString());



        List<NameValuePair> params = new ArrayList<NameValuePair>(1);
        params.add(new BasicNameValuePair("id", Integer.toString(id)));

        JSONObject json = jsonParser.makeHttpRequest(url_get_user_name, "GET", params);

        try {
            User.userName = json.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("uname", User.userName); //trying to figure out why name is null in prefs file

        return null;
    }
}
