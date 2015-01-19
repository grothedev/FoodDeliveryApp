package org.grothedev.fooddelivery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import org.grothedev.fooddelivery.dbtasks.BecomeDelivererTask;
import org.grothedev.fooddelivery.dbtasks.TaskIds;

/**
 * Created by thomas on 18/01/15.
 */
public class LoadingScreenTaskActivity extends Activity {

    //the purpose of this activity is to show a loading icon, execute an AsyncTask, then end.
    //start for result, put task id as extra in intent


    private ProgressDialog pDialog;
    int successful;
    int timeout = 100000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pDialog = new ProgressDialog(LoadingScreenTaskActivity.this);
        pDialog.setMessage("making deliverer");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();


        startTask(getIntent().getExtras().getInt("task_id"));

    }

    @Override
    protected void onPause() {
        super.onPause();

        pDialog.dismiss();
    }

    private void startTask(int taskId) {

        switch (taskId){
            case TaskIds.BECOME_DELIVERER:
                new BecomeDelivererTask().execute(this, timeout);
                break;
            //TODO: initial add user
        }


    }


}