package org.grothedev.fooddelivery;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;


public class WelcomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    //Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;


    // Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;

    private DatabaseHandler dbHandler = new DatabaseHandler();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (firstRun()){
            startActivity(new Intent(this, InitialSetupActivity.class));
        } else {
            authenticate();
        }






        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));



    }

    private void authenticate(){
        if (dbHandler.userIdExists(User.userId)){
            if (User.userEmail.equals(dbHandler.getUserEmail(User.userId))){
                //authenticate email using google api
            } else {
                //start dialog asking to select email or make new account
            }
        } else {
            if (dbHandler.userEmailExists(User.userEmail)){
                //authenticate email using google api
                //if successful google account authentication, fix id in prefs
            } else {
                //start dialog asking to select email or make new account
            }
        }
    }

    private boolean firstRun(){
        SharedPreferences userData = getSharedPreferences("userdata", 0);
        return userData.getBoolean("firstRun", true);
    }




    @Override
    public void onNavigationDrawerItemSelected(int position) {

        User.updateUserData(this);





        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (User.isDeliverer){
            switch (position){
                default:
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                            .commit();
                    break;
            }
        } else {
            switch (position){
                case 2: //become deliverer
                    Fragment initDelivererFrag = new InitDeliverer();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, initDelivererFrag)
                            .commit();
                    break;
                default:
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                            .commit();
                    break;

            }
        }


    }

    public void onSectionAttached(int number) {
        if (User.isDeliverer){
            switch (number) {
                case 1:
                    mTitle = getString(R.string.title_welcome);
                    break;
                case 2:
                    mTitle = getString(R.string.title_order);
                    break;
                case 3:
                    mTitle = getString(R.string.title_del_cp);
                    break;
                case 4:
                    mTitle = getString(R.string.title_del_requests);
                    break;
                case 5:
                    mTitle = getString(R.string.title_del_active);
                    break;
            }
        } else {
            switch (number) {
                case 1:
                    mTitle = getString(R.string.title_welcome);
                    break;
                case 2:
                    mTitle = getString(R.string.title_order);
                    break;
                case 3:
                    mTitle = getString(R.string.title_del_init);

                    break;
            }
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.welcome, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!firstRun()) {
            User.updateUserData(this);
            authenticate(); //authenticate each time resumed to prevent exploits from users modifying prefs file
        }


    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((WelcomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
