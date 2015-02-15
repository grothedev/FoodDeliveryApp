package org.grothedev.fooddelivery;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import org.grothedev.fooddelivery.fragments.BusinessListFragment;
import org.grothedev.fooddelivery.fragments.DelivererCPFragment;
import org.grothedev.fooddelivery.fragments.InitDelivererFragment;
import org.grothedev.fooddelivery.fragments.NavigationDrawerFragment;
import org.grothedev.fooddelivery.fragments.NewBusinessListFragment;
import org.grothedev.fooddelivery.googletasks.GetTokenTask;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    //Fragment managing the behaviors, interactions and presentation of the navigation drawer.
    private NavigationDrawerFragment mNavigationDrawerFragment;


    // Used to store the last screen title. For use in {@link #restoreActionBar()}.
    private CharSequence mTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));



        if (!firstRun()){
            authenticate();
            User.updateUserData(this);
        }



    }

    private void authenticate(){
        new GetTokenTask(this, User.userEmail, "oauth2:https://www.googleapis.com/auth/userinfo.profile").execute();
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
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (User.isDeliverer){
            switch (position){
                case 0: //order

                    ft.replace(R.id.container, new NewBusinessListFragment());
                    ft.commit();

                    break;
                case 1: //delivery CP

                    ft.replace(R.id.container, new DelivererCPFragment());
                    ft.commit();

                    break;

                default:
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                            .commit();
                    break;
            }
        } else {
            switch (position){
                case 0: //order


                    ft.replace(R.id.container, new BusinessListFragment());
                    ft.commit();

                    break;
                case 1: //become deliverer
                    Fragment initDelivererFrag = new InitDelivererFragment();
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
                    mTitle = getString(R.string.title_order);
                    break;
                case 2:
                    mTitle = getString(R.string.title_del_cp);
                    break;
                case 3:
                    mTitle = getString(R.string.title_del_requests);
                    break;
                case 4:
                    mTitle = getString(R.string.title_del_active);
                    break;
            }
        } else {
            switch (number) {
                case 1:
                    mTitle = getString(R.string.title_order);
                    break;
                case 2:
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
        if (firstRun()){
            startActivity(new Intent(this, InitialSetupActivity.class));

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
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
