package org.grothedev.fooddelivery;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;

import java.io.IOException;

/**
 * Created by thomas on 12/12/14.
 */
public class GetUsernameTask extends AsyncTask {

    Activity activity;
    String scope;
    String email;

    GetUsernameTask(Activity activity, String name, String scope){
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
                //use token to access user's google data
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(activity, email, scope);
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        }
        return null;
    }
}
