package org.grothedev.fooddelivery.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import org.grothedev.fooddelivery.Business;
import org.grothedev.fooddelivery.Businesses;
import org.grothedev.fooddelivery.LoadingScreenTaskActivity;
import org.grothedev.fooddelivery.LocationHandler;
import org.grothedev.fooddelivery.MainActivity;
import org.grothedev.fooddelivery.R;
import org.grothedev.fooddelivery.SessionVals;
import org.grothedev.fooddelivery.Settings;
import org.grothedev.fooddelivery.User;
import org.grothedev.fooddelivery.dbtasks.TaskIds;

import java.util.ArrayList;

/**
 * Created by thomas on 10/02/15.
 */
public class BusinessListFragment extends Fragment { //i am currently redoing this class (see NewBusinessListFragment)

    View view;
    //String[] businessNameList;
    ListView businessListView;

    final int REQUEST_CODE_GET_BUSINESSES = 3456;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_businuess_list, container, false);

        setupSearchBar();
        initBusinessListView(); //TODO create a business list view class
        //setupRadiusInput();

        return view;
    }

    private void setupRadiusInput(){
        final EditText radiusInput = (EditText) view.findViewById(R.id.radius);

        Spinner radiusUnit = (Spinner) view.findViewById(R.id.radiusUnitSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        radiusUnit.setAdapter(adapter);

        radiusUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){ //km

                    SessionVals.radiusUnit = SessionVals.DistanceUnit.KM;
                    if (!radiusInput.getText().toString().equals("")){
                        SessionVals.radius = Integer.parseInt(radiusInput.getText().toString());
                        dealWithNewRadius();
                    }


                } else { //mi
                    SessionVals.radiusUnit = SessionVals.DistanceUnit.MI;
                    if (!radiusInput.getText().toString().equals("")){
                        SessionVals.radius = (int)((double) Integer.parseInt(radiusInput.getText().toString()) / 1.60934);
                        dealWithNewRadius();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radiusInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                String radString = charSequence.toString();

                if (!radString.equals("")){
                    int radius = Integer.parseInt(radString);
                    if (SessionVals.radiusUnit == SessionVals.DistanceUnit.MI){
                        SessionVals.radius = (int) (radius * 1.60934);
                    } else {
                        SessionVals.radius = radius;
                    }
                    dealWithNewRadius();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void dealWithNewRadius(){
        if (SessionVals.radius != SessionVals.prevRadius){
            if (SessionVals.radius > SessionVals.prevRadius){ //need to make another call to the server
                getBusinesses();
            } else { //just remove the appropiate businesses from the list
                ArrayList<Business> localBusinessList = new ArrayList<Business>(Businesses.businessList);

                for (int i = 0; i < Businesses.businessList.size(); i++){
                    if (i == localBusinessList.size()) break;

                    if (!LocationHandler.userWithinRadius(localBusinessList.get(i))){
                        localBusinessList.remove(i);
                        i--;
                    }
                }

                String[] newBusinessNameList = new String[localBusinessList.size()];
                for (int i = 0; i < localBusinessList.size(); i++){
                    newBusinessNameList[i] = localBusinessList.get(i).name;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newBusinessNameList);
                adapter.notifyDataSetChanged();

                businessListView.setAdapter(adapter);


            }
        }
    }

    private void initBusinessListView(){
        businessListView = (ListView)view.findViewById(R.id.businessList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GET_BUSINESSES){
            if (resultCode == Activity.RESULT_OK){
                setupListView();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (!SessionVals.userLocationObtained){
            getLocation();
            updateLocationOnServer();
        }

        if (!SessionVals.businessesObtained){
            getBusinesses();
        }

        setupListView();

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

    private void updateLocationOnServer(){

        Intent i = new Intent(getActivity().getApplicationContext(), LoadingScreenTaskActivity.class);
        i.putExtra("task_id", TaskIds.UPDATE_USER_LOCATION);
        i.putExtra("message", "Updating your location...");

        startActivity(i);

    }

    private void assignRegion(){
        double lat = User.userLocation.getLatitude();
        double lon = User.userLocation.getLongitude();

        if (lat >= 0 && lat <= 45){
            if (lon >= -180 && lon <= -90){
                User.region = User.Region.r2B;
            } else if (lon >= -90 && lon <= 0){
                User.region = User.Region.r2A;
            } else if (lon >= 0 && lon <= 90){
                User.region = User.Region.r1A;
            } else {
                User.region = User.Region.r1B;
            }
        } else if (lat >= 45 && lat <= 90){
            if (lon >= -180 && lon <= -90){
                User.region = User.Region.r2D;
            } else if (lon >= -90 && lon <= 0){
                User.region = User.Region.r2C;
            } else if (lon >= 0 && lon <= 90){
                User.region = User.Region.r1C;
            } else {
                User.region = User.Region.r1D;
            }
        } else if (lat >= -45 && lat <= 0){
            if (lon >= -180 && lon <= -90){
                User.region = User.Region.r3B;
            } else if (lon >= -90 && lon <= 0){
                User.region = User.Region.r3A;
            } else if (lon >= 0 && lon <= 90){
                User.region = User.Region.r4A;
            } else {
                User.region = User.Region.r4B;
            }
        } else {
            if (lon >= -180 && lon <= -90){
                User.region = User.Region.r3D;
            } else if (lon >= -90 && lon <= 0){
                User.region = User.Region.r3C;
            } else if (lon >= 0 && lon <= 90){
                User.region = User.Region.r4C;
            } else {
                User.region = User.Region.r4D;
            }
        }

    }

    private void getLocation(){

        LocationManager locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        LocationListener locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                User.userLocation = location;
                SessionVals.userLocationObtained = true;

                assignRegion();


                if (Settings.DEBUG){
                    Log.d("lat, long", Double.toString(User.userLocation.getLatitude()) + ", " + Double.toString(User.userLocation.getLongitude()));
                    Log.d("region", User.region.toString());
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

            }
        };

        locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locListener, null);

    }

    private void setupSearchBar(){
        SearchView searchView = (SearchView) view.findViewById(R.id.searchBusinesses);
        searchView.setIconified(false);

        //listener for change in search query which will change the stuff in the listview
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { //do nothing
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                    updateListView(s.toLowerCase());

                return false;
            }
        });
    }


    private void updateListView(String q){

        ArrayList<Business> localBusinessList = new ArrayList<Business>(Businesses.businessList);

        for (int i = 0; i < Businesses.businessList.size(); i++){
            if (i == localBusinessList.size()){
                break;
            }
            if (!localBusinessList.get(i).name.toLowerCase().contains(q)){
                localBusinessList.remove(i);
                i--;
            }
        }

        String[] newBusinessNameList = new String[localBusinessList.size()];
        for (int i = 0; i < localBusinessList.size(); i++){
            newBusinessNameList[i] = localBusinessList.get(i).name;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, newBusinessNameList);
        adapter.notifyDataSetChanged();

        businessListView.setAdapter(adapter);

    }

    private void setupListView(){

        if (Businesses.businessList.size() == 0){

            String[] businessNameList = new String[1];
            businessNameList[0] = "Was unable to retrieve businesses";

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, businessNameList);
            adapter.notifyDataSetChanged();

            businessListView.setAdapter(adapter);
        } else {
            String[] businessNameList = new String[Businesses.businessList.size()];
            for (int i = 0; i < Businesses.businessList.size(); i++){
                businessNameList[i] = Businesses.businessList.get(i).name;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, businessNameList);
            adapter.notifyDataSetChanged();

            businessListView.setAdapter(adapter);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(1);
    }

}
