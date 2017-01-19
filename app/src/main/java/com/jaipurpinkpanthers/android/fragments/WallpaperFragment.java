package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
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

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.WallpaperActivity;
import com.jaipurpinkpanthers.android.adapters.AlbumAdapter;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WallpaperFragment extends Fragment {
    View view;
    private int id;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private Activity activity;
    public GridView gvImages;
    ArrayList<HashMap<String, String>> list;
    ArrayList<String> links;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallpaper, container, false);

        //id = ((MainActivity) this.getActivity()).getGalleryId();

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        initilizeViews();

        return view;
    }

    public void initilizeViews() {

        links = new ArrayList<String>();
        list = new ArrayList<HashMap<String, String>>();
        gvImages = (GridView) view.findViewById(R.id.gvImages);

        if (InternetOperations.checkIsOnlineViaIP()) {
            getAlbumData();
        } else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Please check your Internet Connection!", Toast.LENGTH_SHORT).show();
        }

    }

    public void getAlbumData() {

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
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getWallpaper?type=1");

                    jsonArray = new JSONArray(response);

                    for (int j = 0; j < jsonArray.length(); j++) {

                        String id = null, name = null, image = null;

                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        id = jsonObject.optString("id");
                        name = jsonObject.optString("name");
                        image = jsonObject.optString("image");

                        populate(id, name, image);

                        addLinks(image);
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
            ((WallpaperActivity) this.getActivity()).setImageLinks(links);
            AlbumAdapter albumAdapter = new AlbumAdapter(getActivity(), list);
            gvImages.setAdapter(albumAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }
    }

    public void populate(String id, String name, String image) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        //map.put("title", name);
        map.put("image", image);
        list.add(map);
    }

    public void addLinks(String image) {
        links.add(image);
    }
}

