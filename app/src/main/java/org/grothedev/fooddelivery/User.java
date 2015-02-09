package org.grothedev.fooddelivery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.widget.EditText;

/**
 * Created by thomas on 04/01/15.
 */
public class User {
    public static String userEmail;
    public static String userName;
    public static int userId;
    public static boolean isDeliverer;
    public static final int DOESNT_EXIST = 0; //email doesn't exist in database
    public static final int NOT_SET_YET = -1; //id not yet set

    public static String token;
    public static boolean hasToken = false;

    public static Location userLocation;

    public static void updateUserData(Context context){
        SharedPreferences userData = context.getSharedPreferences("userdata", 0);
        isDeliverer = userData.getBoolean("isDeliverer", false);
        userEmail = userData.getString("email", null);
        userName = userData.getString("name", null);
        userId = userData.getInt("id", -1);
    }

    public static void updatePrefsFile(Context context){
        SharedPreferences userData = context.getSharedPreferences("userdata", 0);
        SharedPreferences.Editor edit = userData.edit();
        edit.putString("name", userName);
        edit.putString("email", userEmail);
        edit.putInt("id", userId);
        edit.commit();
    }
}
