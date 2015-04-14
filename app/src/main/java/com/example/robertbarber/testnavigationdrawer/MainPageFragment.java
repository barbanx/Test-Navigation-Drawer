package com.example.robertbarber.testnavigationdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainPageFragment extends Fragment {

    private static final String TAG = "MainPageFragment";

    private Button mTestButton;
    private DrawerFragment.DrawerAppCallbacks mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DrawerFragment.DrawerAppCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement ButtonListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mTestButton = (Button) v.findViewById(R.id.page_main_slidebutton);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Test Button has been clicked");
                mListener.toggleNavigationDrawer();
            }
        });

        return v;
    }



}
