package com.jaipurpinkpanthers.android.adapters;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.InternetOperations;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jitu on 28-01-2016.
 */
public class AlbumAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    TypedArray playerImages;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    int lastAnimatedPosition=-1;
    public AlbumAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;

        playerImages = activity.getResources().obtainTypedArray(R.array.playerImages);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false).bitmapConfig(Bitmap.Config.RGB_565).showImageOnLoading(R.drawable.loadingpink)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(false).cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                activity)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        //all the fields in layout specified
        ImageView ivSingle;
        LinearLayout llAldbumSingle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_album_single, null); //change the name of the layout

            holder.ivSingle = (ImageView) convertView.findViewById(R.id.ivSingle); //find the different Views
            holder.llAldbumSingle = (LinearLayout) convertView.findViewById(R.id.llAldbumSingle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        HashMap<String, String> map = list.get(position);

        String image = map.get("image");
        String url = InternetOperations.SERVER_UPLOADS_URL + image;

        String imageUri = InternetOperations.SERVER_THUMB_URL + image +InternetOperations.SERVER_WIDTH_250;
        //imageLoader.displayImage(imageUri, holder.ivSingle,options);

        Glide.with(activity)
                .load(url)
                .asBitmap()
                .override(300, 300)
                .placeholder(R.drawable.loadingnews)
                .centerCrop()
                .into(holder.ivSingle);

        holder.llAldbumSingle.setTag(position);
        //setScaleAnimation(convertView);
        runEnterAnimation(convertView,position);

        return convertView;
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(300);
        view.startAnimation(anim);
    }

    private void runEnterAnimation(View view, int position) {


        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            DisplayMetrics metrics = new DisplayMetrics();
            //ctx.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int height = metrics.heightPixels;
            int width = metrics.widthPixels;

            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(300);
            view.startAnimation(anim);
            view.setTranslationY(200);
            view.animate()
                    .translationY(0)
                    .setDuration(600)
                    .start();

        }
    }
}