package com.jaipurpinkpanthers.android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.fragments.AboutUsFragment;
import com.jaipurpinkpanthers.android.fragments.AlbumFragment;
import com.jaipurpinkpanthers.android.fragments.FanFragment;
import com.jaipurpinkpanthers.android.fragments.GalleryFragment;
import com.jaipurpinkpanthers.android.fragments.HomeFragment;
import com.jaipurpinkpanthers.android.fragments.JppTvFragment;
import com.jaipurpinkpanthers.android.fragments.MatchUpdateFragment;
import com.jaipurpinkpanthers.android.fragments.MatchUpdateFragment1;
import com.jaipurpinkpanthers.android.fragments.MerchandiseFragment;
import com.jaipurpinkpanthers.android.fragments.NavigationDrawerFragment;
import com.jaipurpinkpanthers.android.fragments.NewsDetailFragment;
import com.jaipurpinkpanthers.android.fragments.NewsFragment;
import com.jaipurpinkpanthers.android.fragments.PanthersFragment;
import com.jaipurpinkpanthers.android.fragments.PlayerDescriptionFragment;
import com.jaipurpinkpanthers.android.fragments.ScheduleFragment;
import com.jaipurpinkpanthers.android.fragments.SponsorsFragment;
import com.jaipurpinkpanthers.android.util.CalendarEvent;
import com.jaipurpinkpanthers.android.util.CustomFonts;
//import com.pushwoosh.BasePushMessageReceiver;
//import com.pushwoosh.BaseRegistrationReceiver;
//import com.pushwoosh.PushManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks {
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private static Boolean flag=true;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;

    private static ImageView ivToolbarImage;
    public static TextView tvToolbarText;
    private FrameLayout container;
    public static int PLAYER_ID = 12;
    public static int GALLERY_ID = 0;
    public ImageView ivHome, ivSchedule, ivGallery, ivNews, ivPanthers;
    boolean doubleBackToExitPressedOnce = false;
    boolean inMainActivity = true;
    public static HashMap<String, String> NEWSDETAIL;
    public static ArrayList<String> IMAGE_LINKS = new ArrayList<String>();
    View view;
    public FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        /*mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_panther));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
            }
        });*/


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


        /*********** PushWoosh **********/


        //Register receivers for push notifications
