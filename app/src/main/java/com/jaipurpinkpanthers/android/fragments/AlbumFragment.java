package com.jaipurpinkpanthers.android.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jaipurpinkpanthers.android.MainActivity;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.adapters.AlbumAdapter;
import com.jaipurpinkpanthers.android.util.InternetOperations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AlbumFragment extends Fragment {

    View view;
    private int id;
    //private ImageLoader imageLoader;
    //private DisplayImageOptions options;
    //private Activity activity;
    public GridView gvImages;
    ArrayList<HashMap<String, String>> list;
    ArrayList<String> links;
    AsyncTask asyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_album, container, false);

        id = ((MainActivity) this.getActivity()).getGalleryId();

        /*imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP*/

        initilizeViews();

        return view;
    }

    public void initilizeViews(){

        links = new ArrayList<String>();
        list = new ArrayList<HashMap<String, String>>();
        gvImages = (GridView) view.findViewById(R.id.gvImages);
        getAlbumData();
    }

    public void getAlbumData(){

        asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String response;
                JSONArray jsonArray = null;
                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getGallerySlide?galleryid="+id);

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

                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
                } catch (JSONException je) {
                    Log.e("JPP", Log.getStackTraceString(je));
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                refresh();
            }
        }.execute(null, null, null);
    }


    public void refresh() {
        if (list.size() > 0) {
            ((MainActivity)this.getActivity()).setImageLinks(links);
            AlbumAdapter albumAdapter = new AlbumAdapter(getActivity(), list);
            gvImages.setAdapter(albumAdapter);
        } else {
            //istView.setEmptyView(tvNoBets);
        }
    }

    public void populate(String id, String name, String image) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("image", image);
        list.add(map);
    }

    public void addLinks(String image){
        links.add(image);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        asyncTask.cancel(true);
    }
}
