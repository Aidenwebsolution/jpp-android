package com.jaipurpinkpanthers.android.fragments;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.PointsAdapter;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PointsFragment extends Fragment {
    View view;
    TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;
    ListView lvTeams;
    ArrayList<HashMap<String, String>> list;
    ProgressDialog progressDialog;

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

        initilizeViews();

        return view;
    }

    public void initilizeViews() {

        list = new ArrayList<HashMap<String, String>>();

        tvNo = (TextView) view.findViewById(R.id.tvNo);
        tvTeam = (TextView) view.findViewById(R.id.tvTeam);
        tvP = (TextView) view.findViewById(R.id.tvP);
        tvW = (TextView) view.findViewById(R.id.tvW);
        tvL = (TextView) view.findViewById(R.id.tvL);
        tvPts = (TextView) view.findViewById(R.id.tvPts);
        lvTeams = (ListView) view.findViewById(R.id.lvTeams);

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
                String response;
                JSONArray jsonArray = null;
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getallpoint");

                    jsonArray = new JSONArray(response);

                    if (jsonArray.length() != 0) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = String.valueOf(i + 1);
                            String name = jsonObject.optString("name");
                            String p = jsonObject.optString("played");
                            String w = jsonObject.optString("wins");
                            String l = jsonObject.optString("lost");
                            String points = jsonObject.optString("point");
                            populate(id, name, p, w, l, points);
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
                    refresh();
                } else {
                    Toast.makeText(getActivity(), "Oops, Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(null, null, null);

    }

    public void refresh() {
        if (list.size() > 0) {
            PointsAdapter pointsAdapter = new PointsAdapter(getActivity(), list);
            lvTeams.setAdapter(pointsAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }
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
}
