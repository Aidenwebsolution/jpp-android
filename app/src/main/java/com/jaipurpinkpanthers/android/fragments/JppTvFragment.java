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
import com.jaipurpinkpanthers.android.adapters.VideoAdapter;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JppTvFragment extends Fragment{

        View  viewVideos;
        View view;
        LinearLayout  llVideos;
        ViewFlipper vfGallery;
        TextView  tvVideos;
        ListView lvVideos;

        ArrayList<HashMap<String, String>> listVideo;
        ProgressDialog progressDialog;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_gallery_video, container, false);
            ((MainActivity) this.getActivity()).setToolbarText("JPP TV");

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            initializeViews();

            return view;
        }

        public void initializeViews() {
            viewVideos = view.findViewById(R.id.viewVideos);

            tvVideos = (TextView) view.findViewById(R.id.tvVideos);
            tvVideos.setTypeface(CustomFonts.getLightFont(getActivity()));

            llVideos = (LinearLayout) view.findViewById(R.id.llVideos);

            vfGallery = (ViewFlipper) view.findViewById(R.id.vfGallery);
            vfGallery.setFlipInterval(500);

            listVideo = new ArrayList<HashMap<String, String>>();
            lvVideos = (ListView) view.findViewById(R.id.lvVideos);

            setListeners();

            if (InternetOperations.checkIsOnlineViaIP()) {
                getGalleryData();
            } else {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
            }
        }

        public void setListeners() {
            llVideos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewVideos.setBackgroundColor(getResources().getColor(R.color.jppAccentColor));

                    if (vfGallery.getDisplayedChild() != 1)
                        vfGallery.showNext();
                }
            });
        }

        public void getGalleryData() {
            new AsyncTask<Void, Void, String>() {
                boolean done = false;

                @Override
                protected String doInBackground(Void... params) {

                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    //String response;

                    JSONArray jsonArrayVideo = null;
                    try {
                        String res = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getAllVideoGallery");

                        jsonArrayVideo = new JSONArray(res);

                        for (int j = 0; j < jsonArrayVideo.length(); j++) {

                            String id = null, url = null, name = null;
                            JSONObject jsonObject = jsonArrayVideo.getJSONObject(j);
                            name = jsonObject.optString("name");
                            url = jsonObject.optString("url");

                            populateVideo(name, url);
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
            if (listVideo.size() > 0) {
                VideoAdapter videoAdapter = new VideoAdapter(getActivity(), listVideo);
                lvVideos.setAdapter(videoAdapter);
            } else {
                //istView.setEmptyView(tvNoBets);
            }

        }

        public void populateVideo(String name, String url) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", name);
            map.put("url", url);
            listVideo.add(map);
        }
    }

