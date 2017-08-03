package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.jaipurpinkpanthers.android.MainActivity;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.ViewPagerAdapter;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.InternetOperations;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts,tvmonth,tvdays,tvhours,tvmins;
    LinearLayout llLatestUpdate, llNews, llTable, llPoints,llupcomingmatch, lljpptv, llsignUp,llLiveupdate;
    ImageView ivT1, ivT2, ivNews, ivHomeMain,llivT1live,llivT2live;
    int tvS1,tvS2;
//    TextView tvS2,tvS1;
    TextView tvCo;
    TextView tvVenue;
    TextView tvTime;
    TextView tvMatchTime;
    TextView tvlivescore1;
    TextView tvlivescore2;
    TextView hftime;
    TextView tvNewsHead, tvNewsDesc, tvNewsDate, tvNewsRead;
    String newsTitle = null, newsImage = null, newsTime = null, newsContent ="";
    String imageLink = null;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    ArrayList<HashMap<String, String>> list;
    String team1Id = null, team2Id = null, team1 = null, team2 = null, team1Pts = null, team2Pts = null, venue = "--", time = null, matchTime = null ,bannerimg =null;
    String teamlive1= null,teamlive2= null ,team1logo,team2logo,level;
    ListView lvTeams;
    RelativeLayout ll2, ll3,ll4,ll5,rlsponsers;
    FrameLayout ll1,llLiveUpdatescore;
    ProgressDialog progressDialog;
    Activity activity;
    RelativeLayout flReview,showcongrats;
    ImageView ivReview,ivsponsor,ivpoints,ivsignup;
    LinearLayout llSeasonReview;
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView tvsignUp;
    Button bsignUp,addtocalender;
    private Handler handler;
    private  Runnable runnable;
    HashMap<String, String> single;
    String tagMain ,appImg,appImgStatus,sponsorimg;
    long startTime = 1000;

    long interval = 1000;
    CountDownTimer countDownTimer;
    ImageView homecongrats,iv_jpptv;
    //RelativeLayout relativeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) this.getActivity()).tvOrImage(false, "");

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisc(false).resetViewBeforeLoading(true).build();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.jppAccentColor,R.color.myPrimaryColor);
        //frameLayout=(FrameLayout)view.findViewById(R.id.home_container);

        //relativeLayout=(RelativeLayout)view.findViewById(R.id.home_congrats);

        activity = getActivity();
        list = new ArrayList<HashMap<String, String>>();
        homecongrats=(ImageView)view.findViewById(R.id.home_congrats);
        iv_jpptv=(ImageView)view.findViewById(R.id.home_iv_jpptv);
        ivsponsor=(ImageView)view.findViewById(R.id.home_iv_sponsor);
        showcongrats=(RelativeLayout)view.findViewById(R.id.congrats);

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                activity)
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP

        progressDialog = new ProgressDialog(activity);
        /*progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();*/







        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initilizeViews();
                //getdata();
            }
        });



        initilizeViews();
        //getdata();

        countDownTimer = new MyCountDownTimer(startTime, interval);
        //countDownTimer.start();


        return view;
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long startTime, long interval) {

            super(startTime, interval);

        }

        @Override

        public void onFinish() {
            initilizeViews();
            //getdata();
            Log.e("onfinish","onfinish");
        }

        @Override

        public void onTick(long millisUntilFinished) {


        }

    }

    @Override
    public void onRefresh() {

//        getdata();
        if (InternetOperations.checkIsOnlineViaIP()) {
//            getdata();
            getHomeContentData();
        } else {
            progressDialog.dismiss();
            Toast.makeText(activity, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
        //Toast.makeText(activity,"Refreshed!",Toast.LENGTH_SHORT).show();
    }

    public void initilizeViews() {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(this);
        //swipeRefreshLayout.setRefreshing(true);

        ll1 = (FrameLayout) view.findViewById(R.id.ll1);
        ll2 = (RelativeLayout) view.findViewById(R.id.ll2);
        ll3 = (RelativeLayout) view.findViewById(R.id.ll3);
        ll4 = (RelativeLayout) view.findViewById(R.id.ll4);
        ll5 =(RelativeLayout) view.findViewById(R.id.ll5);
        rlsponsers =(RelativeLayout) view.findViewById(R.id.sponsers);

        //lljpptv = (LinearLayout) view.findViewById(R.id.lljpptv);
        llsignUp = (LinearLayout) view.findViewById(R.id.llsignUp);
        llLiveUpdatescore = (FrameLayout) view.findViewById(R.id.llLiveUpdatescore);

        flReview = (RelativeLayout) view.findViewById(R.id.flReview);

        list = new ArrayList<HashMap<String, String>>();
        lvTeams = (ListView) view.findViewById(R.id.lvTeams);
        llPoints = (LinearLayout) view.findViewById(R.id.llPoints);
        ivHomeMain = (ImageView) view.findViewById(R.id.ivHomeMain);
        addtocalender= (Button) view.findViewById(R.id.bAddToCalendar);
        addtocalender.setTypeface(CustomFonts.getRegularFont(activity));

        // ivReview = (ImageView) view.findViewById(R.id.ivReview);

//        String imageUri = "drawable://" + R.drawable.schedule_back;
//        imageLoader.displayImage(imageUri, ivHomeMain, options);
        //imageLoader.displayImage(imageUri, ivReview, options);


        tvNo = (TextView) view.findViewById(R.id.tvNo);
        tvTeam = (TextView) view.findViewById(R.id.tvTeam);
        tvP = (TextView) view.findViewById(R.id.tvP);
        tvW = (TextView) view.findViewById(R.id.tvW);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvPts = (TextView) view.findViewById(R.id.tvPts);

        llLatestUpdate = (LinearLayout) view.findViewById(R.id.llLatestUpdate);
        TextView tvLatest = (TextView) llLatestUpdate.findViewById(R.id.tvCrossHeader);
        tvLatest.setTypeface(CustomFonts.getRegularFont(activity));
//        tvLatest.setText("LIVE UPDATE");

        llLiveupdate = (LinearLayout) view.findViewById(R.id.llLiveUpdate);
        TextView tvmatchupdate = (TextView) llLiveupdate.findViewById(R.id.tvCrossHeader);
        tvmatchupdate.setTypeface(CustomFonts.getRegularFont(activity));
//        tvmatchupdate.setText("MATCH UPDATE");

        llNews = (LinearLayout) view.findViewById(R.id.llNews);
        TextView tvNews = (TextView) llNews.findViewById(R.id.tvCrossHeader);
        tvNews.setTypeface(CustomFonts.getRegularFont(activity));
        tvNews.setText("NEWS");

        llTable = (LinearLayout) view.findViewById(R.id.llTable);
        TextView tvTable = (TextView) llTable.findViewById(R.id.tvCrossHeader);
        tvTable.setTypeface(CustomFonts.getRegularFont(activity));
        tvTable.setText("POINTS TABLE");

        llSeasonReview = (LinearLayout) view.findViewById(R.id.llReview);
        TextView tvReview = (TextView) llSeasonReview.findViewById(R.id.tvCrossHeader);
        tvReview.setTypeface(CustomFonts.getRegularFont(activity));
        tvReview.setText("SEASON 3 REVIEW");

        tvsignUp=(TextView)view.findViewById(R.id.tvsignUp);
        tvsignUp.setTypeface(CustomFonts.getBoldFont(activity));

        bsignUp=(Button)view.findViewById(R.id.bsignUp);
        bsignUp.setTypeface(CustomFonts.getBoldFont(activity));

        tvNo.setTypeface(CustomFonts.getRegularFont(activity));
        tvTeam.setTypeface(CustomFonts.getRegularFont(activity));
        tvP.setTypeface(CustomFonts.getRegularFont(activity));
        tvW.setTypeface(CustomFonts.getRegularFont(activity));
        tvL.setTypeface(CustomFonts.getRegularFont(activity));
        tvPts.setTypeface(CustomFonts.getRegularFont(activity));

        ivT1 = (ImageView) view.findViewById(R.id.llivT1);
        ivT2 = (ImageView) view.findViewById(R.id.llivT2);


        llivT1live = (ImageView) view.findViewById(R.id.llivT1live);
        llivT2live = (ImageView) view.findViewById(R.id.llivT2live);
        hftime = (TextView) view.findViewById(R.id.halftime_fulltime);
        hftime.setTypeface(CustomFonts.getRegularFont(activity));

        tvlivescore1 = (TextView) view.findViewById(R.id.tvlivescore1);
        tvlivescore2 = (TextView) view.findViewById(R.id.tvlivescore2);

        tvlivescore1.setTypeface(CustomFonts.getScoreFont(activity));
        tvlivescore2.setTypeface(CustomFonts.getScoreFont(activity));

//        tvS1 = (TextView) view.findViewById(R.id.tvS1);
//        tvS2 = (TextView) view.findViewById(R.id.tvS2);
//        tvCo = (TextView) view.findViewById(R.id.tvCo);
//        tvS1.setTypeface(CustomFonts.getBoldFont(activity));
//        tvS2.setTypeface(CustomFonts.getBoldFont(activity));
//        tvCo.setTypeface(CustomFonts.getBoldFont(activity));


        tvMatchTime = (TextView) view.findViewById(R.id.lltvMatchTime);
        tvVenue = (TextView) view.findViewById(R.id.lltvVenue);
        //tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvMatchTime.setTypeface(CustomFonts.getLightFont(activity));
        tvVenue.setTypeface(CustomFonts.getLightFont(activity));
        //tvTime.setTypeface(CustomFonts.getLightFont(activity));
        tvmonth= (TextView) view.findViewById(R.id.tvmonth);
        tvmonth.setTypeface(CustomFonts.getRegularFont(activity));
        tvdays= (TextView) view.findViewById(R.id.tvdays);
        tvdays.setTypeface(CustomFonts.getRegularFont(activity));
        tvhours= (TextView) view.findViewById(R.id.tvhours);
        tvhours.setTypeface(CustomFonts.getRegularFont(activity));
        tvmins= (TextView) view.findViewById(R.id.tvmins);
        tvmins.setTypeface(CustomFonts.getRegularFont(activity));



        tvNewsHead = (TextView) view.findViewById(R.id.tvNewsHead);
        tvNewsDesc = (TextView) view.findViewById(R.id.tvNewsDesc);
        tvNewsDate = (TextView) view.findViewById(R.id.tvNewsDate);
        tvNewsRead = (TextView) view.findViewById(R.id.tvNewsRead);
        ivNews = (ImageView) view.findViewById(R.id.ivNews);
        ivpoints = (ImageView) view.findViewById(R.id.ivpoints);
        ivsignup = (ImageView) view.findViewById(R.id.ivsignup);

        tvNewsHead.setTypeface(CustomFonts.getBoldFont(activity));
        tvNewsDesc.setTypeface(CustomFonts.getLightFont(activity));
        tvNewsDate.setTypeface(CustomFonts.getLightFont(activity));
        tvNewsRead.setTypeface(CustomFonts.getLightFont(activity));


        // mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        //viewPagerAdapter = new ViewPagerAdapter();

        //mViewPager.setAdapter(viewPagerAdapter);

//        getdata();

        if (InternetOperations.checkIsOnlineViaIP()) {

            getHomeContentData();

        } else {
            progressDialog.dismiss();
            Toast.makeText(activity, "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    boolean a = false, b = false, c = false, d = false, e = false, f = false, jpptv = false, signUp = false,livescore=false,sponsers=true;

    public void getHomeContentData() {

        new AsyncTask<Void, Void, String>() {
            boolean done = false;

            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                JSONObject jsonObject = null;


                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getHomeContent");

                    jsonObject = new JSONObject(response);
                    Log.d("jsonObject 222", String.valueOf(jsonObject));

                    //live update
                    try {
                        JSONObject sponsorimage = new JSONObject(jsonObject.optString("sponsorimage"));
                        sponsorimg =sponsorimage.optString("image");

                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        //llLiveupdate.setVisibility(View.GONE);
                        livescore = true;
                    }
                    try {
                        JSONObject apphomeimage = new JSONObject(jsonObject.optString("apphomeimage"));
                        Log.d("appImg", String.valueOf(apphomeimage));
//                        homecongrats=(ImageView)view.findViewById(R.id.home_congrats);
//
                        appImg =apphomeimage.optString("image");
                        appImgStatus =apphomeimage.optString("status");

//                        Log.d("appImg", appImg);
//                        Glide.with(activity)
//                                .load(InternetOperations.SERVER_UPLOADS_URL+appImg)
//                                .asBitmap()
//                                .into(homecongrats);

                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        //llLiveupdate.setVisibility(View.GONE);
                        livescore = true;
                    }
                      //live update
                    try {
                        JSONObject latestUpdate = new JSONObject(jsonObject.optString("latestMatch"));
//                        response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getLatestMatch");
//
//                        latestUpdate = new JSONObject(response);
                        Log.d("res data", latestUpdate.toString());

                        teamlive1 = latestUpdate.optString("team1");
                        teamlive2 = latestUpdate.optString("team2");
//                        tvS1+=1;
//                        tvS2+=2;
//                        team1Pts= String.valueOf(tvS1);
//                        team2Pts= String.valueOf(tvS2);
//                        team1Id="3";
//                        team2Id="4";
                        team1Pts = latestUpdate.optString("score1");
                        team2Pts = latestUpdate.optString("score2");
                        venue = latestUpdate.optString("stadium");
                        time = latestUpdate.optString("starttimedate");
                        team1Id = latestUpdate.optString("team1id");
                        team2Id = latestUpdate.optString("team2id");
                        team1logo = latestUpdate.optString("appteamimage1");
                        team2logo = latestUpdate.optString("appteamimage2");
                        matchTime = latestUpdate.optString("totalmatchtime");
                        level = latestUpdate.optString("level");


                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        //llLiveupdate.setVisibility(View.GONE);
                        livescore = true;
                    }
                    try {
                        JSONObject latestUpdate = new JSONObject(jsonObject.optString("latestMatch"));
//                        response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getLatestMatch");
//                        latestUpdate = new JSONObject(response);
                        Log.d("res data", latestUpdate.toString());
                        team1 = latestUpdate.optString("team1");
                        team2 = latestUpdate.optString("team2");
                        venue = latestUpdate.optString("stadium");
                       time = latestUpdate.optString("starttimedate");
//                      team1Id="3";
//                        team2Id="4";
                       team1Id = latestUpdate.optString("team1id");
                       team2Id = latestUpdate.optString("team2id");
                        team1logo = latestUpdate.optString("appteamimage1");
                       team2logo = latestUpdate.optString("appteamimage2");
                       //addMatch( venue, time, team1Id, team2Id);
                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        //ll1.setVisibility(View.GONE);
                        a = true;
                    }
                    try {
                        JSONObject latestNews = new JSONObject(jsonObject.optString("news"));
                        Log.d("news", String.valueOf(latestNews));
                        newsTitle = latestNews.optString("name");
                        newsImage = latestNews.optString("image");
                        newsTime = latestNews.optString("timestamp");
                        newsContent = latestNews.optString("content");
                        imageLink = InternetOperations.SERVER_UPLOADS_URL + newsImage;
                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        b = true;
                    }

                    try {
                        String jObjectString = jsonObject.optString("points");
                        JSONArray jsonArray = new JSONArray(jObjectString);

                        if (jsonArray.length() != 0) {

                            if (list.size() > 0) { //need to clear the list if pull to refresh is initiated
                                list.clear();
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectPts = jsonArray.getJSONObject(i);
                                String id = String.valueOf(i + 1);
                                String name = jsonObjectPts.optString("name");
                                String p = jsonObjectPts.optString("played");
                                String w = jsonObjectPts.optString("wins");
                                String l = jsonObjectPts.optString("lost");
                                String points = jsonObjectPts.optString("point");
                                populate(id, name, p, w, l, points);
                            }
                        }
                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        c = true;
                    }

                    /*try {
                        String jObjectString = jsonObject.optString("review");
                        JSONArray jsonArray = new JSONArray(jObjectString);

                        if (jsonArray.length() != 0) {

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonReview = jsonArray.getJSONObject(i);
                                String t1 = jsonReview.optString("team1");
                                String t2 = jsonReview.optString("team2");
                                String t1Pts = jsonReview.optString("score1");
                                String t2Pts = jsonReview.optString("score2");
                                String ven = jsonReview.optString("stadium");
                                String t1Id = jsonReview.optString("team1id");
                                String t2Id = jsonReview.optString("team2id");
                                String galleryId = jsonReview.optString("galleryid");
                                String galleryName = jsonReview.optString("galleryname");

                                boolean last = false;

                                if (i == jsonArray.length()-1) {
                                    last = true;
                                }

                                viewPagerAdapter.addView(addMatch(i, t1Id, t2Id, t1Pts, t2Pts, ven, galleryId, galleryName, last));
                            }
                        }

                    } catch (JSONException je) {
                        Log.e("JPP", Log.getStackTraceString(je));
                        //ll1.setVisibility(View.GONE);
                        d = true;
                    }*/

                    done = true;

                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
                    a = b = c = true;
                } catch (JSONException je) {
                    Log.e("JPP", Log.getStackTraceString(je));
                } catch (Exception e) {
                    Log.e("JPP", Log.getStackTraceString(e));
                }


                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getapphomeimage");
                    jsonObject = new JSONObject(response);
                    done = true;

                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
                    a = b = c = true;
                } catch (JSONException je) {
                    Log.e("JPP", Log.getStackTraceString(je));
                } catch (Exception e) {
                    Log.e("JPP", Log.getStackTraceString(e));
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                if (done) {
                    refresh();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(activity, "Oops, Something went wrong!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }.execute(null, null, null);
    }

    public  void getdata(){

//        String DATA_URL = "http://admin.jaipurpinkpanthers.com/index.php/json/getHomeContent";
        String DATA_URL = InternetOperations.SERVER_URL + "getHomeContent";
        swipeRefreshLayout.setRefreshing(true);
        //relativeLayout.setVisibility(View.GONE);
        //Creating a json array request to get the json from our api
        /*StringRequest jsonRequest = new StringRequest(Request.Method.GET, DATA_URL, null, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // the response is already constructed as a JSONObject!
                        show(response);
                        *//*try {
                            response = response.getJSONObject("args");
                            String site = response.getString("site"),
                                    network = response.getString("network");
                            System.out.println("Site: "+site+"\nNetwork: "+network);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*//*
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });*/

        StringRequest stringRequest = new StringRequest(Request.Method.GET, DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //relativeLayout.setVisibility(View.VISIBLE);

                        show(response);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    Toast.makeText(activity, "No Internet Access, Check your internet connection.",
                            Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    //reload_holder.setVisibility(View.VISIBLE);
                }

                if (error instanceof TimeoutError) {
                    Toast.makeText(activity, "Connection timed out", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    //reload_holder.setVisibility(View.VISIBLE);
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);


    }

    private void show(String response) {
        //Looping through all the elements of json array

        try {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(response);
            String jObjectString = jsonObject.optString("points");
            JSONArray jsonArray = new JSONArray(jObjectString);

            JSONObject latestNews = new JSONObject(jsonObject.optString("news"));
            newsTitle = latestNews.optString("name");
            newsImage = latestNews.optString("image");
            newsTime = latestNews.optString("timestamp");
            newsContent = latestNews.optString("content");
            imageLink = InternetOperations.SERVER_UPLOADS_URL + newsImage;

            Log.e("getdat","getdata");
            // Retrieves "colorArray" from the JSON object

            if (list.size() > 0) { //need to clear the list if pull to refresh is initiated
                list.clear();
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectPts = jsonArray.getJSONObject(i);
                String id = String.valueOf(i + 1);
                String name = jsonObjectPts.optString("name");
                String p = jsonObjectPts.optString("played");
                String w = jsonObjectPts.optString("wins");
                String l = jsonObjectPts.optString("lost");
                String points = jsonObjectPts.optString("point");
                Log.e("getdat",points);
                populate(id, name, p, w, l, points);
            }
            Log.e("getdat","getdata");
            /*if (jsonObject.length() != 0) {

                if (list.size() > 0) { //need to clear the list if pull to refresh is initiated
                    list.clear();
                }

                for (int i = 0; i < jsonObject.length(); i++) {
                    //JSONObject jsonObjectPts = jsonObject.getJSONObject(i);
                    String id = String.valueOf(i + 1);
                    String name = jsonObject.getString("name");
                    String p = jsonObject.getString("name");
                    String w = jsonObject.getString("name");
                    String l = jsonObject.getString("wins");
                    String points = jsonObject.getString("lost");
                    Log.e("getdat",points);
                    populate(id, name, p, w, l, points);
                }
            }*/

            refresh();







        } catch (JSONException e1) {
            e1.printStackTrace();
        }


        //if (images.isEmpty()) {
        /*for (int i = 0; i < jsonArray.length(); i++) {
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object

                team1.add(obj.getString(TAG_team1));
                team2.add(obj.getString(TAG_team2));
                score1.add(obj.getString(TAG_score1));
                score2.add(obj.getString(TAG_score2));
                stadium.add(obj.getString(TAG_stadium));
                starttimedate.add(obj.getString(TAG_starttimedate));
                team1id.add(obj.getString(TAG_team1id));
                team2id.add(obj.getString(TAG_team2id));
                matchtime.add(obj.getString(TAG_matchtime));
                //count++;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
    }


    public void refresh() {
        f=true;
        e=true;
        jpptv = true;
        signUp = true;
        sponsers=true;
        Log.d("refresh", String.valueOf(sponsers));
//        rlsponsers.setVisibility(View.VISIBLE);
//        if (appImgStatus.equals("1")){
//            showcongrats.setVisibility(View.VISIBLE);
//            Glide.with(activity)
//                    .load(InternetOperations.SERVER_UPLOADS_URL+appImg)
//                    .asBitmap()
//                    .into(homecongrats);
//        }else{
//            Log.d("live score", "live score ");
//            if (!a) {
//                ll1.setVisibility(View.VISIBLE);
//            }
//            if (!livescore)
//            {
//                llLiveUpdatescore.setVisibility(View.VISIBLE);
//            }
//        }

        if (!b) {
            ll2.setVisibility(View.VISIBLE);
        }
        if (!c) {
            ll3.setVisibility(View.VISIBLE);
        }
        if (!d) {
            flReview.setVisibility(View.GONE);
        }
        if (!e) {
            ll4.setVisibility(View.VISIBLE);
        }
    /*    if (!f) {
            ll5.setVisibility(View.VISIBLE);
        }*/
        if (jpptv) {
            ll5.setVisibility(View.VISIBLE);
        }
        if (signUp) {
            Log.d( "refreshfdkls", String.valueOf(sponsers));

            ll4.setVisibility(View.VISIBLE);
            llsignUp.setVisibility(View.VISIBLE);
            rlsponsers.setVisibility(View.VISIBLE);
            showcongrats.setVisibility(View.VISIBLE);
        }
//        if (sponsers) {
//            Log.d( "refresh: ", String.valueOf(sponsers));
//            rlsponsers.setVisibility(View.VISIBLE);
//            showcongrats.setVisibility(View.VISIBLE);
//        }

        llPoints.removeAllViews();







//full time and half time
        hftime.setText(matchTime);

        tvVenue.setText(venue);
//        Glide.with(activity)
//                .load("http://admin.jaipurpinkpanthers.com/uploads/"+appImg)
//                .asBitmap()
//                .into(homecongrats);

        if (time != null) {
            tvMatchTime.setText(time + "(IST)");
        }

//
                    Log.d("appImg 23e42343",appImg+appImgStatus);
                    if (appImgStatus.equals("1")){
                        showcongrats.setVisibility(View.VISIBLE);
                            Log.d("appImg 23e42343",appImg);
                            Glide.with(activity)
                                    .load("http://admin.jaipurpinkpanthers.com/uploads/"+appImg)
                                    .asBitmap()
                                    .into(homecongrats);
                        llLiveUpdatescore.setVisibility(View.GONE);
                        ll1.setVisibility(View.GONE);
                    }else{

                        Log.d("appImgStatus", appImgStatus);
                        handler = new Handler();
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                handler.postDelayed(this, 1000);
                                try {
                                    Log.d("appImgStatus sada", appImgStatus);
                                    SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy, hh:mm");

                                    Date date = format.parse(time);
                                    Calendar c = Calendar.getInstance();
                                    Date today = c.getTime();
                                    Log.d("today", String.valueOf(today));
                                    Log.d("Date", String.valueOf(date));
                                    if (!today.after(date) && team1Pts.equals("") && team2Pts.equals("")) {
                                        Log.d("Date inside", String.valueOf(date));
//                        long diffmonth=0;
//                        long difftime = date.getTime() - today.getTime();
//                        long diffSeconds = difftime / 1000 % 60;
//                        long diffMinutes = difftime / (60 * 1000) % 60;
//                        if (diffSeconds > 0) {
//                            diffMinutes = diffMinutes + 1;
//                        }
//                        long diffHours = difftime / (60 * 60 * 1000) % 24;
////                        long diffDays= difftime/(60 * 60 * 1000)/24;
//                        diffmonth = (difftime / (24 * 60 * 60 * 1000)) / 30;

                                        long different = date.getTime() - today.getTime();

                                        long secondsInMilli = 1000;
                                        long minutesInMilli = secondsInMilli * 60;
                                        long hoursInMilli = minutesInMilli * 60;
                                        long daysInMilli = hoursInMilli * 24;

                                        long elapsedDays = different / daysInMilli;
                                        different = different % daysInMilli;

                                        long elapsedHours = different / hoursInMilli;
                                        different = different % hoursInMilli;

                                        long elapsedMinutes = different / minutesInMilli;
                                        different = different % minutesInMilli;

                                        long elapsedSeconds = different / secondsInMilli;

//                        long cday = today.getDate();
//                        long matchday = date.getDate();
//                        long monthbefore = date.getMonth() - 1;
//                        if (cday > matchday) {
//                            if (monthbefore == 0 || monthbefore == 2 || monthbefore == 4 || monthbefore == 6 || monthbefore == 7 || monthbefore == 9 || monthbefore == 11) {
//                                diffDays = 31 + (cday - matchday);
//                            } else {
//                                if (monthbefore == 3 || monthbefore == 5 || monthbefore == 8 || monthbefore == 10)
//                                    diffDays = 30 + (cday - matchday);
//                                else
//                                    diffDays = 28 + (cday - matchday);
//                            }
//                        } else {
//                            diffDays = matchday - cday;
//                        }
//
//
//                        Log.d(String.valueOf(matchday), String.valueOf(cday));


                                        tvmins.setText(String.format("%02d", elapsedMinutes));
                                        tvhours.setText(String.format("%02d", elapsedHours));
                                        tvdays.setText(String.format("%02d", elapsedDays));
//                        tvmonth.setText(String.format("%02d", diffmonth));

                                        if (team1Pts.equals("") && team2Pts.equals("")) {
                                            ll1.setVisibility(View.VISIBLE);
                                            showcongrats.setVisibility(View.GONE);
                                            llLiveUpdatescore.setVisibility(View.GONE);
                                            tvlivescore1.setText("00");
                                            tvlivescore2.setText("00");

                                        }

                                    } else {
                                        Log.d("hi dsfsd", "color");

                                        tvmins.setText(String.valueOf("00"));
                                        tvhours.setText(String.valueOf("00"));
                                        tvdays.setText(String.valueOf("00"));
                                        tvmonth.setText(String.valueOf("00"));
                                        handler.removeCallbacks(runnable);

                                        //score view

                                        if (team1Pts.equals("") && team2Pts.equals("")) {
                                            tvlivescore1.setText("00");
                                            tvlivescore2.setText("00");
                                            Log.d("hi dsfsd", "color");
                                            scoreColour(team1, 1);
                                            scoreColour(team2, 2);
                                            llLiveUpdatescore.setVisibility(View.VISIBLE);
                                            showcongrats.setVisibility(View.GONE);
                                            ll1.setVisibility(View.GONE);
                                        } else {

                                            Log.d("hi dsfsd", "color");
                                            scoreColour(team1, 1);
                                            scoreColour(team2, 2);
                                            tvlivescore1.setText(String.valueOf(team1Pts));
                                            tvlivescore2.setText(String.valueOf(team2Pts));
                                            llLiveUpdatescore.setVisibility(View.VISIBLE);
                                            showcongrats.setVisibility(View.GONE);
                                            ll1.setVisibility(View.GONE);
                                        }


                                    }
                                } catch (Exception e) {
                                    Log.d("", "run ");
                                    e.printStackTrace();
                                }}
                            };handler.postDelayed(runnable, 0);

            }


        tagMain = team1 + "#" + team2 + "#" + time;

                addtocalender.setTag(tagMain);



        tvNewsHead.setText(newsTitle);

        if (!newsContent.startsWith("http")){
            tvNewsDesc.setText(newsContent);
        }
        tvNewsDate.setText(newsTime);

        if (team1logo != "" && team2logo != "") {
//            String imageUriTeam1 = "drawable://" + getTeamDrawable(team1Id);
//            String imageUriTeam2 = "drawable://" + getTeamDrawable(team2Id);
            String imageUriTeam1 = InternetOperations.SERVER_UPLOADS_URL + team1logo;
            String imageUriTeam2 = InternetOperations.SERVER_UPLOADS_URL + team2logo;
            imageLoader.displayImage(imageUriTeam1, ivT1, options);
            imageLoader.displayImage(imageUriTeam2, ivT2, options);
        }else{
            ivT1.setImageResource(R.drawable.tbd);
            ivT2.setImageResource(R.drawable.tbd);
        }

        if (team1logo != "" && team2logo != "") {
//            String imageUriTeam1 = "drawable://" + getTeamDrawable(team1Id);
//            String imageUriTeam2 = "drawable://" + getTeamDrawable(team2Id);
            String imageUriTeam1 = InternetOperations.SERVER_UPLOADS_URL + team1logo;
            String imageUriTeam2 = InternetOperations.SERVER_UPLOADS_URL + team2logo;
            imageLoader.displayImage(imageUriTeam1, llivT1live, options);
            imageLoader.displayImage(imageUriTeam2, llivT2live, options);
        }else{
            llivT1live.setImageResource(R.drawable.tbd);
            llivT2live.setImageResource(R.drawable.tbd);
        }

        if(level!= ""){
            llLatestUpdate = (LinearLayout) view.findViewById(R.id.llLatestUpdate);
            TextView tvLatest = (TextView) llLatestUpdate.findViewById(R.id.tvCrossHeader);
            tvLatest.setTypeface(CustomFonts.getRegularFont(activity));
            tvLatest.setText(level);

            llLiveupdate = (LinearLayout) view.findViewById(R.id.llLiveUpdate);
            TextView tvmatchupdate = (TextView) llLiveupdate.findViewById(R.id.tvCrossHeader);
            tvmatchupdate.setTypeface(CustomFonts.getRegularFont(activity));
            tvmatchupdate.setText(level);
        }

        String imageUri = "drawable://" + R.drawable.schedule_back;
        imageLoader.displayImage(imageUri, ivHomeMain, options);

        imageLoader.displayImage(imageLink, ivNews, options);

        Glide.with(activity)
                .load(imageLink)
                .asBitmap()
                .override(300, 300)
                .placeholder(R.drawable.loadingnews)
                .centerCrop()
                .into(ivNews);
        Glide.with(activity)
                .load("http://jaipurpinkpanthers.com/img/mobile/pointtable.png")
                .asBitmap()
                .placeholder(R.drawable.loadingnews)
                .centerCrop()
                .into(ivpoints);
        Glide.with(activity)
                .load("http://jaipurpinkpanthers.com/img/mobile/meet.png")
                .asBitmap()
                .placeholder(R.drawable.loadingnews)
                .centerCrop()
                .into(iv_jpptv);
        Glide.with(activity)
                .load("http://jaipurpinkpanthers.com/img/mobile/signup.png")
                .asBitmap()
                .placeholder(R.drawable.loadingnews)
                .centerCrop()
                .into(ivsignup);

        Glide.with(activity)
                .load(InternetOperations.SERVER_UPLOADS_URL+sponsorimg)
                .asBitmap()
                .placeholder(R.drawable.loadingnews)
                .centerCrop()
                .into(ivsponsor);

        if (list.size() > 0) {


            for (int i = 0; i < list.size(); i++) {
                LayoutInflater inflator = activity.getLayoutInflater();
                View viewPointsRow = inflator.inflate(R.layout.layout_points_row, null, false);

                TextView tvNo = (TextView) viewPointsRow.findViewById(R.id.tvNo); //find the different Views
                TextView tvTeam = (TextView) viewPointsRow.findViewById(R.id.tvTeam);
                TextView tvP = (TextView) viewPointsRow.findViewById(R.id.tvP);
                TextView tvW = (TextView) viewPointsRow.findViewById(R.id.tvW);
                TextView tvL = (TextView) viewPointsRow.findViewById(R.id.tvL);
                TextView tvPts = (TextView) viewPointsRow.findViewById(R.id.tvPts);

                LinearLayout llFull = (LinearLayout) viewPointsRow.findViewById(R.id.llFull);

                HashMap<String, String> map = list.get(i);

                String no = map.get("tvNo");
                String team = map.get("tvTeam");
                String p = map.get("tvP");
                String w = map.get("tvW");
                String l = map.get("tvL");
                String pts = map.get("tvPts");

                tvNo.setText(no);
                tvTeam.setText(team);

                if (team.equals("Jaipur Pink Panthers")) {
                    /*tvNo.setTypeface(CustomFonts.getRegularFont(activity));
                    tvTeam.setTypeface(CustomFonts.getRegularFont(activity));
                    tvP.setTypeface(CustomFonts.getRegularFont(activity));
                    tvW.setTypeface(CustomFonts.getRegularFont(activity));
                    tvL.setTypeface(CustomFonts.getRegularFont(activity));
                    tvPts.setTypeface(CustomFonts.getRegularFont(activity));*/

                    tvNo.setTypeface(CustomFonts.getRegularFont(activity));
                    tvNo.setTextColor(Color.parseColor("#ee4a9b"));
                    tvTeam.setTypeface(CustomFonts.getRegularFont(activity));
                    tvTeam.setTextColor(Color.parseColor("#ee4a9b"));
                    tvP.setTypeface(CustomFonts.getRegularFont(activity));
                    tvP.setTextColor(Color.parseColor("#ee4a9b"));
                    tvW.setTypeface(CustomFonts.getRegularFont(activity));
                    tvW.setTextColor(Color.parseColor("#ee4a9b"));
                    tvL.setTypeface(CustomFonts.getRegularFont(activity));
                    tvL.setTextColor(Color.parseColor("#ee4a9b"));
                    tvPts.setTypeface(CustomFonts.getRegularFont(activity));
                    tvPts.setTextColor(Color.parseColor("#ee4a9b"));
                    llFull.setBackgroundColor(Color.parseColor("#7bd9fa"));


                } else {
                    tvNo.setTypeface(CustomFonts.getLightFont(activity));
                    tvNo.setTextColor(Color.parseColor("black"));
                    tvTeam.setTypeface(CustomFonts.getLightFont(activity));
                    tvTeam.setTextColor(Color.parseColor("black"));
                    tvP.setTypeface(CustomFonts.getLightFont(activity));
                    tvP.setTextColor(Color.parseColor("black"));
                    tvW.setTypeface(CustomFonts.getLightFont(activity));
                    tvW.setTextColor(Color.parseColor("black"));
                    tvL.setTypeface(CustomFonts.getLightFont(activity));
                    tvL.setTextColor(Color.parseColor("black"));
                    tvPts.setTypeface(CustomFonts.getLightFont(activity));
                    tvPts.setTextColor(Color.parseColor("black"));

                    llFull.setBackgroundColor(Color.parseColor("#7bd9fa"));

                }
                tvP.setText(p);
                tvW.setText(w);
                tvL.setText(l);
                tvPts.setText(pts);

                llPoints.addView(viewPointsRow);
            }

        } else {

        }

        //viewPagerAdapter.notifyDataSetChanged();
    }

    public void populate(String n, String team, String p, String w, String l, String pts) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("tvNo", n);
        map.put("tvTeam", team);
        map.put("tvP", p);
        map.put("tvW", w);
        map.put("tvL", l);
        map.put("tvPts", pts);
        list.add(map);
    }
    public void setappImage(String appimg1){

    }


    //set colour for score textview
    public void scoreColour(String team,int teamNo)
    {
        Log.d("hi ","color ");
        if(teamNo==1){
            if(team.equals("Patna Pirates"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#0a4436"));
            }
            else if(team.equals("Bengaluru Bulls"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#b01d21"));
            }
            else if(team.equals("Bengal Warriors"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#f26724"));
            }
            else if(team.equals("Dabang Delhi"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#d91f2d"));
            }
            else if(team.equals("Jaipur Pink Panthers"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#ee4a9b"));
                Log.d("hi pink panthers","color ");
            }
            else if(team.equals("Puneri Paltan"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#f04e23"));
            }
            else if(team.equals("Telugu Titans"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#da2131"));
            }
            else if(team.equals("U Mumba"))
            {
                tvlivescore1.setTextColor(Color.parseColor("#f15922"));
                Log.d("hi u mumba","color ");
            }}

        else{
            if(team.equals("Patna Pirates"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#0a4436"));
            }
            else if(team.equals("Bengaluru Bulls"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#b01d21"));
            }
            else if(team.equals("Bengal Warriors"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#f26724"));
            }
            else if(team.equals("Dabang Delhi"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#d91f2d"));
            }
            else if(team.equals("Jaipur Pink Panthers"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#ee4a9b"));
                Log.d("hi pink panthers","color ");
            }
            else if(team.equals("Puneri Paltan"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#f04e23"));
            }
            else if(team.equals("Telugu Titans"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#da2131"));
            }
            else if(team.equals("U Mumba"))
            {
                tvlivescore2.setTextColor(Color.parseColor("#f15922"));
                Log.d("hi u mumba","color ");
            }
        }
    }

    public int getTeamDrawable(String id) {

        int teamId = Integer.parseInt(id);
        TypedArray teamLogos = activity.getResources().obtainTypedArray(R.array.teamLogo);

        return teamLogos.getResourceId(teamId - 1, -1);
    }

    /*private void addMatch(String venue,String time,String team1Id,String team2Id)
    {

        ivT1.setImageResource(getTeamDrawable(team1Id));
        ivT2.setImageResource(getTeamDrawable(team2Id));
        tvMatchTime.setText(time + "(IST)");
        tvVenue.setText(venue);
    }*/

/*

    private LinearLayout addMatch(final int id, String t1Id, String t2Id, String t1Pts, String t2Pts, String ven, String galleryId, String galleryName, boolean last) {

        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout v = (LinearLayout) inflater.inflate(R.layout.layout_review, null);

        ImageView ivT1R, ivT2R, ivLeft, ivRight;
        TextView tvS1R, tvS2R, tvCoR, tvVenueR, tvGoToGallery;

        ivT1R = (ImageView) v.findViewById(R.id.ivT1R);
        ivT2R = (ImageView) v.findViewById(R.id.ivT2R);

        ivLeft = (ImageView) v.findViewById(R.id.ivLeft);
        ivRight = (ImageView) v.findViewById(R.id.ivRight);

        tvS1R = (TextView) v.findViewById(R.id.tvS1);
        tvS2R = (TextView) v.findViewById(R.id.tvS2);
        tvCoR = (TextView) v.findViewById(R.id.tvCo);
        tvS1R.setTypeface(CustomFonts.getBoldFont(activity));
        tvS2R.setTypeface(CustomFonts.getBoldFont(activity));
        tvCoR.setTypeface(CustomFonts.getBoldFont(activity));

        tvVenueR = (TextView) v.findViewById(R.id.tvVenue);
        tvGoToGallery = (TextView) v.findViewById(R.id.tvGoToGallery);
        tvVenueR.setTypeface(CustomFonts.getLightFont(activity));
        tvGoToGallery.setTypeface(CustomFonts.getLightFont(activity));

        String imageUriTeam1 = "drawable://" + getTeamDrawable(t1Id);
        String imageUriTeam2 = "drawable://" + getTeamDrawable(t2Id);

        //String imageUriTeam1 = "drawable://" + R.drawable.t1;
        //String imageUriTeam2 = "drawable://" + R.drawable.t2;

        String imageUri = "drawable://" + R.drawable.schedule_back;


        //String imageUriTeam1 = "drawable://" + teamUrl[Integer.parseInt(t1Id)-1];
        //String imageUriTeam2 = "drawable://" + teamUrl[Integer.parseInt(t2Id)-1];
        String imageUriTeam3 = "drawable://" + R.drawable.t1;

        //Picasso.with(getActivity()).load(R.drawable.t11).into(ivT1R);
        //Picasso.with(getActivity()).load(R.drawable.t1).into(ivT2R);

       */
/* if (id == 0) {
            ivLeft.setImageResource(R.drawable.ic_blank);
        } else {
            ivLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchPrev();
                }
            });

        }

        if (last) {
            ivRight.setImageResource(R.drawable.ic_blank);
        } else {
            ivRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchNext();
                }
            });
        }
*//*

        //ivT1R.setImageResource(getTeamDrawable(t1Id));
        //ivT2R.setImageResource(getTeamDrawable(t2Id));
        //ivT1R.setImageURI(Uri.parse(imageUriTeam1));
        //ivT2R.setImageURI(Uri.parse(imageUriTeam2));

        ivT1R.setImageResource(getTeamDrawable(t1Id));
        ivT2R.setImageResource(getTeamDrawable(t2Id));

        String jay = "assets://";
        //Log.d(TAG, "URI = " + imageUri + fileName);
        //imageLoader.displayImage(jay+"t1.png", ivT1R);
        //ImageLoader.getInstance().displayImage(imageUriTeam1, ivT1R);
        //ImageLoader.getInstance().displayImage(imageUriTeam2, ivT2R);

        */
/*imageLoader.displayImage("drawable://" + R.drawable.ic_bottom_gallery_white, ivT1R);
        imageLoader.displayImage("drawable://" + R.drawable.ic_bottom_gallery_white, ivT2R, options);
        ivT1R.setBackground(R.drawable.ic_bottom_gallery_white);*//*


        */
/*imageLoader.displayImage("http://jaipurpinkpanthers.com/admin/uploads/736x327-14.jpg",ivT1R,options);
        imageLoader.displayImage("http://jaipurpinkpanthers.com/admin/uploads/736x327-14.jpg",ivT2R,options);*//*


        tvS1R.setText(t1Pts);
        tvS2R.setText(t2Pts);

        tvVenueR.setText(ven);

        String tag = galleryId + "#" + galleryName;
        tvGoToGallery.setTag(tag);

        return v;
    }
*/

  /*  private void matchPrev() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
    }

    private void matchNext() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
    }*/

    private static final int[] teamUrl = new int[] {
            R.drawable.t1,
            R.drawable.t2,
            R.drawable.t3,
            R.drawable.t4,
            R.drawable.t5,
            R.drawable.t6,
            R.drawable.t7,
            R.drawable.t8
    };

}
