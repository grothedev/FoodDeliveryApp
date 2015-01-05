package org.grothedev.fooddelivery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by thomas on 04/01/15.
 */
public class User {
    String userEmail;
    String userName;
    Context context;
    int userId;

    public User(Context context){
        this.context = context;
    }

    public void updateUserData(){
        SharedPreferences userData = context.getSharedPreferences("userdata", 0);
        Settings.isDeliverer = userData.getBoolean("isDeliverer", false);
        userEmail = userData.getString("email", null);
        userName = userData.getString("name", null);
        userId = userData.getInt("id", -1);
    }
}
