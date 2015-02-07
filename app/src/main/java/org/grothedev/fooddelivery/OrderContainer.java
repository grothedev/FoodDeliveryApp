package org.grothedev.fooddelivery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by thomas on 07/02/15.
 */
public class OrderContainer extends Fragment {

    //this is a fragment that contains two fragments, the search bar and the listview for the business viewing

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_container, container, false);
        return view;
    }

}
