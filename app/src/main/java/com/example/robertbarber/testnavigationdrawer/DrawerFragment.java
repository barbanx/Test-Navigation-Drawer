package com.example.robertbarber.testnavigationdrawer;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {

    private static final String TAG = "DrawerFragment";

    private Button mSlideButton;
    private Button mMainPageButton;
    private Button mEventsPageButton;
    private DrawerAppCallbacks mListener;

    public static interface DrawerAppCallbacks{
        public void drawerButtonPressed(int pageId);

        public void toggleNavigationDrawer();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (DrawerAppCallbacks) getActivity();
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() + " must implement DrawerAppCallbacks");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);

        mMainPageButton = (Button) v.findViewById(R.id.drawer_home_button);
        mMainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Home Page button pressed");
                mListener.drawerButtonPressed(R.id.drawer_home_button);

            }
        });

        mEventsPageButton = (Button) v.findViewById(R.id.drawer_events_button);
        mEventsPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Events Page button pressed");
                mListener.drawerButtonPressed(R.id.drawer_events_button);
            }
        });

        return v;
    }


}
