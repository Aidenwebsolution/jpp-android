package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaipurpinkpanthers.android.MainActivity;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.CustomFonts;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PlayerDescriptionFragment extends Fragment {
    private View view;
    private LinearLayout llPlayerDesc,llstat;
    private ImageView ivPlayer;
    private ImageView toolbar;
    private Button btAchieveDesc ,btTourDesc,btcareer,btcurrent,btlastseason;
    public ArrayList<HashMap<String, String>> list;
    ImageLoader imageLoader;
    DisplayImageOptions options;


    private TextView tvName,tvDOBval, tvNo,tvStat,tvType,tvnative, tvWeightval,tvheight,tvWeight,tvheightval,tvNationality, tvDOB,tvnativeval, tvJerseyNo, tvDesc, tvNat, tvBorn, tvJer, tvTour, tvTourDesc, tvAchieve, tvAchieveDesc;
    private TextView tvplayed,tvtotalpts,tvtotalraidpts,tvtotaldefensepts,tvredcard,tvgreencard,tvyellowcard,tvsuccessfulraids,tvunsuccessfulraids,tvtackles,tvsuccesstackles,tvunsuccesstackles,tvraids,tvemptyraids;
    private Activity activity;
    ProgressDialog progressDialog;
    String playerName , playerType , playerNationality, playerJerseyNum, playerTourDesc, playerAchievmantDesc, playerImg,weight,height,nativeplace,dob;
    String careerObj,currentObj,lastseasonObj ,careerstatus,currentstatus,lastseasonstatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_player_description, container, false);

        int id = ((MainActivity) this.getActivity()).getPlayerId();
        Log.d("id", String.valueOf(id));

        //((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Please wait...");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        initilizeViews();
        getPlayerDetails(id);

        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();

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

        return view;
    }



    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initilizeViews() {

        activity = getActivity();

        llPlayerDesc = (LinearLayout) view.findViewById(R.id.llPlayerDesc);
        llstat = (LinearLayout) view.findViewById(R.id.llstat);

        ivPlayer = (ImageView) llPlayerDesc.findViewById(R.id.ivPlayer);
        tvName = (TextView) llPlayerDesc.findViewById(R.id.tvName);
        tvNo = (TextView) llPlayerDesc.findViewById(R.id.tvNo);
        tvType = (TextView) llPlayerDesc.findViewById(R.id.tvType);
        tvStat = (TextView) llPlayerDesc.findViewById(R.id.tvStat);
        tvNationality = (TextView) llPlayerDesc.findViewById(R.id.tvNationality);
        tvDOB = (TextView) llPlayerDesc.findViewById(R.id.tvDOB);
        tvnative = (TextView) llPlayerDesc.findViewById(R.id.tvnative);
        tvDOBval = (TextView) llPlayerDesc.findViewById(R.id.tvDOBval);
        tvWeightval = (TextView) llPlayerDesc.findViewById(R.id.tvWeightval);

        //career //current //lastseen
        tvplayed = (TextView) llPlayerDesc.findViewById(R.id.tvplayed);
        tvtotalpts = (TextView) llPlayerDesc.findViewById(R.id.tvtotalpts);
        tvtotalraidpts = (TextView) llPlayerDesc.findViewById(R.id.tvtotalraidpts);
        tvtotaldefensepts = (TextView) llPlayerDesc.findViewById(R.id.tvtotaldefensepts);
        tvgreencard = (TextView) llPlayerDesc.findViewById(R.id.tvgreencard);
        tvredcard = (TextView) llPlayerDesc.findViewById(R.id.tvredcard);
        tvyellowcard = (TextView) llPlayerDesc.findViewById(R.id.tvyellowcard);
        tvraids = (TextView) llPlayerDesc.findViewById(R.id.tvraids);
        tvsuccessfulraids = (TextView) llPlayerDesc.findViewById(R.id.tvsuccessfulraids);
        tvunsuccessfulraids = (TextView) llPlayerDesc.findViewById(R.id.tvunsuccessfulraids);
        tvemptyraids = (TextView) llPlayerDesc.findViewById(R.id.tvemptyraids);
        tvtackles = (TextView) llPlayerDesc.findViewById(R.id.tvtackles);
        tvsuccesstackles = (TextView) llPlayerDesc.findViewById(R.id.tvsuccesstackles);
        tvunsuccesstackles = (TextView) llPlayerDesc.findViewById(R.id.tvunsuccesstackles);

        //tvDesc = (TextView) llPlayerDesc.findViewById(R.id.tvDesc);
        tvTourDesc = (TextView) llPlayerDesc.findViewById(R.id.tvTourDesc);
        tvAchieveDesc = (TextView) llPlayerDesc.findViewById(R.id.tvAchieveDesc);
        btAchieveDesc = (Button) llPlayerDesc.findViewById(R.id.btAchieveDesc);
        btTourDesc = (Button) llPlayerDesc.findViewById(R.id.btTourDesc);
        btcareer = (Button) llPlayerDesc.findViewById(R.id.btcareer);
        btcurrent = (Button) llPlayerDesc.findViewById(R.id.btcurrent);
        btlastseason = (Button) llPlayerDesc.findViewById(R.id.btlastseason);

        tvNat = (TextView) llPlayerDesc.findViewById(R.id.tvNat);
        tvnativeval = (TextView) llPlayerDesc.findViewById(R.id.tvnativeval);
//        tvBorn = (TextView) llPlayerDesc.findViewById(R.id.tvBorn);
        tvheightval = (TextView) llPlayerDesc.findViewById(R.id.tvheightval);
        tvheight = (TextView) llPlayerDesc.findViewById(R.id.tvheight);
        tvWeight = (TextView) llPlayerDesc.findViewById(R.id.tvWeight);
        tvTour = (TextView) llPlayerDesc.findViewById(R.id.tvTour);
        tvAchieve = (TextView) llPlayerDesc.findViewById(R.id.tvAchieve);

        tvNo.setTypeface(CustomFonts.getDreamwalker(activity));
        tvName.setTypeface(CustomFonts.getDreamwalker(activity));
        tvStat.setTypeface(CustomFonts.getDreamwalker(activity));
        tvType.setTypeface(CustomFonts.getDreamwalker(activity));
        tvNationality.setTypeface(CustomFonts.getLightFont(activity));
        tvDOB.setTypeface(CustomFonts.getRegularFont(activity));
        tvnative.setTypeface(CustomFonts.getRegularFont(activity));
        tvheight.setTypeface(CustomFonts.getRegularFont(activity));
        tvWeight.setTypeface(CustomFonts.getRegularFont(activity));
        tvDOBval.setTypeface(CustomFonts.getLightFont(activity));
        tvheightval.setTypeface(CustomFonts.getLightFont(activity));
        tvWeightval.setTypeface(CustomFonts.getLightFont(activity));
        tvnativeval.setTypeface(CustomFonts.getLightFont(activity));
        //tvDesc.setTypeface(CustomFonts.getLightFont(activity));
        tvTourDesc.setTypeface(CustomFonts.getLightFont(activity));
        tvAchieveDesc.setTypeface(CustomFonts.getLightFont(activity));

        tvNat.setTypeface(CustomFonts.getRegularFont(activity));
//        tvBorn.setTypeface(CustomFonts.getRegularFont(activity));
//        tvJer.setTypeface(CustomFonts.getRegularFont(activity));
        tvTour.setTypeface(CustomFonts.getRegularFont(activity));
        tvAchieve.setTypeface(CustomFonts.getRegularFont(activity));
        toolbar= (ImageView) view.findViewById(R.id.toolbar_image);
        tvTourDesc.setVisibility(view.VISIBLE);
        tvTour.setVisibility(view.VISIBLE);
        btTourDesc.setAlpha((float) 1);
        btAchieveDesc.setAlpha((float) 0.5);
        btcareer.setAlpha((float) 0.5);
        btlastseason.setAlpha((float) 0.5);
        btcurrent.setAlpha((float) 1);

        btcareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btcurrent.setAlpha((float) 0.5);
                btlastseason.setAlpha((float) 0.5);
                btcareer.setAlpha((float) 1);
                setcareer(careerObj);
            }
        });
        btcurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btcareer.setAlpha((float) 0.5);
                btlastseason.setAlpha((float) 0.5);
                btcurrent.setAlpha((float) 1);
                setcareer(currentObj);
            }
        });
        btlastseason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btcareer.setAlpha((float) 0.5);
                btcurrent.setAlpha((float) 0.5);
                btlastseason.setAlpha((float) 1);
                setcareer(lastseasonObj);
            }
        });
        btAchieveDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerAchievmantDesc==null){
                    tvAchieveDesc.setVisibility(View.GONE);

                    tvAchieve.setVisibility(View.GONE);
                }else{
                    tvAchieveDesc.setVisibility(view.VISIBLE);
                    tvAchieve.setVisibility(view.VISIBLE);
                }
                tvTourDesc.setVisibility(view.GONE);
                tvTour.setVisibility(view.GONE);
                btTourDesc.setAlpha((float) 0.5);
                btAchieveDesc.setAlpha((float) 1);
            }
        });
        btTourDesc.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (playerTourDesc==null){
                  tvTourDesc.setVisibility(View.GONE);
                  tvTour.setVisibility(View.GONE);
              }else{
                  tvTourDesc.setVisibility(view.VISIBLE);
                  tvTour.setVisibility(view.VISIBLE);
              }
              tvAchieveDesc.setVisibility(view.GONE);
              tvAchieve.setVisibility(view.GONE);
              btAchieveDesc.setAlpha((float) 0.5);
              btTourDesc.setAlpha((float) 1);

          }
      });



    }
