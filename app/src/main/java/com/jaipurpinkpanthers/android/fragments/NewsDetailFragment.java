package com.jaipurpinkpanthers.android.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.jaipurpinkpanthers.android.MainActivity;
import com.jaipurpinkpanthers.android.R;

import java.util.HashMap;

public class NewsDetailFragment extends Fragment {

    View view;
    HashMap<String,String> map;
    ImageView ivNewsImageInside;
    TextView tvNewsTitleInside, tvNewsDateInside, tvNewsDescInside;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        ((MainActivity)this.getActivity()).setToolbarText("NEWS & MEDIA");

        map = ((MainActivity) this.getActivity()).getNewsDetails();

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


        initilizeViews();

        return view;
    }

    public void initilizeViews(){

        ivNewsImageInside = (ImageView) view.findViewById(R.id.ivNewsImageInside);
        tvNewsTitleInside = (TextView) view.findViewById(R.id.tvNewsTitleInside);
        tvNewsDateInside = (TextView) view.findViewById(R.id.tvNewsDateInside);
        tvNewsDescInside = (TextView) view.findViewById(R.id.tvNewsDescInside);


        String title = map.get("title");
        String date = map.get("date");
        String desc = map.get("desc");
        String image = map.get("image");

        tvNewsTitleInside.setText(title);
        tvNewsDateInside.setText(date);
        tvNewsDescInside.setText(desc);

        imageLoader.displayImage(image, ivNewsImageInside, options);

    }


}
