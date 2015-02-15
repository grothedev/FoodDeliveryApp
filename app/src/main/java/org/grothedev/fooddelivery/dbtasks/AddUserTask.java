package org.grothedev.fooddelivery.dbtasks;

import android.app.Activity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.grothedev.fooddelivery.JSONParser;
import org.grothedev.fooddelivery.Settings;
import org.grothedev.fooddelivery.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 09/02/15.
 */
public class AddUserTask extends DBTask {

    JSONParser jsonParser = new JSONParser();
    String url_id_of_email = Settings.url + "get_id_of_email.php";
    String url_add_user = Settings.url + "add_user.php";
    String url_get_user_name = Settings.url + "get_user_name.php";
    String url_update_user_name = Settings.url + "update_user_name.php";

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        activity = (Activity) objects[0];

        getIdFromEmail();

        while (User.userId == User.NOT_SET_YET){}//wait for id to be set

        if (User.userId == User.DOESNT_EXIST){
            addUserToDB();
        } else {
            if (User.userName.equals("")){
                getUserName(); //get this user's previous username
            } else {
                updateUserName();
            }
        }

        success = true;

        return null;
    }

    private void getUserName(){
        List<NameValuePair> params = new ArrayList<NameValuePair>(1);
        params.add(new BasicNameValuePair("id", Integer.toString(User.userId)));

        JSONObject json = jsonParser.makeHttpRequest(url_get_user_name, "GET", params);

        try {
            User.userName = json.getString("name");
        } catch (JSONException e) {
            success = false;
            e.printStackTrace();
        }
    }

    private void updateUserName(){
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("id", Integer.toString(User.userId)));
        params.add(new BasicNameValuePair("name", User.userName));

        JSONObject json = jsonParser.makeHttpRequest(url_update_user_name, "POST", params);

        try {
            if (json.getInt("success") == 0){
                success = false;
            }
        } catch (JSONException e) {
            success = false;
            e.printStackTrace();
        }
    }

    private void addUserToDB(){
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("name", User.userName));
        params.add(new BasicNameValuePair("email", User.userEmail));


        JSONObject json = jsonParser.makeHttpRequest(url_add_user, "POST", params);


        try {
            User.userId = json.getInt("id");
        } catch (JSONException e) {
            success = false;
            e.printStackTrace();
        }
    }

    //if email already exists, use that id, else make a new user
    private void getIdFromEmail(){
        List<NameValuePair> params = new ArrayList<NameValuePair>(1);
        params.add(new BasicNameValuePair("email", User.userEmail));

        JSONObject json = jsonParser.makeHttpRequest(url_id_of_email, "GET", params);

        int id;
        try {
            id = json.getInt("id");

        } catch (JSONException e) {
            e.printStackTrace();
            id = User.DOESNT_EXIST;

        }

        User.userId = id;
    }
}
