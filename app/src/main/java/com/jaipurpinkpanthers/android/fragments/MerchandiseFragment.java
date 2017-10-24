package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.WebActivity;
import com.jaipurpinkpanthers.android.adapters.MatchRecycleAdapter;
import com.jaipurpinkpanthers.android.adapters.MatchUpdateGetter;
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

import java.util.ArrayList;
import java.util.HashMap;

public class MerchandiseFragment extends Fragment {
    RecyclerView.Adapter RecyclerAdapter;
    RecyclerView.LayoutManager RecyclerlayoutManager;
    TextView tvTic, tvBookNow;
    TextView tvMer, tvBuyNow;
    TextView tvVenue1, tvTime1,tvVenue2, tvTime2,tvVenue3, tvTime3,tvVenue4, tvTime4;
    Button bBook1,bBook2,bBook3,bBook4;
    HashMap<String, String> single;
    ImageView ivT1, ivT2, ivBanner;

    ImageLoader imageLoader;
    DisplayImageOptions options;
    LinearLayout llAddToCalendar, llSchedule;
    LinearLayout llSchedule1,llSchedule2,llSchedule3,llSchedule4;
    LinearLayout llmatch1,llmatch2,llmatch3,llmatch4;
    RelativeLayout merchandise;
    private Handler handler;
    private  Runnable runnable;
    Button  buynow;
    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;

    public static final String TAG_id = "id";
    public static final String TAG_image = "image";
    public static final String TAG_link = "link";
    public static final String TAG_name= "name";
    public static final String TAG_order = "order";
    public static final String TAG_price = "price";

    ArrayList<MatchUpdateGetter> matchUpdateGetter = new ArrayList<MatchUpdateGetter>();

    View view;
    RecyclerView recyclerView;
    ArrayList<HashMap<String, String>> list;
    ProgressDialog progressDialog;
    AsyncTask asyncTask;

    RelativeLayout rlSchedule1, rlSchedule2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_merchandise, container, false);
        activity = getActivity();
//        recyclerView=(RecyclerView)view.findViewById(R.id.rvmarchandise);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setColorSchemeResources(R.color.jppAccentColor,R.color.myPrimaryColor);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getdata();
//
//            }
//        });
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

        initilizeViews();
        return view;
    }

    public void initilizeViews(){
//        single = new HashMap<String, String>();
//        list = new ArrayList<HashMap<String, String>>();

//        tvTic = (TextView) view.findViewById(R.id.tvTic);
//        tvMer = (TextView) view.findViewById(R.id.tvMer);
//        tvTic.setTypeface(CustomFonts.getBoldFont(getActivity()));
//        tvMer.setTypeface(CustomFonts.getBoldFont(getActivity()));
//
//        tvBuyNow = (TextView) view.findViewById(R.id.tvBuyNow);
//        tvBuyNow.setTypeface(CustomFonts.getRegularFont(getActivity()));
//
//        tvBookNow = (TextView) view.findViewById(R.id.tvBookNow);
//        tvBookNow.setTypeface(CustomFonts.getRegularFont(getActivity()));

//        tvTime1 = (TextView) view.findViewById(R.id.tvVenue1);
//        tvVenue1 = (TextView) view.findViewById(R.id.tvTime1);
//        tvTime1.setTypeface(CustomFonts.getLightFont(getActivity()));
        buynow= (Button) view.findViewById(R.id.buynow);
        buynow.setTypeface(CustomFonts.getLightFont(getActivity()));
//        tvVenue1.setTypeface(CustomFonts.getLightFont(getActivity()));
//        merchandise= (RelativeLayout) view.findViewById(R.id.merchandise1);
        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("webLink", "http://www.didasportswear.com/jaipur-pink-panthers.html");
                startActivity(intent);
            }
        });
