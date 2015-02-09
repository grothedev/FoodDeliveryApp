package org.grothedev.fooddelivery.dbtasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by thomas on 07/02/15.
 */
public abstract class DBTask extends AsyncTask {

    Activity activity;
    boolean success = false;

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (activity != null) {
            if (success) {
                activity.setResult(Activity.RESULT_OK);
            }

            activity.finish();
        } else {
            Log.d("activity null", "");
        }
    }

}