//        registerReceivers();
//
//        //Create and start push manager
//        PushManager pushManager = PushManager.getInstance(this);
//
//        //Start push manager, this will count app open for Pushwoosh stats as well
//        try {
//            pushManager.onStartup(this);
//        }
//        catch(Exception e)
//        {
//            //push notifications are not available or AndroidManifest.xml is not configured properly
//        }
//
//        //Register for push!
//        pushManager.registerForPushNotifications();
//
//        checkMessage(getIntent());

        /*************** Pushwoosh ************/


        ivToolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        tvToolbarText = (TextView) findViewById(R.id.toolbar_title);
        tvToolbarText.setTypeface(CustomFonts.getLightFont(this));

        tvToolbarText.setVisibility(View.GONE);

        container = (FrameLayout) findViewById(R.id.container);
        /*container.setBackgroundColor(getResources().getColor(R.color.jppPrimaryColor));*/

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);


        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();

        initializeViews();

        createDirectory();

        Intent intent = this.getIntent();
        //int fragmentId = 0;

        //fragmentId = if((Integer.parseInt(intent.getStringExtra("FragmentId"))));
        int fragmentId = intent.getIntExtra("FragmentId", 0);
        //fragmentId = if(!intent.getStringExtra("FragmentId").isEmpty())

        onNavigationDrawerItemSelected(fragmentId);

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("JPP inside", "oOIS");
        Log.e("JPP item", String.valueOf(item.getItemId()));

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }*/

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
//        if (position == 0) { // home
//            if (inMainActivity) {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        homeShow();
//                    }
//                }, 300);
//            }
//        }
//        if (position == 1) { // schedule
//            if (inMainActivity) {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        scheduleShow();
//                    }
//                }, 300);
//            }
//        }
//        if (position == 2) { // MATCH UPDATE
//            if (inMainActivity) {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        matchupdate();
//                    }
//                }, 300);
//
//            }
//        }
//        if (position == 3) { // gallery
//            if (inMainActivity) {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        galleryShow();
//                    }
//                }, 300);
//            }
//        }
//
//        if (position == 4) { // jpptv
//            if (inMainActivity) {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        videoShows();
//                    }
//                }, 300);
//            }
//        }
//        if (position == 5) { // news
//            if (inMainActivity) {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        newsShow();
//                    }
//                }, 300);
//            }
//        }
//        if (position == 6) { // knowPanthers
//            if (inMainActivity) {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        knowPanthersShow();
//                    }
//                }, 300);
//            }
//        }
//        if (position == 7) { // merchandise
//            if (inMainActivity) {
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        merchandise1();
//                    }
//                }, 300);
//            }
//        }
//        if (position == 8) { // wallpapers
//            if (inMainActivity) {
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        wallpapers1();
//                    }
//                }, 300);
//            }
//        }
//        if (position == 9) { // points table
//            if (inMainActivity) {
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        points1();
//                    }
//                }, 300);
//            }
//        }
//
//        if (position == 10) { // fan corner
//            if (inMainActivity) {
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        fan();
//                    }
//                }, 300);
//            }
//        }
//        if (position ==11) { // about us
//            if (inMainActivity) {
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        clearBackStackOfFragments(getFragmentManager());
//                        about1();
//
//                    }
//                }, 300);
//            }
//        }

        if (inMainActivity) {
            switch (position) {
                case 0:
                    clearBackStackOfFragments(getFragmentManager());
                    homeShow();
                    break;
                case 1:
                    clearBackStackOfFragments(getFragmentManager());
                    scheduleShow();
                    break;
//                case 1:
//                    clearBackStackOfFragments(getFragmentManager());
//                    matchupdate();
//                    break;
                case 2:
                    clearBackStackOfFragments(getFragmentManager());
                    matchupdate1();
                    break;
                case 3:
                    clearBackStackOfFragments(getFragmentManager());
                    galleryShow();
                    break;
                case 4:
                    clearBackStackOfFragments(getFragmentManager());
                    videoShows();
                    break;
                case 5:
                    clearBackStackOfFragments(getFragmentManager());
                    newsShow();
                    break;
                case 6:
                    clearBackStackOfFragments(getFragmentManager());
                    knowPanthersShow();
                    break;
//                case 7:
//                    clearBackStackOfFragments(getFragmentManager());
//                    merchandise1();
//                    break;
                case 7:
                    clearBackStackOfFragments(getFragmentManager());
                    wallpapers1();
                    break;
                case 8:
                    clearBackStackOfFragments(getFragmentManager());
                    points1();
                    break;
                case 9:
                    clearBackStackOfFragments(getFragmentManager());
                    fan();
                    break;
                case 10:
                    clearBackStackOfFragments(getFragmentManager());
                    about1();
                    break;
                case 11:
                    clearBackStackOfFragments(getFragmentManager());
                    sponsors1();
                    break;
                default:
                    homeShow();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        }  else if (getFragmentManager().getBackStackEntryCount() == 0){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                //super.onDestroy();
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit...", Toast.LENGTH_LONG).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void initializeViews() {


        initializeBottomToolbarViews();

    }

    public void initializeBottomToolbarViews() {
        ivHome = (ImageView) findViewById(R.id.ivHome);
        ivSchedule = (ImageView) findViewById(R.id.ivSchedule);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivNews = (ImageView) findViewById(R.id.ivNews);
        ivPanthers = (ImageView) findViewById(R.id.ivPanthers);
    }

    public void bottomViewClicked(int i) {

        ivHome.setImageResource(R.drawable.ic_bottom_home_color);
        ivSchedule.setImageResource(R.drawable.ic_bottom_schedule_color);
        ivGallery.setImageResource(R.drawable.ic_bottom_gallery_color);
        ivNews.setImageResource(R.drawable.ic_bottom_news_color);
        ivPanthers.setImageResource(R.drawable.ic_bottom_panthers_color);

        if (i == 1) {
            ivHome.setImageResource(R.drawable.ic_bottom_home_white);
        }
        else if (i == 2) {
            ivSchedule.setImageResource(R.drawable.ic_bottom_schedule_white);
        }
        else if (i == 3) {
            ivGallery.setImageResource(R.drawable.ic_bottom_gallery_white);
        } else if (i == 4) {
            ivNews.setImageResource(R.drawable.ic_bottom_news_white);
        } else if (i == 5) {
            ivPanthers.setImageResource(R.drawable.ic_bottom_panthers_white);
        }

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

    public void home(View v) {
        Log.v("JPP", "Home");
        homeShow();
    }

    public void homeShow() {
        tvOrImage(false, "");

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
       /* //fragmentTransaction.add(R.id.container, homeFragment, "HOME");
        fragmentTransaction.replace(R.id.container, homeFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, homeFragment, "homeFragment")
                .commit();
        bottomViewClicked(1);
    }

    public void schedule(View v) {
        Log.v("JPP", "SCHEDULE");
        scheduleShow();
    }

    public void scheduleShow() {
        tvOrImage(true, "SCHEDULE");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ScheduleFragment scheduleFragment = new ScheduleFragment();

        /*//fragmentTransaction.add(R.id.container, scheduleFragment, "SCHEDULE");
        fragmentTransaction.replace(R.id.container, scheduleFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, scheduleFragment)
                .commit();

        bottomViewClicked(2);
    }

    public void gallery(View v) {
        Log.v("JPP", "Inside Gallery");
        galleryShow();
    }

    public void galleryShow() {
        tvOrImage(true, "GALLERY");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GalleryFragment galleryFragment = new GalleryFragment();

        /*//fragmentTransaction.add(R.id.container, galleryFragment, "GALLERY");
        fragmentTransaction.replace(R.id.container, galleryFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, galleryFragment)
                .commit();

        bottomViewClicked(3);
    }
    public void jppTv(Context v){
        Log.v("JPP", "Inside Gallery");
        videoShows();
    }
    public void jppTv(View v){
        Log.v("JPP", "Inside Gallery");
        videoShows();
    }
    public void videoShows() {
        tvOrImage(true, "JPP TV");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        JppTvFragment jppTvFragment =new JppTvFragment();
       /* //fragmentTransaction.add(R.id.container, galleryFragment, "GALLERY");
        fragmentTransaction.replace(R.id.container, jppTvFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, jppTvFragment)
                .commit();
    }
    public void matchupdate1(View v){
        Log.v("JPP", "MATCH UPDATE");
        matchupdate1();
    }
    public void matchupdate1() {
        tvOrImage(true, "MATCH UPDATE");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MatchUpdateFragment matchUpdateFragment =new MatchUpdateFragment();
        /*fragmentTransaction.replace(R.id.container, matchUpdateFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, matchUpdateFragment,"matchUpdateFragment")
                .commit();

    }
    public void matchupdate(View v){
        Log.v("JPP", "WORLD CUP JOURNEY");
        matchupdate();
    }
    public void matchupdate() {
        tvOrImage(true, "WORLD CUP JOURNEY");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MatchUpdateFragment1 matchUpdateFragment1 =new MatchUpdateFragment1();
        /*fragmentTransaction.replace(R.id.container, matchUpdateFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, matchUpdateFragment1,"matchUpdateFragment")
                .commit();

    }
    public void news(View v) { ////left to do
        Log.v("JPP", "Inside News");
        newsShow();
    }

    public void newsShow() {
        tvOrImage(true, "NEWS & MEDIA");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NewsFragment newsFragment = new NewsFragment();
/*
        //fragmentTransaction.add(R.id.container, newsFragment, "NEWS");
        fragmentTransaction.replace(R.id.container, newsFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, newsFragment)
                .commit();

        bottomViewClicked(4);
    }




    public void knowPanthers(View v) {
        Log.v("JPP", "Know Your Panthers");
        knowPanthersShow();
    }

    public void knowPanthersShow() {
        tvOrImage(true, "KNOW YOUR PANTHERS");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PanthersFragment panthersFragment = new PanthersFragment();

       /* //fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, panthersFragment);
        fragmentTransaction.commit();
*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, panthersFragment)
                .commit();
        bottomViewClicked(5);
    }
    public void merchandise(View v)
    {
        Log.v("JPP", "TICKETS & MERCHANDISE");
        merchandise1();
    }
    public void merchandise1()
    {
        //startActivity(new Intent(MainActivity.this, MerchandiseActivity.class));
        tvOrImage(true, "MERCHANDISE");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MerchandiseFragment merchandiseFragment = new MerchandiseFragment();

        /*//fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, merchandiseFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, merchandiseFragment)
                .commit();
    }
    public void wallpapers(View v)
    {
        Log.v("JPP", "WALLPAPERS");
        wallpapers1();
    }
    public void wallpapers1()
    {
        startActivity(new Intent(MainActivity.this, WallpaperActivity.class));
    }
    public void points(View v)
    {
        Log.v("JPP", "POINTS");
        points1();
    }
    public void points1()
    {
        startActivity(new Intent(MainActivity.this, PointsActitivy.class));
    }

    public void fan1(View v) { ////left to do
        Log.v("JPP", "Sign Up");
        fan();
    }
    public void fan() {
        //startActivity(new Intent(MainActivity.this, FanActivity.class));
        tvOrImage(true, "FAN CORNER");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FanFragment fanFragment = new FanFragment();

        /*//fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, fanFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, fanFragment)
                .commit();
    }
    public void about(View v)
    {
        Log.v("JPP", "About Us");
        about1();
    }
    public void about1()
    {
        //startActivity(new Intent(MainActivity.this, AboutActivity.class));
        tvOrImage(true, "ABOUT US");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AboutUsFragment aboutFragment = new AboutUsFragment();

        /*//fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, aboutFragment);
        fragmentTransaction.commit();*/
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, aboutFragment)
                .commit();
    }

    public void sponsors(View v)
    {
        Log.v("JPP", "SPONSORS");
        sponsors1();
    }
    public void sponsors1()
    {
        //startActivity(new Intent(MainActivity.this, AboutActivity.class));
        tvOrImage(true, "SPONSORS");
        SponsorsFragment SponsorsFragment =new SponsorsFragment();

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim)
                .replace(R.id.container, SponsorsFragment)
                .commit();
    }
    public void removeFragment(String TAG_FRAGMENT) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void clearBackStackOfFragments(FragmentManager fragmentManager) {
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        Log.d("true","clearBackStackOfFragments");
    }

    public void pantherDescription(View v) {
        Log.v("JPP", "Panther Description");

        String tag = v.getTag().toString();
        List<String> playerInfoList = Arrays.asList(tag.split("#"));

        int id = Integer.parseInt(playerInfoList.get(0));
        String name = playerInfoList.get(1);

        setPlayerId(id);

        tvOrImage(true, name);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayerDescriptionFragment playerDescriptionFragment = new PlayerDescriptionFragment();

        //fragmentTransaction.add(R.id.container, panthersFragment, "PANTHERS");
        fragmentTransaction.replace(R.id.container, playerDescriptionFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void newsDetail(View v) {
        Log.v("JPP", "News Detail");
        String tag = v.getTag().toString();
        List<String> newsInfoList = Arrays.asList(tag.split("#"));

        String title = newsInfoList.get(0);
        String image = newsInfoList.get(1);
        String date = newsInfoList.get(2);
        String desc = newsInfoList.get(3);
        if (!desc.startsWith("http")) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", title);
            map.put("date", date);
            map.put("desc", desc);
            map.put("image", image);

            setNewsDetails(map);

            tvOrImage(true, "NEWS & MEDIA");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            NewsDetailFragment newsDetailFragment = new NewsDetailFragment();

            fragmentTransaction.replace(R.id.container, newsDetailFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(MainActivity.this, WebActivity.class);
            intent.putExtra("webLink", desc);
            startActivity(intent);
        }
    }

    public void goToGallery(View v) {
        String tag = v.getTag().toString();
        List<String> galleryInfoList = Arrays.asList(tag.split("#"));

        int id = Integer.parseInt(galleryInfoList.get(0));
        String name = galleryInfoList.get(1);
        setGalleryId(id);

        tvOrImage(true, name);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AlbumFragment albumFragment = new AlbumFragment();

        fragmentTransaction.replace(R.id.container, albumFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static void setNewsDetails(HashMap<String, String> news) {
        NEWSDETAIL = news;
    }

    public static HashMap<String, String> getNewsDetails() {
        return NEWSDETAIL;
    }

    public static void setGalleryId(int id) {
        GALLERY_ID = id;
    }

    public static int getGalleryId() {
        return GALLERY_ID;
    }

    public static void setPlayerId(int id) {
        PLAYER_ID = id;
    }

    public static int getPlayerId() {
        return PLAYER_ID;
    }

    public static void setToolbarText(String text) {
        tvToolbarText.setText(text);
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

    public void addToCalendar(View v) {
        String tag = v.getTag().toString();
        CalendarEvent.remind(this, tag);
    }

    public void goToBook (View v) {
        Intent intent = new Intent(MainActivity.this, WebActivity.class);
        intent.putExtra("webLink", "http://in.bookmyshow.com/sports/kabaddi/jaipur-pink-panthers/?utm_source=web_prokabaddi&utm_medium=referral&utm_campaign=web_prokabaddi_011816");
        startActivity(intent);
    }

    public void goToYoutube(View v) {
        String tag = v.getTag().toString();
        Intent intent = new Intent(MainActivity.this, WebActivity.class);
        intent.putExtra("webLink", tag);
        startActivity(intent);
    }


    public void openSlideShowActivity(View v) {
        String position = v.getTag().toString();
        Intent intent = new Intent(MainActivity.this, SlideShowActivity.class);
        intent.putExtra("wallpaper","0");
        intent.putExtra("position", position);
        intent.putStringArrayListExtra("links", IMAGE_LINKS);
        startActivity(intent);
    }

    public void createDirectory() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        if (isExternalStorageAvailable()) {
            // get the URI

            // 1. Get the external storage directory
            String appName = MainActivity.this.getString(R.string.app_name);
            String imgDir = "/JPP/JPP Images";
            //String audioDir = "/NoteShare/NoteShare Audio";
            //String extraDir = "/NoteShare/.NoteShare";

            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), appName);
            // 2. Create our subdirectory
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.e("JPP", "Failed to create NoteShare directory.");
                }
            }

            // 3. Creating Image Directory in NoteShare Directory
            File imgDirectory = new File(Environment.getExternalStorageDirectory(), imgDir);
            if (!imgDirectory.exists()) {
                if (!imgDirectory.mkdirs()) {
                    Log.e("JPP", "Failed to create Image directory.");
                }
            }

        }

    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }



    //pushwoosh
