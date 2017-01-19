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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.jaipurpinkpanthers.android.MainActivity;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.GalleryAdapter;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GalleryFragment extends Fragment {
    View viewPhotos, viewVideos;
    View view;
    LinearLayout llPhotos, llVideos;
    ViewFlipper vfGallery;
    TextView tvPhotos, tvVideos;
    ListView lvPhotos, lvVideos;
    ArrayList<HashMap<String, String>> list;
    ArrayList<HashMap<String, String>> listVideo;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ((MainActivity) this.getActivity()).setToolbarText("GALLERY");


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        initializeViews();

        return view;
    }

    public void initializeViews() {

        viewPhotos = view.findViewById(R.id.viewPhotos);
        //viewVideos = view.findViewById(R.id.viewVideos);
        viewPhotos.setBackgroundColor(getResources().getColor(R.color.jppAccentColor));

        tvPhotos = (TextView) view.findViewById(R.id.tvPhotos);
        //tvVideos = (TextView) view.findViewById(R.id.tvVideos);
        tvPhotos.setTypeface(CustomFonts.getLightFont(getActivity()));
        //tvVideos.setTypeface(CustomFonts.getLightFont(getActivity()));

        llPhotos = (LinearLayout) view.findViewById(R.id.llPhotos);
        //llVideos = (LinearLayout) view.findViewById(R.id.llVideos);

        vfGallery = (ViewFlipper) view.findViewById(R.id.vfGallery);
        //vfGallery.setFlipInterval(500);

        list = new ArrayList<HashMap<String, String>>();
        //listVideo = new ArrayList<HashMap<String, String>>();
        lvPhotos = (ListView) view.findViewById(R.id.lvPhotos);
        //lvVideos = (ListView) view.findViewById(R.id.lvVideos);

        setListeners();

        if (InternetOperations.checkIsOnlineViaIP()) {
            getGalleryData();
        } else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public void setListeners() {

        llPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewVideos.setBackgroundColor(getResources().getColor(R.color.jppGalleryToolbar));
                viewPhotos.setBackgroundColor(getResources().getColor(R.color.jppAccentColor));

                if (vfGallery.getDisplayedChild() != 0)
                    vfGallery.showPrevious();
            }
        });

        /*llVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPhotos.setBackgroundColor(getResources().getColor(R.color.jppGalleryToolbar));
                viewVideos.setBackgroundColor(getResources().getColor(R.color.jppAccentColor));

                if (vfGallery.getDisplayedChild() != 1)
                    vfGallery.showNext();
            }
        });*/
    }

    public void getGalleryData() {

        new AsyncTask<Void, Void, String>() {
            boolean done = false;

            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                //JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getallgallery");

                    jsonArray = new JSONArray(response);

                    for (int j = 0; j < jsonArray.length(); j++) {

                        String id = null, order = null, name = null, image = null;

                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        id = jsonObject.optString("id");
                        name = jsonObject.optString("name");
                        image = jsonObject.optString("image");

                        populate(id, name, image);
                    }

                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
                } catch (JSONException je) {
                    Log.e("JPP", Log.getStackTraceString(je));
                } catch (Exception e){
                    Log.e("JPP", Log.getStackTraceString(e));
                }


                JSONArray jsonArrayVideo = null;
                try {
                    String res = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getAllVideoGallery");

                    jsonArrayVideo = new JSONArray(res);

                    for (int j = 0; j < jsonArrayVideo.length(); j++) {

                        String id = null, url = null, name = null;
                        JSONObject jsonObject = jsonArrayVideo.getJSONObject(j);
                        name = jsonObject.optString("name");
                        url = jsonObject.optString("url");

                        //populateVideo(name, url);
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
            GalleryAdapter galleryAdapter = new GalleryAdapter(getActivity(), list);
            lvPhotos.setAdapter(galleryAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }

        /*if (listVideo.size() > 0) {
            VideoAdapter videoAdapter = new VideoAdapter(getActivity(), listVideo);
            lvVideos.setAdapter(videoAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }*/

    }

    public void populate(String id, String name, String image) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("title", name);
        map.put("image", image);
        list.add(map);
    }

    /*public void populateVideo(String name, String url) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("title", name);
        map.put("url", url);
        listVideo.add(map);
    }*/
}
