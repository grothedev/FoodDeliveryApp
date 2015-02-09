package org.grothedev.fooddelivery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.grothedev.fooddelivery.dbtasks.TaskIds;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomas on 07/02/15.
 */
public class BusinessListFragment extends ListFragment {

    //this fragment is the list of businesses

    String[] businessNameList;
    final int REQUEST_CODE_GET_BUSINESSES = 3456;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);

        if (!SessionVals.userLocationObtained){
            getLocation();
            updateLocationOnServer();
        }



    }

    @Override
    public void onResume() {
        super.onResume();

        if (!SessionVals.businessesObtained){
            getBusinesses();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GET_BUSINESSES){
            if (resultCode == Activity.RESULT_OK){
                setupListView();
            } else {
                Log.d("not ok", "");
            }
        }

    }

    private void setupListView(){

        if (Businesses.businessList.size() == 0){

            businessNameList = new String[1];
            businessNameList[0] = "Was unable to retrieve businesses";

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, businessNameList);

            setListAdapter(adapter);
        } else {
            businessNameList = new String[Businesses.businessList.size()];
            for (int i = 0; i < Businesses.businessList.size(); i++){
                businessNameList[i] = Businesses.businessList.get(i).name;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, businessNameList);

            setListAdapter(adapter);
        }


    }

    private void updateLocationOnServer(){

        Intent i = new Intent(getActivity().getApplicationContext(), LoadingScreenTaskActivity.class);
        i.putExtra("task_id", TaskIds.UPDATE_USER_LOCATION);
        i.putExtra("message", "Updating your location...");

        startActivity(i);

    }

    private void getLocation(){

        LocationManager locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        LocationListener locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                User.userLocation = location;
                SessionVals.userLocationObtained = true;

                //assign region


                if (Settings.DEBUG){
                    Log.d("lat, long", Double.toString(User.userLocation.getLatitude()) + ", " + Double.toString(User.userLocation.getLongitude()));

                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                //request to be enabled
            }
        };

        locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locListener, null);

    }

    private void getBusinesses(){

        if (User.userLocation == null){
            Toast.makeText(getActivity().getApplicationContext(), "can't get location", Toast.LENGTH_LONG);
        } else {
            Intent i = new Intent(getActivity().getApplicationContext(), LoadingScreenTaskActivity.class);
            i.putExtra("task_id", TaskIds.GET_BUSINESSES);
            i.putExtra("message", "Finding businesses in your area...");

            startActivityForResult(i, REQUEST_CODE_GET_BUSINESSES);
        }

    }

}
