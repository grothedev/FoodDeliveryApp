package org.grothedev.fooddelivery.dbtasks;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.grothedev.fooddelivery.JSONParser;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 13/01/15.
 */
public class UpdateUserNameTask extends AsyncTask {

    JSONParser jsonParser = new JSONParser();
    String url_update_user_name = "http://96.42.75.21/android/food/db/update_user_name.php";

    @Override
    protected Object doInBackground(Object[] objects) {

        String id = objects[0].toString();
        String name = objects[1].toString();

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("name", name));

        JSONObject json = jsonParser.makeHttpRequest(url_update_user_name, "POST", params);

        return null;
    }
}
