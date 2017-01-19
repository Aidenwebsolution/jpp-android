package com.jaipurpinkpanthers.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaipurpinkpanthers.android.fragments.FanFragment;
import com.jaipurpinkpanthers.android.fragments.NavigationDrawerFragment;
import com.jaipurpinkpanthers.android.util.CustomFonts;

import java.util.ArrayList;

public class FanActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private static ImageView ivToolbarImage;
    private static TextView tvToolbarText;
    private FrameLayout container;
    public static int PLAYER_ID = 12;
    public ImageView ivHome, ivSchedule, ivGallery, ivNews, ivPanthers;
    boolean doubleBackToExitPressedOnce = false;
    boolean inMainActivity = true;
    public ArrayList<String> selections = new ArrayList<String>();
    View v;
    MainActivity  ma=new MainActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ivToolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        tvToolbarText = (TextView) findViewById(R.id.toolbar_title);
        tvToolbarText.setTypeface(CustomFonts.getLightFont(this));

        //tvToolbarText.setVisibility(View.GONE);
        ivToolbarImage.setVisibility(View.GONE);
        tvToolbarText.setText("FAN CORNER");

        container = (FrameLayout) findViewById(R.id.container);
        /*container.setBackgroundColor(getResources().getColor(R.color.jppPrimaryColor));*/

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);


        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();

        initializeViews();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragmentss
        if (position == 0) { // home
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goHome(FanActivity.this);
                    finish();
                }
            }, 300);
        }
//        if (position == 1) { // schedule
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ma.clearBackStackOfFragments(getFragmentManager());
//                    GoToMainFragments.goSchedule(FanActivity.this);
//                    finish();
//                }
//            }, 300);
//
//        }
        if (position == 1) { // MATCH UPDATE
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.gomatchupdate(FanActivity.this);
                    finish();
                }
            }, 300);

        }
        if (position == 2) { // MATCH UPDATE
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.gomatchupdate1(FanActivity.this);
                    finish();
                }
            }, 300);

        }
        if (position == 3) { // gallery
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goGallery(FanActivity.this);
                    finish();
                }
            }, 300);
        }
        if (position == 4) { // jpptv
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    ma.videoShows();
                    finish();
                }
            }, 300);
        }
        if (position == 5) { // news
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goNews(FanActivity.this);
                    finish();
                }
            }, 300);
        }
        if (position == 6) { // knowPanthers
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goPanthers(FanActivity.this);
                    finish();
                }
            }, 300);
        }
        if (position == 7) { // tickets
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(FanActivity.this, MerchandiseActivity.class));
                    finish();
                }
            }, 300);
        }
        if (position == 8) { // wallpaper
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(FanActivity.this, WallpaperActivity.class));
                    finish();
                }
            }, 300);
        }
        if (position == 9) { // points table
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(FanActivity.this, PointsActitivy.class));
                    finish();
                }
            }, 300);
        }

        if (position == 10) { // fan corner

        }
        if (position == 11) { // about us
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(FanActivity.this, AboutActivity.class));
                    finish();
                }
            }, 300);
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else if (getFragmentManager().getBackStackEntryCount() == 0) {
            //if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
            finish();
            //super.onDestroy();
                //return;
            /*}

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit...", Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);*/
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void initializeViews() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FanFragment fanFragment = new FanFragment();

        fragmentTransaction.add(R.id.container, fanFragment);
        //fragmentTransaction.replace(R.id.container, scheduleFragment);
        fragmentTransaction.commit();

    }

    public void home(View v) {
        Log.v("JPP", "Home");
        GoToMainFragments.goHome(this);
    }

//    public void schedule(View v) {
//        Log.v("JPP", "Schedule");
//        GoToMainFragments.goSchedule(this);
//    }

    public void gallery(View v) {
        Log.v("JPP", "Gallery");
        GoToMainFragments.goGallery(this);
    }

    public void news(View v) {
        Log.v("JPP", "News");
        GoToMainFragments.goNews(this);
    }

    public void knowPanthers(View v) {
        Log.v("JPP", "Panthers");
        GoToMainFragments.goPanthers(this);
    }


    public static void tvOrImage(boolean tv, String header) {
        if (tv) {
            ivToolbarImage.setVisibility(View.GONE);
            tvToolbarText.setVisibility(View.VISIBLE);
            tvToolbarText.setText(header);
        } else {
            tvToolbarText.setVisibility(View.GONE);
            ivToolbarImage.setVisibility(View.VISIBLE);
        }
    }

}
