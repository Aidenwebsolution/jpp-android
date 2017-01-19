package com.jaipurpinkpanthers.android;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.jaipurpinkpanthers.android.util.InternetOperations;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SlideShowActivity extends AppCompatActivity {
    ViewFlipper vfSlide;
    private float initialXPoint;
    //String[] images;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    ArrayList<String> images;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        Intent intent = this.getIntent();
        int position = Integer.parseInt(intent.getStringExtra("position"));
        int wallpaper = Integer.parseInt(intent.getStringExtra("wallpaper"));

        images = intent.getStringArrayListExtra("links");
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).showImageOnLoading(R.drawable.loadingnews)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565)
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(SlideShowActivity.this)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP


        vfSlide = (ViewFlipper) findViewById(R.id.vfSlide);

        for (int i = 0; i < images.size(); i++) {
            if (wallpaper == 1) {
                addWallpaperImageInFlipper(images.get(i));
            } else {
                addImageInFlipper(images.get(i));
            }
        }

        vfSlide.setDisplayedChild(position);
    }

    public void addImageInFlipper(String url) {

        LayoutInflater inflator = getLayoutInflater();
        View viewFlipperSingleImage = inflator.inflate(R.layout.layout_single_image_flipper, null, false);

        ImageView ivBig = (ImageView) viewFlipperSingleImage.findViewById(R.id.ivBig); //find the different Views
        //ImageView ivDownload = (ImageView) viewFlipperSingleImage.findViewById(R.id.ivDownload);
        //ivDownload.setTag(InternetOperations.SERVER_UPLOADS_URL + url);
        String image = InternetOperations.SERVER_THUMB_URL + url + InternetOperations.SERVER_WIDTH_400;
        imageLoader.displayImage(image, ivBig, options);

        vfSlide.addView(viewFlipperSingleImage);

    }

    public void addWallpaperImageInFlipper(String url) {

        LayoutInflater inflator = getLayoutInflater();
        View viewFlipperSingleImage = inflator.inflate(R.layout.layout_single_image_flipper, null, false);

        ImageView ivBig = (ImageView) viewFlipperSingleImage.findViewById(R.id.ivBig); //find the different Views
        ImageView ivDownload = (ImageView) viewFlipperSingleImage.findViewById(R.id.ivDownload);
        ivDownload.setVisibility(View.VISIBLE);
        ivDownload.setTag(InternetOperations.SERVER_UPLOADS_URL + url);
        String image = InternetOperations.SERVER_THUMB_URL + url + InternetOperations.SERVER_WIDTH_400;
        imageLoader.displayImage(image, ivBig, options);

        vfSlide.addView(viewFlipperSingleImage);

    }

    // Using the following method, we will handle all screen swaps.
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {


            case MotionEvent.ACTION_DOWN:
                initialXPoint = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchevent.getX();


                // Handling left to right screen swap.
                if (initialXPoint < currentX) {


                    // If there aren't any other children, just break.
                    if (vfSlide.getDisplayedChild() == 0)
                        break;


                    // Next screen comes in from left.
                    vfSlide.setInAnimation(this, R.anim.slide_right_out);
                    // Current screen goes out from right.
                    vfSlide.setOutAnimation(this, R.anim.slide_right_in);


                    // Display next screen.
                    vfSlide.showPrevious();
                }


                // Handling right to left screen swap.
                if (initialXPoint > currentX) {


                    // If there is a child (to the left), kust break.
                    if (vfSlide.getDisplayedChild() == vfSlide.getChildCount() - 1)
                        break;


                    // Next screen comes in from right.
                    vfSlide.setInAnimation(this, R.anim.slide_left_in);
                    // Current screen goes out from left.
                    vfSlide.setOutAnimation(this, R.anim.slide_left_out);


                    // Display previous screen.
                    vfSlide.showNext();
                }
                break;
        }
        return false;
    }

    public void cross(View v) {
        finish();
    }

    public void downloadMedia(View v) {

        Toast.makeText(this, "Download started...", Toast.LENGTH_SHORT).show();

        String url = v.getTag().toString();
        getImage(url, getApplicationContext());
    }

    public void getImage(final String url, final Context context) {

        new AsyncTask<Void, Void, String>() {
            boolean done = false;

            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }

                byte[] media = null;
                try {
                    media = getMedia(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Random randomGenerator = new Random();
                String randomNumber = String.valueOf(randomGenerator.nextInt(10000));
                String name = randomNumber + ".png";

                try {
                    File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "/JPP/JPP Images/" + name);
                    if (!mediaStorageDir.exists()) {
                        Bitmap b = BitmapFactory.decodeByteArray(media, 0, media.length);
                        b.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(mediaStorageDir));

                        // Refreshing Gallery to view Image in Gallery
                        //MainActivity mainActivity = new MainActivity();

                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.DATA, mediaStorageDir.getAbsolutePath());
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                        done = true;
                    }

                } catch (FileNotFoundException fe) {
                    Log.e("JPP", Log.getStackTraceString(fe));
                } catch (NullPointerException npe) {
                    Log.e("JPP", Log.getStackTraceString(npe));
                }

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (done) {
                    Toast.makeText(context, "Downloaded!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }


            }
        }.execute(null, null, null);

    }

    byte[] getMedia(String url) throws IOException {
        //RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().bytes();
    }
}
