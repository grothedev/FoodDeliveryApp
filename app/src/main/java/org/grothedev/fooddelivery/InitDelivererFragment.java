package org.grothedev.fooddelivery;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.grothedev.fooddelivery.dbtasks.TaskIds;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 09/12/14.
 */
public class InitDelivererFragment extends Fragment implements View.OnClickListener{

    final int BECOME_DELIVERER_CODE = 99;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.fragment_del_init, container, false);
        Button becomeDeliverer = (Button)view.findViewById(R.id.button_del_init);
        becomeDeliverer.setOnClickListener(this);

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BECOME_DELIVERER_CODE){

            if (resultCode == Activity.RESULT_OK){
                //update navigation drawer
                NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                        getActivity().getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

                mNavigationDrawerFragment.becomeDeliverer();
                mNavigationDrawerFragment.setUp(
                        R.id.navigation_drawer,
                        (DrawerLayout) getActivity().findViewById(R.id.drawer_layout));

                //set view to deliverer control panel
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.container, new DelivererCPFragment());
                ft.commit();


                //grey button and toast
                Button become = (Button) view.findViewById(R.id.button_del_init);
                become.setClickable(false);
                become.setEnabled(false);


                //updateprefsfile
                SharedPreferences userData = getActivity().getSharedPreferences("userdata", 0);
                SharedPreferences.Editor edit = userData.edit();
                edit.putBoolean("isDeliverer", true);
                edit.commit();


                Toast.makeText(getActivity().getApplicationContext(), "You are now a deliverer", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onClick(View view) {

        Intent i = new Intent(getActivity().getApplicationContext(), LoadingScreenTaskActivity.class);
        i.putExtra("task_id", TaskIds.BECOME_DELIVERER);
        i.putExtra("message", "Becoming deliverer...");

        startActivityForResult(i, BECOME_DELIVERER_CODE);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }





}
