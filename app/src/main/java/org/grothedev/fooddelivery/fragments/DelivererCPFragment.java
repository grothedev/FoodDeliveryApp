package org.grothedev.fooddelivery.fragments;


import android.app.Activity;
import android.support.v4.app.Fragment;

import org.grothedev.fooddelivery.MainActivity;

/**
 * Created by thomas on 09/02/15.
 */
public class DelivererCPFragment extends Fragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(2);
    }

}
