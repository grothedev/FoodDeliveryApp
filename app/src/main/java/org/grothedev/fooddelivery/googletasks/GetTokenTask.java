package org.grothedev.fooddelivery.googletasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import org.grothedev.fooddelivery.Settings;

import java.io.IOException;

/**
 * Created by thomas on 12/12/14.
 */
public class GetTokenTask extends AsyncTask {

    Activity activity;
    String scope;
    String email;
    final int REQUEST_CODE = 90;

    public GetTokenTask(Activity activity, String email, String scope){
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

                Settings.token = true;


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