//
//    //Registration receiver
//    BroadcastReceiver mBroadcastReceiver = new BaseRegistrationReceiver()
//    {
//        @Override
//        public void onRegisterActionReceive(Context context, Intent intent)
//        {
//            checkMessage(intent);
//        }
//    };
//
//    //Push message receiver
//    private BroadcastReceiver mReceiver = new BasePushMessageReceiver()
//    {
//        @Override
//        protected void onMessageReceive(Intent intent)
//        {
//            //JSON_DATA_KEY contains JSON payload of push notification.
//            showMessage("push message is " + intent.getExtras().getString(JSON_DATA_KEY));
//        }
//    };
//
//    //Registration of the receivers
//    public void registerReceivers()
//    {
//        IntentFilter intentFilter = new IntentFilter(getPackageName() + ".action.PUSH_MESSAGE_RECEIVE");
//
//        registerReceiver(mReceiver, intentFilter, getPackageName() +".permission.C2D_MESSAGE", null);
//
//        registerReceiver(mBroadcastReceiver, new IntentFilter(getPackageName() + "." + PushManager.REGISTER_BROAD_CAST_ACTION));
//    }
//
//    public void unregisterReceivers()
//    {
//        //Unregister receivers on pause
//        try
//        {
//            unregisterReceiver(mReceiver);
//        }
//        catch (Exception e)
//        {
//            // pass.
//        }
//
//        try
//        {
//            unregisterReceiver(mBroadcastReceiver);
//        }
//        catch (Exception e)
//        {
//            //pass through
//        }
//    }
//
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//
//        //Re-register receivers on resume
//        registerReceivers();
//    }
//
//    @Override
//    public void onPause()
//    {
//        super.onPause();
//
//        //Unregister receivers on pause
//        unregisterReceivers();
//    }
//
//
//    private void checkMessage(Intent intent)
//    {
//        if (null != intent)
//        {
//            if (intent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
//            {
//                showMessage("push message is " + intent.getExtras().getString(PushManager.PUSH_RECEIVE_EVENT));
//            }
//            else if (intent.hasExtra(PushManager.REGISTER_EVENT))
//            {
//                showMessage("register");
//            }
//            else if (intent.hasExtra(PushManager.UNREGISTER_EVENT))
//            {
//                showMessage("unregister");
//            }
//            else if (intent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
//            {
//                showMessage("register error");
//            }
//            else if (intent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
//            {
//                showMessage("unregister error");
//            }
//
//            resetIntentValues();
//        }
//    }
//
//    /**
//     * Will check main Activity intent and if it contains any PushWoosh data, will clear it
//     */
//    private void resetIntentValues()
//    {
//        Intent mainAppIntent = getIntent();
//
//        if (mainAppIntent.hasExtra(PushManager.PUSH_RECEIVE_EVENT))
//        {
//            mainAppIntent.removeExtra(PushManager.PUSH_RECEIVE_EVENT);
//        }
//        else if (mainAppIntent.hasExtra(PushManager.REGISTER_EVENT))
//        {
//            mainAppIntent.removeExtra(PushManager.REGISTER_EVENT);
//        }
//        else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_EVENT))
//        {
//            mainAppIntent.removeExtra(PushManager.UNREGISTER_EVENT);
//        }
//        else if (mainAppIntent.hasExtra(PushManager.REGISTER_ERROR_EVENT))
//        {
//            mainAppIntent.removeExtra(PushManager.REGISTER_ERROR_EVENT);
//        }
//        else if (mainAppIntent.hasExtra(PushManager.UNREGISTER_ERROR_EVENT))
//        {
//            mainAppIntent.removeExtra(PushManager.UNREGISTER_ERROR_EVENT);
//        }
//
//        setIntent(mainAppIntent);
//    }

//    private void showMessage(String message)
//    {
//        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//    }
//
//
//    @Override
//    protected void onNewIntent(Intent intent)
//    {
//        super.onNewIntent(intent);
//        setIntent(intent);
//
//        checkMessage(intent);
//    }


}