//        getdata();
//
//        bBook1 = (Button) view.findViewById(R.id.bBook1);
//        bBook1.setTypeface(CustomFonts.getBoldFont(getActivity()));
//        bBook1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "https://in.bookmyshow.com/sports/pro-kabaddi/jaipur-pink-panthers/seat-layout/ET00042335";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//            }
//        });
//
//
//        llSchedule1= (LinearLayout) view.findViewById(R.id.rlSchedule1);
//
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                handler.postDelayed(this, 1000);
//        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy, hh:mm");
//        String time1="29 Jun 2016, 20:00";
//        Date date = null;
//        try {
//            date = format.parse(time1);
//            Calendar c = Calendar.getInstance();
//            Date today=c.getTime();
//
//            if(today.getTime()>=date.getTime())
//            {
//                llSchedule1.setVisibility(View.GONE);
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }     }
//        };
//        handler.postDelayed(runnable, 0);
//
//
//        llSchedule2= (LinearLayout) view.findViewById(R.id.rlSchedule2);
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                handler.postDelayed(this, 1000);
//                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy, hh:mm");
//                String time2="30 Jun 2016, 20:00";
//                Date date = null;
//                try {
//                    date = format.parse(time2);
//                    Calendar c = Calendar.getInstance();
//                    Date today=c.getTime();
//
//                    if(today.getTime()>=date.getTime())
//                    {
//                        llSchedule2.setVisibility(View.GONE);
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }     }
//        };
//        handler.postDelayed(runnable, 0);
//
//
//        llSchedule3= (LinearLayout) view.findViewById(R.id.rlSchedule3);
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                handler.postDelayed(this, 1000);
//                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy, hh:mm");
//                String time3="01 Jul 2016, 20:00";
//                Date date = null;
//                try {
//                    date = format.parse(time3);
//                    Calendar c = Calendar.getInstance();
//                    Date today=c.getTime();
//
//                    if(today.getTime()>=date.getTime())
//                    {
//                        llSchedule3.setVisibility(View.GONE);
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }     }
//        };
//        handler.postDelayed(runnable, 0);
//
//
//        llSchedule4= (LinearLayout) view.findViewById(R.id.rlSchedule4);
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                handler.postDelayed(this, 1000);
//                SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy, hh:mm");
//                String time4="02 Jul 2016, 20:00";
//                Date date = null;
//                try {
//                    date = format.parse(time4);
//                    Calendar c = Calendar.getInstance();
//                    Date today=c.getTime();
//
//                    if(today.getTime()>=date.getTime())
//                    {
//                        llSchedule4.setVisibility(View.GONE);
//                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }     }
//        };
//        handler.postDelayed(runnable, 0);
//
//
//        tvTime2 = (TextView) view.findViewById(R.id.tvVenue2);
//        tvVenue2 = (TextView) view.findViewById(R.id.tvTime2);
//        tvTime2.setTypeface(CustomFonts.getLightFont(getActivity()));
//        tvVenue2.setTypeface(CustomFonts.getLightFont(getActivity()));
//        bBook2 = (Button) view.findViewById(R.id.bBook2);
//        bBook2.setTypeface(CustomFonts.getBoldFont(getActivity()));
//        bBook2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "https://in.bookmyshow.com/sports/pro-kabaddi/jaipur-pink-panthers/seat-layout/ET00042336";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//            }
//        });
//
//        tvTime3 = (TextView) view.findViewById(R.id.tvVenue3);
//        tvVenue3 = (TextView) view.findViewById(R.id.tvTime3);
//        tvTime3.setTypeface(CustomFonts.getLightFont(getActivity()));
//        tvVenue3.setTypeface(CustomFonts.getLightFont(getActivity()));
//        bBook3 = (Button) view.findViewById(R.id.bBook3);
//        bBook3.setTypeface(CustomFonts.getBoldFont(getActivity()));
//        bBook3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "https://in.bookmyshow.com/sports/pro-kabaddi/jaipur-pink-panthers/seat-layout/ET00042338";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//            }
//        });
//
//        tvTime4 = (TextView) view.findViewById(R.id.tvVenue4);
//        tvVenue4 = (TextView) view.findViewById(R.id.tvTime4);
//        tvTime4.setTypeface(CustomFonts.getLightFont(getActivity()));
//        tvVenue4.setTypeface(CustomFonts.getLightFont(getActivity()));
//        bBook4 = (Button) view.findViewById(R.id.bBook4);
//        bBook4.setTypeface(CustomFonts.getBoldFont(getActivity()));
//
//        bBook4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "https://in.bookmyshow.com/sports/pro-kabaddi/jaipur-pink-panthers/seat-layout/ET00042337";
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//            }
//        });
//
//
//        llmatch1 = (LinearLayout) view.findViewById(R.id.llMatch1);
//        TextView tvTable1 = (TextView) llmatch1.findViewById(R.id.tvCrossHeader);
//        tvTable1.setTypeface(CustomFonts.getRegularFont(getActivity()));
//        tvTable1.setText("MATCH - 01");
//
//        llmatch2 = (LinearLayout) view.findViewById(R.id.llMatch2);
//        TextView tvTable2 = (TextView) llmatch2.findViewById(R.id.tvCrossHeader);
//        tvTable2.setTypeface(CustomFonts.getRegularFont(getActivity()));
//        tvTable2.setText("MATCH - 02");
//
//        llmatch3 = (LinearLayout) view.findViewById(R.id.llMatch3);
//        TextView tvTable3 = (TextView) llmatch3.findViewById(R.id.tvCrossHeader);
//        tvTable3.setTypeface(CustomFonts.getRegularFont(getActivity()));
//        tvTable3.setText("MATCH - 03");
//
//        llmatch4 = (LinearLayout) view.findViewById(R.id.llMatch4);
//        TextView tvTable4 = (TextView) llmatch4.findViewById(R.id.tvCrossHeader);
//        tvTable4.setTypeface(CustomFonts.getRegularFont(getActivity()));
//        tvTable4.setText("MATCH - 04");


