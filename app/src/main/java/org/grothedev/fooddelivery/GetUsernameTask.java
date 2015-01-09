package org.grothedev.fooddelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by thomas on 12/12/14.
 */
public class GetUsernameTask extends AsyncTask {

    Activity activity;
    String scope;
    String email;
    final int REQUEST_CODE = 90;

    GetUsernameTask(Activity activity, String email, String scope){
        this.activity = activity;
        this.scope = scope;
        this.email = email;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        String token = null;
        try {
            token = fetchToken();
            if (token != null){

                final String logToken = token;

                activity.runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        Toast.makeText(activity, "Successfully got token", Toast.LENGTH_SHORT);

                        Settings.token = true;
                        Log.d("token", logToken);


                    }
                });



            } else {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "didn't get token", Toast.LENGTH_SHORT);

                        Settings.token = false;
                        Log.d("token", "no token got");


                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(activity, email, scope);
        } catch (UserRecoverableAuthException recoverableException){
            Intent recoveryIntent = recoverableException.getIntent();
            activity.startActivityForResult(recoveryIntent, REQUEST_CODE);

        }catch (GoogleAuthException e) {
            e.printStackTrace();
        }
        return null;
    }
}
