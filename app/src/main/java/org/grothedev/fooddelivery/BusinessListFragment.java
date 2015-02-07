package org.grothedev.fooddelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.grothedev.fooddelivery.dbtasks.TaskIds;

/**
 * Created by thomas on 07/02/15.
 */
public class BusinessListFragment extends ListFragment {

    //this fragment is the list of businesses

    String[] businessesList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);

        getBusinesses();

        updateLocalList();




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, businessesList);

        setListAdapter(adapter);


    }

    private void getBusinesses(){
        Intent i = new Intent(getActivity().getApplicationContext(), LoadingScreenTaskActivity.class);
        i.putExtra("task_id", TaskIds.GET_BUSINESSES);

        startActivity(i);

    }


    private void updateLocalList(){

        SharedPreferences businesses = getActivity().getSharedPreferences("businesses", 0);
        int numBusiness = businesses.getInt("num_businesses", 0);
        for (int i = 0; i < numBusiness; i++){
            businessesList[i] = businesses.getString(Integer.toString(i), "?");
        }

    }
}
