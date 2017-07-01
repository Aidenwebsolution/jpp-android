package com.jaipurpinkpanthers.android.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.MainActivity;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.NewsAdapter;
import com.jaipurpinkpanthers.android.adapters.PanthersAdapter;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PanthersFragment extends Fragment {

    public GridView gvPlayers;
    public View view;
    ArrayList<HashMap<String, String>> list;
    ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_panthers, container, false);

        int id = ((MainActivity)this.getActivity()).getPlayerId();
        ((MainActivity)this.getActivity()).setToolbarText("KNOW YOUR PANTHERS");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        initilizeView();

        return view;
    }

    public void initilizeView(){
        Log.d("jsonArray", "djsakdjkl");
        list = new ArrayList<HashMap<String, String>>();

        gvPlayers = (GridView) view.findViewById(R.id.gvPlayers);
        new AsyncTask<Void, Void, String>() {
            boolean done = false;


            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getallplayers");

//                    jsonArray = new JSONArray(response);
                    jsonObject = new JSONObject(response);
                    Log.d("jsonArray", String.valueOf(jsonObject));
                    String jObjectString = jsonObject.optString("queryresult");
                    jsonArray = new JSONArray(jObjectString);
                    for (int j = 0; j < jsonArray.length(); j++) {

                        /*"id": "1",
                            "name": "Lorem Ipsum",
                            "image": "n11.jpg",
                            "timestamp": "27 Jan 2016",
                            "content": "Lorem Ipsum is*/
                        String id = null, name = null, image = null, type = null;

                        JSONObject player = jsonArray.getJSONObject(j);
                        id = player.optString("id");
                        type = player.optString("type");
                        name = player.optString("name");
                        image = player.optString("smallimage");


                        populate(id,type,name, image);
                    }

                    done = true;

                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
                } catch (JSONException je) {
                    Log.e("JPP", Log.getStackTraceString(je));
                } catch (Exception e){
                    Log.e("JPP", Log.getStackTraceString(e));
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                Log.d("done", String.valueOf(done));
                if (done) {
                    refresh();
                } else {
                    Toast.makeText(getActivity(), "Oops, Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(null, null, null);


//        ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String, String>>();
//
//        String[] players = getResources().getStringArray(R.array.player);
//
//        for(int i = 0; i < players.length; i++)
//        {
//            HashMap<String,String> map = new HashMap<String,String>();
//            map.put("PlayerInfo", players[i]); //this will send id, player name and image
//            list.add(map);
//        }
//
//        PanthersAdapter panthersAdapter = new PanthersAdapter(getActivity(),list);
//
//        gvPlayers.setAdapter(panthersAdapter);
    }
    public void refresh() {
        if (list.size() > 0) {
            PanthersAdapter panthersAdapter = new PanthersAdapter(getActivity(),list);

            gvPlayers.setAdapter(panthersAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }
    }
    public void populate(String id,String type, String name, String image) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("type", type);
        map.put("name", name);
        map.put("image", image);
        list.add(map);
        Log.d("list", String.valueOf(list));

    }

}
