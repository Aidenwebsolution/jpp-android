package com.jaipurpinkpanthers.android.adapters;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.InternetOperations;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;


public class GalleryAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    int lastAnimatedPosition=-1;

    public GalleryAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true).showImageOnLoading(R.drawable.loadingnews)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        // UNIVERSAL IMAGE LOADER SETUP
        /*DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                activity)
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(1024 * 1024).build();

        ImageLoader.getInstance().init(config);*/
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
        ImageView ivGallery;
        TextView tvTitle;
        FrameLayout flGallery;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_gallery_one, null); //change the name of the layout

            holder.flGallery = (FrameLayout) convertView.findViewById(R.id.flGallery);

            holder.ivGallery = (ImageView) convertView.findViewById(R.id.ivGallery); //find the different Views
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

            holder.tvTitle.setTypeface(CustomFonts.getBoldFont(activity));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        HashMap<String, String> map = list.get(position);

        String id = map.get("id");
        String title = map.get("title");
        String image = map.get("image");

        String tag = id+"#"+title;

        holder.tvTitle.setText(title);

        holder.flGallery.setTag(tag);

        image = InternetOperations.SERVER_UPLOADS_URL + image;
        //image = InternetOperations.SERVER_THUMB_URL + image + InternetOperations.SERVER_WIDTH_400;
        //imageLoader.displayImage(image, holder.ivGallery, options);

        //holder.tvTitle.setText(image);

        Glide.with(activity)
                .load(image)
                .asBitmap()
                .override(300, 300)
                .placeholder(R.drawable.loadingnews)
                .centerCrop()
                .into(holder.ivGallery);

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