//        if (InternetOperations.checkIsOnlineViaIP()) {
//
//        } else {
//            //progressDialog.dismiss();
//            Toast.makeText(getActivity(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
//        }
    }
//    public void getdata() {
//
//        String DATA_URL = InternetOperations.SERVER_URL +"getallmerchandize";
//
//
//
//        //Creating a json array request to get the json from our api
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                show(response);
//                swipeRefreshLayout.setRefreshing(false);
//
//            }
//
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        if (error instanceof NoConnectionError) {
//                            Toast.makeText(activity, "No Internet Access, Check your internet connection.",
//                                    Toast.LENGTH_SHORT).show();
//                            swipeRefreshLayout.setRefreshing(false);
//                        }
//
//                        if (error instanceof TimeoutError) {
//                            Toast.makeText(activity, "Connection timed out", Toast.LENGTH_SHORT).show();
//                            swipeRefreshLayout.setRefreshing(false);
//                        }
//                    }
//                }
//        );
//
//        //Creating a request queue
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        //Adding our request to the queue
//        requestQueue.add(jsonArrayRequest);
//
//    }
//
//    private void show(JSONArray jsonArray) {
//        //Looping through all the elements of json array
//
////        team1.clear();
////        team2.clear();
////        score1.clear();
////        score2.clear();
////        stadium.clear();
////        starttimedate.clear();
////        team1id.clear();
////        team2id.clear();
////        matchtime.clear();
////        teamimage1.clear();
////        teamimage2.clear();
//        matchUpdateGetter.clear();
//
//        //if (images.isEmpty()) {
//
//        for (int i = 0; i < jsonArray.length(); i++) {
//            //Creating a json object of the current index
//            JSONObject obj = null;
//            try {
//                //getting json object from current index
//                obj = jsonArray.getJSONObject(i);
//
//                //getting image url and title from json object
//
////                team1.add(obj.getString(TAG_team1));
////                team2.add(obj.getString(TAG_team2));
////                score1.add(obj.getString(TAG_score1));
////                score2.add(obj.getString(TAG_score2));
////                stadium.add(obj.getString(TAG_stadium));
////                starttimedate.add(obj.getString(TAG_starttimedate));
////                team1id.add(obj.getString(TAG_team1id));
////                team2id.add(obj.getString(TAG_team2id));
////                matchtime.add(obj.getString(TAG_matchtime));
////                teamimage1.add(obj.getString(TAG_teamimage1));
////                teamimage2.add(obj.getString(TAG_teamimage2));
//                //count++;
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        //}
//
//        int count =team1.size();
//        //Creating GridViewAdapter Object
//        for (int i = 0; i < team1.size(); i++) {
//            MatchUpdateGetter current = new MatchUpdateGetter(team1.get(i), team2.get(i), score1.get(i),
//                    score2.get(i), stadium.get(i), starttimedate.get(i), team1id.get(i), team2id.get(i),
//                    matchtime.get(i),teamimage1.get(i),teamimage2.get(i), String.valueOf(count));
//            matchUpdateGetter.add(current);
//
//            count--;
//            Log.e("ItemInfoGetter: ", "" + i);
//        }
//
//        RecyclerAdapter = new MatchRecycleAdapter(matchUpdateGetter, getActivity());
//
//        recyclerView.setHasFixedSize(true);
//
//        RecyclerlayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(RecyclerlayoutManager);
//        recyclerView.setAdapter(RecyclerAdapter);
//        RecyclerAdapter.notifyDataSetChanged();
//
//    }
//    public void getScheduleData() {
//
//        new AsyncTask<Void, Void, String>() {
//
//            boolean done = false;
//            @Override
//            protected String doInBackground(Void... params) {
//
//                if (Looper.myLooper() == null) {
//                    Looper.prepare();
//                }
//                String response;
//                JSONArray jsonArray = null;
//                try {
//                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "review");
//
//                    jsonArray = new JSONArray(response);
//
//                    if (jsonArray.length() != 0) {
//
//                        for (int i = 0; i < jsonArray.length(); i++) {
//
//                            if (i != 0) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                //String id = String.valueOf(i + 1);
//                                String team1 = jsonObject.optString("team1");
//                                String team2 = jsonObject.optString("team2");
//                                String time = jsonObject.optString("starttimedate");
//                                String venue = jsonObject.optString("stadium");
//                                populate(team1, team2, time,venue);
//                            } else {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                //String id = String.valueOf(i + 1);
//                                String team1 = jsonObject.optString("team1");
//                                String team2 = jsonObject.optString("team2");
//                                String team1id = jsonObject.optString("team1id");
//                                String team2id = jsonObject.optString("team2id");
//                                String time = jsonObject.optString("starttimedate");
//                                String stadium = jsonObject.optString("stadium");
//
//                                single.put("team1", team1);
//                                single.put("team2", team2);
//                                single.put("team1id", team1id);
//                                single.put("team2id", team2id);
//                                single.put("time", time);
//                                single.put("stadium", stadium);
//
//                            }
//                        }
//                    }
//                    done = true;
//
//                } catch (IOException io) {
//                    Log.e("JPP", Log.getStackTraceString(io));
//                } catch (JSONException je) {
//                    Log.e("JPP", Log.getStackTraceString(je));
//                } catch (Exception e){
//                    Log.e("JPP", Log.getStackTraceString(e));
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                progressDialog.dismiss();
//                if (done) {
//                    refresh();
//                }else{
//                    Toast.makeText(getActivity(),"Oops, Something went wrong!",Toast.LENGTH_SHORT).show();
//                }
//            }
//        }.execute(null, null, null);
//    }
//
//
//    public void refresh() {
//
//        rlSchedule1.setVisibility(View.VISIBLE);
//        rlSchedule2.setVisibility(View.VISIBLE);
//
//        String team1Id = single.get("team1id");
//        String team2Id = single.get("team2id");
//
//        if (team1Id != null || team2Id != null) {
//            String imageUriTeam1 = "drawable://" + getTeamDrawable(team1Id);
//            String imageUriTeam2 = "drawable://" + getTeamDrawable(team2Id);
//            String imageBanner = "drawable://" + R.drawable.schedule_back;
//
//            imageLoader.displayImage(imageUriTeam1, ivT1, options);
//            imageLoader.displayImage(imageUriTeam2, ivT2, options);
//            imageLoader.displayImage(imageBanner, ivBanner, options);
//        }
//
//        if (list.size() > 0) {
//
//            tvVenue.setText(single.get("stadium"));
//            tvTime.setText(single.get("time") + "(IST)");
//
//
//            for (int i = 0; i < list.size(); i++) {
//                LayoutInflater inflator = getActivity().getLayoutInflater();
//                View viewScheduleRow = inflator.inflate(R.layout.activity_merchandise, null, false);
//
//                TextView tvMatch = (TextView) viewScheduleRow.findViewById(R.id.tvMatch); //find the different Views
//                TextView tvTime = (TextView) viewScheduleRow.findViewById(R.id.tvTime);
//
//
//                //ImageView ivTickets = (ImageView) viewScheduleRow.findViewById(R.id.ivTickets);
//                LinearLayout llTicket = (LinearLayout) viewScheduleRow.findViewById(R.id.llTicket);
//                TextView tvBookIndi = (TextView) viewScheduleRow.findViewById(R.id.tvBookIndi);
//                tvBookIndi.setTypeface(CustomFonts.getLightFont(getActivity()));
//                LinearLayout llCal = (LinearLayout) viewScheduleRow.findViewById(R.id.llCal);
//
//
//
//
//                tvMatch.setTypeface(CustomFonts.getRegularFont(getActivity()));
//                tvTime.setTypeface(CustomFonts.getLightFont(getActivity()));
//
//                HashMap<String, String> map = list.get(i);
//
//                String team1 = map.get("team1");
//                String team2 = map.get("team2");
//                String time = map.get("time");
//                String venue = map.get("venue");
//
//                if(venue.toUpperCase().equals("JAIPUR")){
//                    LinearLayout llBo = (LinearLayout) viewScheduleRow.findViewById(R.id.llBo);
//                    llBo.setVisibility(View.VISIBLE);
//                    //ivTickets.setVisibility(View.VISIBLE);
//                    //llTicket.setVisibility(View.VISIBLE);
//                }
//
//                tvMatch.setText(team1 + " VS " + team2);
//                tvTime.setText(time + "(IST)");
//
//
//
//                llSchedule.addView(viewScheduleRow);
//            }
//        } else {
//            //istView.setEmptyView(tvNoBets);
//        }
//    }
//
//    public void populate(String team1, String team2, String time, String venue) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("team1", team1);
//        map.put("team2", team2);
//        map.put("time", time);
//        map.put("venue", venue);
//        list.add(map);
//    }
//
//    public int getTeamDrawable(String id) {
//
//        int teamId = Integer.parseInt(id);
//        TypedArray teamLogos = getActivity().getResources().obtainTypedArray(R.array.teamLogo);
//
//        return teamLogos.getResourceId(teamId - 1, -1);
//    }

    }


