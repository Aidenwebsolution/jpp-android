package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.PointsAdapter;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.InternetOperations;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PointsFragment extends Fragment {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;
    ListView lvTeamsA,lvTeamsB;
    ArrayList<HashMap<String, String>> listA,listB;
    ProgressDialog progressDialog;
    LinearLayout llptheader,sponsers;
    ImageView home_iv_sponsor;
    String sponsor;
    Activity activity;
    ImageLoader imageLoader;
    DisplayImageOptions options;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_points, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        activity = getActivity();

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisc(false).resetViewBeforeLoading(true).build();
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

    public void initilizeViews() {

        listA = new ArrayList<HashMap<String, String>>();
        listB = new ArrayList<HashMap<String, String>>();

        llptheader= (LinearLayout) view.findViewById(R.id.llptheader);
        sponsers= (LinearLayout) view.findViewById(R.id.sponsers);
        home_iv_sponsor= (ImageView) view.findViewById(R.id.home_iv_sponsor);
        tvNo = (TextView) view.findViewById(R.id.tvNo);
        tvTeam = (TextView) view.findViewById(R.id.tvTeam);
        tvP = (TextView) view.findViewById(R.id.tvP);
        tvW = (TextView) view.findViewById(R.id.tvW);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvPts = (TextView) view.findViewById(R.id.tvPts);
        lvTeamsA = (ListView) view.findViewById(R.id.lvTeamsA);
        lvTeamsB = (ListView) view.findViewById(R.id.lvTeamsB);

        tvNo.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvNo.setTextColor(Color.parseColor("black"));
        tvTeam.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvTeam.setTextColor(Color.parseColor("black"));
        tvP.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvP.setTextColor(Color.parseColor("black"));
        tvW.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvW.setTextColor(Color.parseColor("black"));
        tvL.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvL.setTextColor(Color.parseColor("black"));
        tvPts.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvPts.setTextColor(Color.parseColor("black"));

        if (InternetOperations.checkIsOnlineViaIP()) {
            getPointsTableData();
        } else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getPointsTableData() {

        new AsyncTask<Void, Void, String>() {
            boolean done = false;

            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response ;
                JSONArray jsonArray = null;
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getallpoint");
                    Log.d("getsponsorimageresponse",response);

                    jsonArray = new JSONArray(response);
                    JSONObject zoneA = jsonArray.getJSONObject(0);
                    JSONArray pointsTableA = new JSONArray(zoneA.optString("pointsTableA"));
                    JSONObject zoneB = jsonArray.getJSONObject(1);
                    JSONArray pointsTableB = new JSONArray(zoneB.optString("pointsTableB"));


                    if (pointsTableA.length() != 0) {

                        for (int i = 0; i < pointsTableA.length(); i++) {
                            JSONObject jsonObject = pointsTableA.getJSONObject(i);
                            String id = String.valueOf(i + 1);
                            String name = jsonObject.optString("name");
                            String p = jsonObject.optString("played");
                            String w = jsonObject.optString("wins");
                            String l = jsonObject.optString("lost");
                            String points = jsonObject.optString("point");
                            populate(id, name, p, w, l, points,listA);
                        }
                    }
                    if (pointsTableB.length() != 0) {

                        for (int i = 0; i < pointsTableB.length(); i++) {
                            JSONObject jsonObject = pointsTableB.getJSONObject(i);
                            String id = String.valueOf(i + 1);
                            String name = jsonObject.optString("name");
                            String p = jsonObject.optString("played");
                            String w = jsonObject.optString("wins");
                            String l = jsonObject.optString("lost");
                            String points = jsonObject.optString("point");
                            populate(id, name, p, w, l, points,listB);
                        }
                    }
                    done = true;
                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
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
                    llptheader.setVisibility(View.VISIBLE);

                    refresh();
                } else {
                    Toast.makeText(getActivity(), "Oops, Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(null, null, null);


        new AsyncTask<Void, Void, String>() {
            boolean done = false;

            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String  getsponsorimageresponse ;
                try {
                    getsponsorimageresponse = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getsponsorimage");

                    JSONObject getsponsorimage = new JSONObject(getsponsorimageresponse);
                    sponsor =getsponsorimage.optString("image");


                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
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
                    String imagesponsor = InternetOperations.SERVER_UPLOADS_URL + sponsor;
                    imageLoader.displayImage(imagesponsor, home_iv_sponsor, options);
                } else {
//                    Toast.makeText(getActivity(), "Oops, Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(null, null, null);
    }

    public void refresh() {
        if (listA.size() > 0) {
            PointsAdapter pointsAdapter = new PointsAdapter(getActivity(), listA);
            lvTeamsA.setAdapter(pointsAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }
        if (listB.size() > 0) {
            PointsAdapter pointsAdapter = new PointsAdapter(getActivity(), listB);
            lvTeamsB.setAdapter(pointsAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }
    }

    public void populate(String n, String team, String p, String w, String l, String pts,ArrayList<HashMap<String, String>> list) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("tvNo", n);
        map.put("tvTeam", team);
        map.put("tvP", p);
        map.put("tvW", w);
        map.put("tvL", l);
        map.put("tvPts", pts);
        list.add(map);
    }
}
