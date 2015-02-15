package org.grothedev.fooddelivery;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.grothedev.fooddelivery.dbtasks.TaskIds;

/**
 * Created by thomas on 15/02/15.
 */
public class BusinessListManager {

    ListView listView;
    View view;
    Activity activity;

    final int REQUEST_CODE_GET_BUSINESSES = 3456;

    public BusinessListManager(ListView lv, View view, Activity activity){
        this.listView = lv;
        this.view = view;
        this.activity = activity;

        getBusinesses();
    }

    private void getBusinesses(){

        if (User.userLocation == null){
            Toast.makeText(activity, "can't get location", Toast.LENGTH_LONG);
        } else {
            Intent i = new Intent(activity.getApplicationContext(), LoadingScreenTaskActivity.class);
            i.putExtra("task_id", TaskIds.GET_BUSINESSES);
            i.putExtra("message", "Finding businesses in your area...");

            activity.startActivityForResult(i, REQUEST_CODE_GET_BUSINESSES);
        }

    }

    public void updateListView(){
        if (Businesses.businessList.size() == 0){

            String[] businessNameList = new String[1];
            businessNameList[0] = "Was unable to retrieve businesses";

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, businessNameList);
            adapter.notifyDataSetChanged();

            listView.setAdapter(adapter);
        } else {
            String[] businessNameList = new String[Businesses.businessList.size()];
            for (int i = 0; i < Businesses.businessList.size(); i++){
                businessNameList[i] = Businesses.businessList.get(i).name;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, businessNameList);
            adapter.notifyDataSetChanged();

            listView.setAdapter(adapter);
        }
    }

}
