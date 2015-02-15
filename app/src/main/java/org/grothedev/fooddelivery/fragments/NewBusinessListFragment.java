package org.grothedev.fooddelivery.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import org.grothedev.fooddelivery.BusinessListManager;
import org.grothedev.fooddelivery.R;

/**
 * Created by thomas on 15/02/15.
 */
public class NewBusinessListFragment extends Fragment {

    View view;
    ListView businessListView;
    BusinessListManager businessListViewManager;

    final int REQUEST_CODE_GET_BUSINESSES = 3456;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_businuess_list, container, false);

        initSearchBar();
        initBusinessList();

        return view;
    }

    private void initBusinessList(){
        businessListViewManager = new BusinessListManager(businessListView, view, getActivity());
    }

    private void initSearchBar(){
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


                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GET_BUSINESSES){
            if (resultCode == Activity.RESULT_OK){
                businessListViewManager.updateListView();
            }
        }

    }


}
