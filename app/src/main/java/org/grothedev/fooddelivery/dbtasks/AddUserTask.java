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
 * Created by thomas on 11/01/15.
 */
public class AddUserTask extends AsyncTask{
    JSONParser jsonParser = new JSONParser();
    String url_add_user = "http://96.42.75.21/android/food/db/add_user.php";

    @Override
    protected Object doInBackground(Object[] objects) {
        String email = objects[0].toString();
        String name = objects[1].toString();



        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));

        //TODO: setup token auth thing on server
        JSONObject json = jsonParser.makeHttpRequest(url_add_user, "POST", params);


        try {
            User.userId = json.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("add user response", json.toString());
        Log.d("local id", Integer.toString(User.userId));
        return null;
    }
}