//    public void showAchive(boolean show) {
//        Log.d("hi", String.valueOf(show));
//    if (show){
//        tvTourDesc.setVisibility(View.VISIBLE);
//        tvAchieveDesc.setVisibility(View.GONE);
//    }
//        else{
//        tvTourDesc.setVisibility(View.GONE);
//        tvAchieveDesc.setVisibility(View.VISIBLE);
//    }
//    }
    public void getPlayerDetails(final int id) {




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

                JSONObject playerid = new JSONObject();
                try {
                    playerid.put("id", id);

                } catch (JSONException je) {
                }
                String submitJson = playerid.toString();
                Log.d("submitJson", String.valueOf(id));

                try {
                    response = InternetOperations.postBlank(InternetOperations.SERVER_URL + "getsingleplayer" + "?id=" + id);

                    JSONObject getplayerdetail = new JSONObject(response);
                    Log.d("getplayerdetail", String.valueOf(getplayerdetail));
                    String value = getplayerdetail.optString("value");
                    if (value.equals("true")) {
                        JSONObject data = new JSONObject(getplayerdetail.optString("data"));
                        JSONObject player = new JSONObject(data.optString("player"));

                        careerObj =player.optString("career");
                        currentObj =player.optString("current");
                        lastseasonObj =player.optString("lastseason");

                        JSONObject career = new JSONObject(player.optString("career"));
                        JSONObject current = new JSONObject(player.optString("current"));
                        JSONObject lastseason = new JSONObject(player.optString("lastseason"));
                        careerstatus=career.optString("status");
                        currentstatus=current.optString("status");
                        lastseasonstatus=lastseason.optString("status");


                        playerName = player.optString("name");
                        playerType = player.optString("type");
                        playerNationality =player.optString("nationality");
                        playerJerseyNum =player.optString("jerseyno");
                        playerImg =player.optString("bigimage");
                        weight =player.optString("weight");
                        height =player.optString("height");
                        nativeplace =player.optString("nativeplace");
                        dob =player.optString("dob");
                        JSONArray playerTourDescArray =  new JSONArray(data.optString("tournamentplayed"));
                        for (int j = 0; j < playerTourDescArray.length(); j++) {
                            JSONObject tour = playerTourDescArray.getJSONObject(j);
                            Log.d("tour.name)", tour.optString("name"));
                            if (!tour.optString("name").equals("N/A") ){
                                if (playerTourDesc==null){
                                    playerTourDesc=   tour.optString("name") +"(" +tour.optString("year") +") ";
                                }else{
                                    playerTourDesc= playerTourDesc +  tour.optString("name") +"(" +tour.optString("year") +") ";
                                }
                            }
                        }
                        JSONArray playerachievmantDescArray =  new JSONArray(data.optString("achievmant"));
                        for (int j = 0; j < playerachievmantDescArray.length(); j++) {
                            JSONObject achievmant = playerachievmantDescArray.getJSONObject(j);
                            Log.d("tour.name)", achievmant.optString("name"));
                            if (!achievmant.optString("name").equals("N/A") ){
                                if (playerAchievmantDesc==null){
                                    playerAchievmantDesc=   achievmant.optString("name") +"(" +achievmant.optString("year") +") ";
                                }else{
                                    playerAchievmantDesc= playerAchievmantDesc +  achievmant.optString("name") +"(" +achievmant.optString("year") +") ";
                                }
                            }
                        }
                            done = true;

                    } else {
                        progressDialog.dismiss();
                    }

                } catch (IOException io) {
                    Log.e("JPP", Log.getStackTraceString(io));
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


//        String[] players = getResources().getStringArray(R.array.playersDesc);
//        String singlePlayer = players[id - 1];
//
//        //String player = getResources().getString(R.string.player1);
//        List<String> playerDetail = Arrays.asList(singlePlayer.split("#"));
//
//        String playerName = playerDetail.get(0);                //PlayerName
//        String playerType = playerDetail.get(1);                //PlayerType
//        String playerNationality = playerDetail.get(8);         //PlayerNationality
//        String playerDob = playerDetail.get(7);                 //PlayerDob
//        String playerJerseyNum = playerDetail.get(4);           //PlayerJerseyNum
//        //String playerDesc = playerDetail.get(5);              //PlayerDesc
//
//        String playerTourDesc = playerDetail.get(5);            //PlayerTour
//        String playerAchieveDesc = playerDetail.get(6);         //PlayerAchieve
//        // String playerAge = playerDetail.get(7);              //PlayerAge
//
//        TypedArray playerImages = getActivity().getResources().obtainTypedArray(R.array.playerImages);
//
//        ivPlayer.setImageResource(playerImages.getResourceId(id-1, 0));
//
//        tvName.setText(playerName);
//        tvType.setText(playerType);
//        tvNationality.setText(playerNationality);
//        tvDOB.setText(playerDob);
//        tvJerseyNo.setText(playerJerseyNum);
//        // tvDesc.setText(playerDesc);
//        tvTourDesc.setText(playerTourDesc);
//
//        if(playerTourDesc.startsWith("--")){
//            tvTourDesc.setVisibility(View.GONE);
//            tvTour.setVisibility(View.GONE);
//        }
//
//        tvAchieveDesc.setText(playerAchieveDesc);
//
//        // tvAge.setText(playerAge);
//
//        playerImages.recycle();
    }
    public String getDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "MMM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public void setcareer(String career) {
        try {
            JSONObject jsonObj = new JSONObject(career);
            tvplayed.setText(jsonObj.optString("matchplayed"));
            tvtotalpts.setText(jsonObj.optString("totalpoints"));
            tvtotalraidpts.setText(jsonObj.optString("totalraidpoints"));
            tvtotaldefensepts.setText(jsonObj.optString("totaldefencepoints"));
            tvgreencard.setText(jsonObj.optString("greencards"));
            tvredcard.setText(jsonObj.optString("redcards"));
            tvyellowcard.setText(jsonObj.optString("yellowcards"));
            tvraids.setText(jsonObj.optString("raids"));
            tvsuccessfulraids.setText(jsonObj.optString("successfulraids"));
            tvunsuccessfulraids.setText(jsonObj.optString("unsuccessfulraids"));
            tvemptyraids.setText(jsonObj.optString("emptyraids"));
            tvtackles.setText(jsonObj.optString("tackles"));
            tvsuccesstackles.setText(jsonObj.optString("successfultackles"));
            tvunsuccesstackles.setText(jsonObj.optString("unsuccessfultackles"));



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
        public void refresh() {
            if (careerstatus.equals("2") && currentstatus.equals("2") && lastseasonstatus.equals("2")){
               llstat.setVisibility(View.GONE);
            }
            if (careerstatus.equals("2")){
                btcareer.setVisibility(View.GONE);
            }
            if (currentstatus.equals("2")){
                btcurrent.setVisibility(View.GONE);
            }
            if (lastseasonstatus.equals("2")){
                btlastseason.setVisibility(View.GONE);
            }
            setcareer(currentObj);
            tvName.setText(playerName);
            tvType.setText(playerType);
            tvNationality.setText(playerNationality);
            tvNo.setText(playerJerseyNum);
            playerImg = InternetOperations.SERVER_UPLOADS_URL + playerImg;
            tvWeightval.setText(weight);
            tvheightval.setText(height);
            tvnativeval.setText(nativeplace);
            dob=getDate(dob);
            tvDOBval.setText(dob);

            imageLoader.displayImage(playerImg, ivPlayer, options);
            if (playerTourDesc==null && playerAchievmantDesc==null){
                Log.d("gone ", "refresh  gone");
                btTourDesc.setVisibility(View.GONE);
                btAchieveDesc.setVisibility(View.GONE);
            }

            if (playerTourDesc==null){
                tvTourDesc.setVisibility(View.GONE);
                tvTour.setVisibility(View.GONE);
                btTourDesc.setVisibility(View.INVISIBLE);
            }else{
                Log.d("playerTourDesc",playerTourDesc);
                tvTourDesc.setText(playerTourDesc);
            }

            if (playerAchievmantDesc==null){
                tvAchieveDesc.setVisibility(View.GONE);
                tvAchieve.setVisibility(View.GONE);
                btAchieveDesc.setVisibility(View.INVISIBLE);
            }else{
                Log.d("playerTourDesc",playerAchievmantDesc);
                tvAchieveDesc.setText(playerAchievmantDesc);
            }
    }

}
