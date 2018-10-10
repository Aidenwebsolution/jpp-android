package com.jaipurpinkpanthers.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarActivity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jaipurpinkpanthers.android.fragments.NavigationDrawerFragment;
import com.jaipurpinkpanthers.android.fragments.WallpaperFragment;
import com.jaipurpinkpanthers.android.util.CustomFonts;

import java.util.ArrayList;

public class WallpaperActivity extends AppCompatActivity
        implements NavigationDrawerCallbacks {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private static ImageView ivToolbarImage;
    private static TextView tvToolbarText;
    public ImageView ivGallery;
    private FrameLayout container;
    boolean doubleBackToExitPressedOnce = false;
    public static ArrayList<String> IMAGE_LINKS = new ArrayList<String>();
    MainActivity ma = new MainActivity();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ivToolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        tvToolbarText = (TextView) findViewById(R.id.toolbar_title);
        tvToolbarText.setTypeface(CustomFonts.getLightFont(this));

        //tvToolbarText.setVisibility(View.GONE);
        ivToolbarImage.setVisibility(View.GONE);
        tvToolbarText.setText("WALLPAPERS");

        container = (FrameLayout) findViewById(R.id.container);
        /*container.setBackgroundColor(getResources().getColor(R.color.jppPrimaryColor));*/

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);


        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();

        initializeViews();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
                    GoToMainFragments.goHome(WallpaperActivity.this);
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
                    GoToMainFragments.goSchedule(WallpaperActivity.this);
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
//                    GoToMainFragments.gomatchupdate(WallpaperActivity.this);
//                    finish();
//                }
//            }, 300);
//
//        }
//        if (position == 2) { // MATCH UPDATE
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ma.clearBackStackOfFragments(getFragmentManager());
//                    GoToMainFragments.gomatchupdate1(WallpaperActivity.this);
//                    finish();
//                }
//            }, 300);
//
//        }
        if (position == 2) { // gallery
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goGallery(WallpaperActivity.this);
                    finish();
                }
            }, 300);
        }
        if (position == 3) { // jpptv
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goJppTv(WallpaperActivity.this);
                    finish();
                }
            }, 300);
        }
        if (position == 4) { // news
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goNews(WallpaperActivity.this);
                    finish();
                }
            }, 300);
        }
        if (position == 5) { // knowPanthers
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    GoToMainFragments.goPanthers(WallpaperActivity.this);
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
//                    startActivity(new Intent(WallpaperActivity.this, MerchandiseActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
//                    finish();
//                }
//            }, 300);
//        }
        if (position == 6) { // wallpaper

        }
        if (position == 7) { // points table
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(WallpaperActivity.this, PointsActitivy.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                  finish();
                }
            }, 300);
        }

        if (position == 8) { // fan corner
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(WallpaperActivity.this, FanActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                    finish();
                }
            }, 300);
        }
        if (position == 9) { // about us
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(WallpaperActivity.this, AboutActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                    finish();
                }
            }, 300);
        }
        if (position == 10) { // Sponsors
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ma.clearBackStackOfFragments(getFragmentManager());
                    startActivity(new Intent(WallpaperActivity.this, SponsorsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                    finish();
                }
            }, 300);
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        }  else if (getFragmentManager().getBackStackEntryCount() == 0) {

          // if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
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
            //FragmentManager manager = getFragmentManager();
           // FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            //manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
        WallpaperFragment wallpaperFragment = new WallpaperFragment();

        /*fragmentTransaction.add(R.id.container, wallpaperFragment);
        //fragmentTransaction.replace(R.id.container, scheduleFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, wallpaperFragment)
                .commit();

    }

    public void home(View v) {
        GoToMainFragments.goHome(this);
        finish();
    }

//    public void schedule(View v) {
//        GoToMainFragments.goSchedule(this);
//        finish();
//    }

    public void gallery(View v) {
        GoToMainFragments.goGallery(this);
        finish();
    }

    public void news(View v) {
        GoToMainFragments.goNews(this);
        finish();
    }

    public void knowPanthers(View v) {
        GoToMainFragments.goPanthers(this);
        finish();
    }

    public static ArrayList<String> getImageLinks() {
        return IMAGE_LINKS;
    }

    public static void setImageLinks(ArrayList<String> links) {
        if (IMAGE_LINKS.size() > 0) {
            IMAGE_LINKS.clear();
        }
        IMAGE_LINKS = links;
    }

    public void openSlideShowActivity(View v) {
        String position = v.getTag().toString();
        Intent intent = new Intent(WallpaperActivity.this, SlideShowActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("wallpaper", "1");
        intent.putStringArrayListExtra("links", IMAGE_LINKS);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Wallpaper Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.jaipurpinkpanthers.android/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Wallpaper Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app URL is correct.
//                Uri.parse("android-app://com.jaipurpinkpanthers.android/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
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