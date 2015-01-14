package org.grothedev.fooddelivery;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;

import org.grothedev.fooddelivery.dbtasks.GetUserNameTask;


public class InitialSetupActivity extends ActionBarActivity {

    private final int REQUEST_CODE_PICK_ACCOUNT = 1000; //code for picking account activity
    String userEmail;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    Button chooseEmail;
    Button complete;

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



                    DatabaseHandler.userEmailExists(userEmail);

                    Log.d("id", Integer.toString(User.userId));



                    while(User.userId == User.NOT_SET_YET){} //wait for id to be set. this probably shouldn't be done on ui thread



                    if (User.userId == User.DOESNT_EXIST){ //user is not in database yet

                        User.userName = name.getText().toString();
                        if (User.userName == null){
                            //TODO: get from g+ profile
                            User.userName = "from g+ profile";
                        }

                        Log.d("username", User.userName);

                        DatabaseHandler.addUser(userEmail, User.userName);
                    } else {
                        new GetUserNameTask().execute(User.userId); //i don't think it's a good idea to pass around these static user variables
                    }

                    while (User.userId == User.DOESNT_EXIST || User.userName == null){} //wait for the user to be added to database and name to be gotten.  this probably shouldn't be done on ui thread

                    edit.putString("name", User.userName);
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

            new GetTokenTask(this, userEmail, SCOPE).execute();

        }
    }




}
