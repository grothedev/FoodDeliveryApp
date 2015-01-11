package org.grothedev.fooddelivery;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class InitialSetupActivity extends ActionBarActivity {

    private final int REQUEST_CODE_PICK_ACCOUNT = 1000; //code for picking account activity
    String userEmail;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    Button chooseEmail;
    Button complete;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setup);

        chooseEmail = (Button)findViewById(R.id.button_choose_email);
        chooseEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickUserAccount();
            }
        });

        complete = (Button)findViewById(R.id.button_init_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //the user owns the google account
                if (Settings.token){ //might it be possible for someone to change the value of Settings.token ?

                    EditText name = (EditText)findViewById(R.id.editText_name);
                    SharedPreferences userData = getSharedPreferences("userdata", 0);
                    SharedPreferences.Editor edit = userData.edit();


                    User.userId = DatabaseHandler.userEmailExists(userEmail);
                    if (User.userId == -1){ //user is not in database yet
                        if (name.getText().toString() != null){
                            userName = name.getText().toString();
                        } else {
                            //TODO: get from g+ profile
                            userName = "from g+ profile";
                        }

                        DatabaseHandler.addUser(userEmail, userName);
                    }

                    while (User.userId == -1){ //i think this is on ui thread so the app might freeze for a bit if poor internet connection
                        //wait for the user id to be successfully updated
                    }

                    edit.putString("name", userName);
                    edit.putString("email", userEmail);
                    edit.putBoolean("firstRun", false);
                    edit.putInt("id", User.userId);
                    edit.commit();


                } else {
                    Toast.makeText(getApplicationContext(), "don't have auth token", Toast.LENGTH_SHORT).show();
                }






                finish();

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initial_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, true, "Your food delivery account will be associated with your google account", null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_ACCOUNT){
            if (resultCode == RESULT_OK){
                userEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

                getUsername();

                chooseEmail.setText("Chosen email: " + userEmail);
            }

        }
    }

    private void getUsername(){
        if (userEmail == null){
            pickUserAccount();
        } else {

            new GetUsernameTask(this, userEmail, SCOPE).execute();

        }
    }

    class AddUser extends AsyncTask{

        JSONParser jsonParser = new JSONParser();
        String url_add_user = "http://96.42.75.21/android/food/db/add_user.php";

        @Override
        protected Object doInBackground(Object[] objects) {
            String email = objects[0].toString();
            String name = objects[1].toString();



            List<NameValuePair> params = new ArrayList<NameValuePair>(2);
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("email", email));

            //TODO: setup token auth thing on server
            JSONObject json = jsonParser.makeHttpRequest(url_add_user, "POST", params);


            //finish();

            return null;
        }


    }

}
