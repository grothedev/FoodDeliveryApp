package org.grothedev.fooddelivery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by thomas on 09/12/14.
 */
public class InitDeliverer extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_del_init, container, false);
        Button becomeDeliverer = (Button)view.findViewById(R.id.button_del_init);
        becomeDeliverer.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        SharedPreferences userData = getActivity().getSharedPreferences("userdata", 0);
        SharedPreferences.Editor edit = userData.edit();
        edit.putBoolean("isDeliverer", true);
        edit.commit();

        //update navigation drawer
        NavigationDrawerFragment mNavigationDrawerFragment = (NavigationDrawerFragment)
                getActivity().getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mNavigationDrawerFragment.becomeDeliverer();
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) getActivity().findViewById(R.id.drawer_layout));


        //grey button and toast
        Button become = (Button) view.findViewById(R.id.button_del_init);
        become.setClickable(false);
        become.setEnabled(false);

        Toast.makeText(getActivity().getApplicationContext(), "You are now a deliverer", Toast.LENGTH_SHORT).show();

        //update server data

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((WelcomeActivity) activity).onSectionAttached(2);
    }
}
