package org.grothedev.fooddelivery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

/**
 * Created by thomas on 04/01/15.
 */
public class User {
    static String userEmail;
    static String userName;
    public static int userId;
    static boolean isDeliverer;


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
