package org.grothedev.fooddelivery.dbtasks;

import android.os.AsyncTask;

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
public class IdOfEmailTask extends AsyncTask {

    JSONParser jsonParser = new JSONParser();
    String url_id_of_email = "http://96.42.75.21/android/food/db/get_id_of_email.php";

    @Override
    protected Object doInBackground(Object[] objects) {
        String email = objects[0].toString();

        List<NameValuePair> params = new ArrayList<NameValuePair>(1);
        params.add(new BasicNameValuePair("email", email));

        JSONObject json = jsonParser.makeHttpRequest(url_id_of_email, "GET", params);


        int id;
        try {
            id = json.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
            id = User.DOESNT_EXIST;
        }

        User.userId = id;

        return null;
    }
}
