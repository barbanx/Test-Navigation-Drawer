package com.example.robertbarber.testnavigationdrawer;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    private static final String TAG = "EventsFragment";

    private DrawerFragment.DrawerAppCallbacks mListener;

    private Button mToggleDrawerButton;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (DrawerFragment.DrawerAppCallbacks) getActivity();
        } catch (ClassCastException cce) {
            throw new ClassCastException(getActivity().toString() + " must implement DrawerAppCallbacks");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_events, container, false);


        mToggleDrawerButton = (Button) v.findViewById(R.id.fragment_events_slidebutton);
        mToggleDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "EventsFragment nav drawer button pressed");
                mListener.toggleNavigationDrawer();
            }
        });

        return v;
    }




}
