package org.grothedev.fooddelivery;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

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
                Toast.makeText(activity, "Successfully got token", Toast.LENGTH_SHORT);
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
