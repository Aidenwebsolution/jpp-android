package com.jaipurpinkpanthers.android.fragments;

        import android.app.Activity;
        import android.app.Fragment;
        import android.app.ProgressDialog;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Looper;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.jaipurpinkpanthers.android.R;
        import com.jaipurpinkpanthers.android.util.CustomFonts;
        import com.jaipurpinkpanthers.android.util.ImageCircle;
        import com.jaipurpinkpanthers.android.util.InternetOperations;
        import com.nostra13.universalimageloader.core.DisplayImageOptions;
        import com.nostra13.universalimageloader.core.ImageLoader;
        import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
        import com.nostra13.universalimageloader.core.assist.ImageScaleType;
        import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;

public class SponsorsFragment extends Fragment {

    View view;
    String sponsor;
    private Activity activity;
    ImageView ivsponsorbanner;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sponsors, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        activity = getActivity();

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisc(false).resetViewBeforeLoading(true).build();
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                activity)
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        initilizeViews();

        return view;
    }

    public void initilizeViews(){
        activity = getActivity();
        ivsponsorbanner = (ImageView) view.findViewById(R.id.ivsponsorbanner);


        new AsyncTask<Void, Void, String>() {
            boolean done = false;

            @Override
            protected String doInBackground(Void... params) {

                if (Looper.myLooper() == null) {
                    Looper.prepare();
                }
                String getsponsorimageresponse;


                try {

                    getsponsorimageresponse = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getsponsorimage");

                    JSONObject getsponsorimage = new JSONObject(getsponsorimageresponse);
                    sponsor =getsponsorimage.optString("image");

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

                    ivsponsorbanner.setVisibility(View.VISIBLE);
                    String imagesponsor = InternetOperations.SERVER_UPLOADS_URL + sponsor;
                    imageLoader.displayImage(imagesponsor, ivsponsorbanner, options);
                } else {
                    Toast.makeText(activity, "Oops, Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute(null, null, null);
    }


}
