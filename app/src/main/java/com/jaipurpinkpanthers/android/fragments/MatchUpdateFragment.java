package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.MatchRecycleAdapter;
import com.jaipurpinkpanthers.android.adapters.MatchUpdateGetter;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchUpdateFragment extends Fragment {

    RecyclerView.Adapter RecyclerAdapter;
    RecyclerView.LayoutManager RecyclerlayoutManager;

    public static final String TAG_team1 = "team1";
    public static final String TAG_team2 = "team2";
    public static final String TAG_score1 = "score1";
    public static final String TAG_score2 = "score2";
    public static final String TAG_stadium = "stadium";
    public static final String TAG_starttimedate = "starttimedate";
    public static final String TAG_team1id = "team1id";
    public static final String TAG_team2id = "team2id";
    public static final String TAG_matchtime = "matchtime";
    public static final String TAG_teamimage1 = "teamimage1";
    public static final String TAG_teamimage2 = "teamimage2";

    public ArrayList<String> team1 = new ArrayList<>();
    public ArrayList<String> team2 = new ArrayList<>();
    public ArrayList<String> score1 = new ArrayList<>();
    public ArrayList<String> score2 = new ArrayList<>();
    public ArrayList<String> stadium = new ArrayList<>();
    public ArrayList<String> starttimedate = new ArrayList<>();
    public ArrayList<String> team1id = new ArrayList<>();
    public ArrayList<String> team2id = new ArrayList<>();
    public ArrayList<String> matchtime = new ArrayList<>();
    public ArrayList<String> teamimage1 = new ArrayList<>();
    public ArrayList<String> teamimage2 = new ArrayList<>();

    Activity activity;
    SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<MatchUpdateGetter> matchUpdateGetter = new ArrayList<MatchUpdateGetter>();

    View view;
    RecyclerView recyclerView;
    ArrayList<HashMap<String, String>> list;
    ProgressDialog progressDialog;
    AsyncTask asyncTask;
    //Handler handler = getActivity().getWindow().getDecorView().getHandler();
    private static Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_matchupdate, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.lvmatchupdate);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.jppAccentColor,R.color.myPrimaryColor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getdata();

            }
        });
        activity=getActivity();


        getdata();

        return view;
    }

    public void getdata() {

        String DATA_URL = InternetOperations.SERVER_URL +"getScheduleForIosAndroidSeason4";



        //Creating a json array request to get the json from our api
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Dismissing the progressdialog on response
                //progressBar.setVisibility(View.GONE);
                //linlaProgressBar.setVisibility(View.GONE);


                show(response);
                swipeRefreshLayout.setRefreshing(false);



                        /*if (mySwipeRefreshLayout.isRefreshing()){
                            mySwipeRefreshLayout.setRefreshing(false);
                        }*/
                //mySwipeRefreshLayout.setRefreshing(false);
                //Displaying our grid


            }

        },
                new Response.ErrorListener() {
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
                }
        );

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        //Adding our request to the queue
        requestQueue.add(jsonArrayRequest);

    }

    private void show(JSONArray jsonArray) {
        //Looping through all the elements of json array

        team1.clear();
        team2.clear();
        score1.clear();
        score2.clear();
        stadium.clear();
        starttimedate.clear();
        team1id.clear();
        team2id.clear();
        matchtime.clear();
        teamimage1.clear();
        teamimage2.clear();
        matchUpdateGetter.clear();

        //if (images.isEmpty()) {

        for (int i = 0; i < jsonArray.length(); i++) {
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
                teamimage1.add(obj.getString(TAG_teamimage1));
                teamimage2.add(obj.getString(TAG_teamimage2));
                //count++;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //}

        int count =team1.size();
        //Creating GridViewAdapter Object
        for (int i = 0; i < team1.size(); i++) {
            MatchUpdateGetter current = new MatchUpdateGetter(team1.get(i), team2.get(i), score1.get(i),
                    score2.get(i), stadium.get(i), starttimedate.get(i), team1id.get(i), team2id.get(i),
                    matchtime.get(i),teamimage1.get(i),teamimage2.get(i), String.valueOf(count));
            matchUpdateGetter.add(current);

            count--;
            Log.e("ItemInfoGetter: ", "" + i);
        }

        RecyclerAdapter = new MatchRecycleAdapter(matchUpdateGetter, getActivity());
        /*recyclerView.setItemAnimator(new SlideInUpAnimator());
        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);*/
        recyclerView.setHasFixedSize(true);
        //RecyclerAdapter.setHasStableIds(true);
        ///RecyclerlayoutManager = new LinearLayoutManager();
        RecyclerlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(RecyclerlayoutManager);
        recyclerView.setAdapter(RecyclerAdapter);
        RecyclerAdapter.notifyDataSetChanged();

    }



}
