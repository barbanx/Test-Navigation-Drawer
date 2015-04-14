package com.example.robertbarber.testnavigationdrawer;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * This template project is in place to start an app with an under-laying drawer instead
 * of an overlaying drawer. It is set up with an Events and MainPage fragment to better show how
 * to use it.
 *
 * To add another page:
 * 1: A new fragment should be created with it's own XML layout file.
 *
 * 2: A button should be added to the DrawerFragment and must call
 *      DrawerAppCallbacks.drawerButtonPressed(int buttonId) when clicked.
 *
 * 3: A new switch statement case needs to be added to drawerButtonPressed inside this
 *      (DrawerActivity) class that checks the proper corresponding drawer button ID and starts
 *      the added page if it doesn't already exist.
 *
 */

public class DrawerActivity extends FragmentActivity implements DrawerFragment.DrawerAppCallbacks{

    private static final String TAG = "DrawerActivity";

    private FrameLayout mDrawerLayout;
    private FrameLayout mCurrentPageLayout; // Layout of current page being viewed
    private Fragment mDrawerFragment;
    private Fragment mCurrentPageFragment; // This fragment contains whatever page is currently being viewed

    private FragmentManager mFragmentManager; // Used as a private field for optimization

    private int mDisplayWidth;
    private boolean mIsDrawerShown;
    private int mSliderEndPosition;
    private ObjectAnimator mShowDrawerTransition; // Hamburger button transition
    private ObjectAnimator mHideDrawerTransition;
    private Handler mDrawerVisibilityHandler = new Handler();
    private Runnable mHideDrawerRunner;
    private int mShowDrawerInterval = 500; // 500 ms for the show drawer transition

    private Handler mChangeFragmentHandler = new Handler();
    private Runnable mChangeFragmentRunner; // Needed for delaying fragment transaction

    @SuppressWarnings("deprecation")
    @TargetApi(13)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        mFragmentManager = getSupportFragmentManager();

        Display display = getWindowManager().getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Point p = new Point();
            display.getSize(p);
            mDisplayWidth = p.x;
        } else {
            mDisplayWidth = display.getWidth();

        }

        mSliderEndPosition = (int) (mDisplayWidth * 0.8); // Where the slide transition will end

        mIsDrawerShown = false; // Activity starts with the drawer hidden

        // Start the two fragments
        mDrawerFragment = mFragmentManager.findFragmentById(R.id.fragment_drawer_container);
        mCurrentPageFragment = mFragmentManager.findFragmentById(R.id.fragment_overlay_container);

        // Start the drawer fragment and the Main Page if they aren't started.
        if (mDrawerFragment == null && mCurrentPageFragment == null) {
            mDrawerFragment = new DrawerFragment();
            mCurrentPageFragment = new MainPageFragment();

            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_drawer_container, mDrawerFragment)
                    .add(R.id.fragment_overlay_container, mCurrentPageFragment)
                    .commit();
        } else if (mDrawerFragment == null && mCurrentPageFragment != null) {
            mDrawerFragment = new DrawerFragment();

            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_drawer_container, mDrawerFragment)
                    .commit();
        } else if (mDrawerFragment != null && mCurrentPageFragment == null) {
            mCurrentPageFragment = new MainPageFragment();

            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_overlay_container, mCurrentPageFragment)
                    .commit();
        }

        mDrawerLayout = (FrameLayout) findViewById(R.id.fragment_drawer_container);
        mDrawerLayout.setVisibility(View.INVISIBLE); // Initially invisible (for performance)

        mCurrentPageLayout = (FrameLayout) findViewById(R.id.fragment_overlay_container);

        // Assign Transitions
        mShowDrawerTransition = ObjectAnimator.ofInt(mCurrentPageLayout, "left", 0, mSliderEndPosition);
        mShowDrawerTransition.setDuration(mShowDrawerInterval);

        mHideDrawerTransition = ObjectAnimator.ofInt(mCurrentPageLayout, "left", mSliderEndPosition, 0);
        mHideDrawerTransition.setDuration(mShowDrawerInterval);

        mHideDrawerRunner = new Runnable() {
            @Override
            public void run() {
                if (mDrawerLayout.getVisibility() == View.VISIBLE) {
                    mDrawerLayout.setVisibility(View.INVISIBLE);
                }
            }
        };


    }

    public void drawerButtonPressed(int buttonId) {


        final Fragment fragment;
        switch (buttonId) {
            case R.id.drawer_home_button:
                if (mCurrentPageFragment instanceof MainPageFragment) {
                    fragment = null;
                } else {
                    fragment = new MainPageFragment();
                }
                break;
            case R.id.drawer_events_button:
                if (mCurrentPageFragment instanceof EventsFragment) {
                    fragment = null;
                } else {
                    fragment = new EventsFragment();
                }
                break;
            default:
                fragment = null;
                Log.e(TAG, "Button " + getString(buttonId) + " is not set up");
                break; // If a button is not set up then do nothing
        }



        if (fragment != null) {
            mChangeFragmentRunner = new Runnable() {
                @Override
                public void run() {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_overlay_container, fragment)
                            .commit();
                }
            };

//            toggleNavigationDrawer();
            mChangeFragmentHandler.postDelayed(mChangeFragmentRunner, mShowDrawerInterval);
        } else {
//            toggleNavigationDrawer();
        }
    }

    public void toggleNavigationDrawer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (!mIsDrawerShown) {
                mDrawerLayout.setVisibility(View.VISIBLE);
                mShowDrawerTransition.start();
                mIsDrawerShown = true;
            } else {
                mHideDrawerTransition.start();
                mDrawerVisibilityHandler.postDelayed(mHideDrawerRunner, mShowDrawerInterval);
                mIsDrawerShown = false;
            }
        }
    }

}
