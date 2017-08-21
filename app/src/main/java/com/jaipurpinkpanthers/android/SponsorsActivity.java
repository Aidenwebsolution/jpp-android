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

import com.jaipurpinkpanthers.android.fragments.AboutUsFragment;
import com.jaipurpinkpanthers.android.fragments.NavigationDrawerFragment;
import com.jaipurpinkpanthers.android.fragments.SponsorsFragment;
import com.jaipurpinkpanthers.android.util.CustomFonts;

/**
 * Created by wohlig on 5/8/17.
 */

public class SponsorsActivity extends ActionBarActivity
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
    MainActivity ma=new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ivToolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        tvToolbarText = (TextView) findViewById(R.id.toolbar_title);
        tvToolbarText.setTypeface(CustomFonts.getLightFont(this));

        //tvToolbarText.setVisibility(View.GONE);
        ivToolbarImage.setVisibility(View.GONE);
        tvToolbarText.setText("SPONSORS");

        onTrimMemory(TRIM_MEMORY_BACKGROUND);

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
        // update the main content by replacing fragments
        if (position == 0) { // home
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goHome(SponsorsActivity.this);
                    finish();
                }
            }, 300);
        }
        if (position == 1) { // schedule
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goSchedule(SponsorsActivity.this);
                    finish();
                }
            }, 300);

        }
//        if (position == 1) { // MATCH UPDATE
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ma.clearBackStackOfFragments(getFragmentManager());
//                    GoToMainFragments.gomatchupdate(SponsorsActivity.this);
//                    finish();
//                }
//            }, 300);
//
//        }
        if (position == 2) { // MATCH UPDATE
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.gomatchupdate1(SponsorsActivity.this);
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
                    GoToMainFragments.goGallery(SponsorsActivity.this);
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
                    GoToMainFragments.goJppTv(SponsorsActivity.this);
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
                    GoToMainFragments.goNews(SponsorsActivity.this);
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
                    GoToMainFragments.goPanthers(SponsorsActivity.this);
                    finish();
                }
            }, 300);
        }
//        if (position == 7) { // tickets
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ma.clearBackStackOfFragments(getFragmentManager());
//                    startActivity(new Intent(SponsorsActivity.this, MerchandiseActivity.class));
//                    finish();
//                }
//            }, 300);
//        }
        if (position == 7) { // wallpaper
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(SponsorsActivity.this, WallpaperActivity.class));
                    finish();
                }
            }, 300);
        }
        if (position == 8) { // points table
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(SponsorsActivity.this, PointsActitivy.class));
                    finish();
                }
            }, 300);
        }

        if (position == 9) { // fan corner
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(SponsorsActivity.this, FanActivity.class));
                    finish();
                }
            }, 300);
        }
        if (position == 10) { // about us
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent( SponsorsActivity.this,AboutActivity.class));
                    finish();
                }
            }, 300);
        }if (position == 11) { // Sponsors

        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        } else if (getFragmentManager().getBackStackEntryCount() == 0) {
            //if (doubleBackToExitPressedOnce){
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
        SponsorsFragment sponsorsFragment = new SponsorsFragment();

        fragmentTransaction.add(R.id.container, sponsorsFragment);
        //fragmentTransaction.replace(R.id.container, scheduleFragment);
        fragmentTransaction.commit();

    }

    public void home(View v) {
        Log.v("JPP", "Home");
        GoToMainFragments.goHome(this);
        finish();
    }

//    public void schedule(View v) {
//        Log.v("JPP", "Schedule");
//        GoToMainFragments.goSchedule(this);
//        finish();
//    }

    public void gallery(View v) {
        Log.v("JPP", "Gallery");
        GoToMainFragments.goGallery(this);
        finish();
    }

    public void news(View v) {
        Log.v("JPP", "News");
        GoToMainFragments.goNews(this);
        finish();
    }

    public void knowPanthers(View v) {
        Log.v("JPP", "Panthers");
        GoToMainFragments.goPanthers(this);
        finish();
    }


}
