package com.jaipurpinkpanthers.android.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mahesh on 7/4/2016.
 */
public class MatchUpdateAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    String logo_url = "http://jaipurpinkpanthers.com/img/team/";

    public MatchUpdateAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisc(true).resetViewBeforeLoading(true).build();
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false).showImageOnLoading(R.drawable.loadingnews)
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
        // END - UNIVERSAL IMAGE LOADER SETUP
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        //all the fields in layout specified
        ImageView ivT1matchupdate,ivT2matchupdate;
        TextView tvmatchupdateVenue, lltvMatchTime, halftime_fulltime,tvupdatescore1,tvupdatescore2;
        LinearLayout llMatchUpdate;
        FrameLayout llmatchUpdateScore;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_matchupdate, null); //change the name of the layout

            holder.ivT1matchupdate = (ImageView) convertView.findViewById(R.id.ivT1matchupdate);
            holder.ivT2matchupdate = (ImageView) convertView.findViewById(R.id.ivT2matchupdate);//find the different Views
            holder.tvmatchupdateVenue = (TextView) convertView.findViewById(R.id.tvmatchupdateVenue);
            holder.lltvMatchTime = (TextView) convertView.findViewById(R.id.lltvMatchTime);
            holder.tvupdatescore1 = (TextView) convertView.findViewById(R.id.tvupdatescore1);
            holder.tvupdatescore2 = (TextView) convertView.findViewById(R.id.tvupdatescore2);

            holder.halftime_fulltime = (TextView) convertView.findViewById(R.id.halftime_fulltime);
//            holder.llMatchUpdate = (LinearLayoutLayout) convertView.findViewById(R.id.llMatchUpdate);
            holder.llmatchUpdateScore = (FrameLayout) convertView.findViewById(R.id.llmatchUpdateScore);



            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        HashMap<String, String> map = list.get(position);

        String team1 = map.get("team1");
        String team2 = map.get("team2");
        String team1Pts = map.get("score1");
        String team2Pts = map.get("score2");
        String venue = map.get("stadium");
        String time = map.get("starttimedate");
        String team1Id = map.get("team1id");
        String team2Id = map.get("team2id");
        String matchTime = map.get("matchtime");
        String header=map.get("header");


        holder.tvmatchupdateVenue.setText(venue);
        holder.tvmatchupdateVenue.setTypeface(CustomFonts.getRegularFont(activity));
        holder.lltvMatchTime.setText(time);
        holder.lltvMatchTime.setTypeface(CustomFonts.getRegularFont(activity));
        scoreColour(team1,1,holder);

        holder.tvupdatescore1.setText(team1Pts);
        holder.tvupdatescore1.setTypeface(CustomFonts.getScoreFont(activity));
        scoreColour(team2,2,holder);
        holder.tvupdatescore2.setText(team2Pts);
        holder.tvupdatescore2.setTypeface(CustomFonts.getScoreFont(activity));
        holder.halftime_fulltime.setText(matchTime);
        holder.halftime_fulltime.setTypeface(CustomFonts.getRegularFont(activity));



        TextView tvLatest = (TextView)holder.llMatchUpdate.findViewById(R.id.tvCrossHeader);
        tvLatest.setTypeface(CustomFonts.getRegularFont(activity));
        tvLatest.setText("MATCH - 0"+header);


        //if(!desc.startsWith("http")){
        //    holder.tvNewsDesc.setText(desc);
        //}
        if (team1Id != null && team2Id != null) {
            String imageUriTeam1 = logo_url+team1+".png" ;
            String imageUriTeam2 = logo_url+team2+".png" ;
            imageLoader.displayImage(imageUriTeam1,holder.ivT1matchupdate , options);
            imageLoader.displayImage(imageUriTeam2,holder.ivT2matchupdate , options);
        }

//        String tag = title+"#"+image+"#"+date+"#"+desc;
//        holder.llNewsClick.setTag(tag);
//
//        imageLoader.displayImage(image, holder.ivNewsImage, options);

        return convertView;
    }
    public void scoreColour(String team,int teamNo,ViewHolder holder)
    {
        Log.d("hi ","color ");
        if(teamNo==1){
            if(team.equals("Patna Pirates"))
            {
                holder.tvupdatescore1.setTextColor(Color.parseColor("#0a4436"));
            }
            else if(team.equals("Bengaluru Bulls"))
            {
                holder.tvupdatescore1.setTextColor(Color.parseColor("#b01d21"));
            }
            else if(team.equals("Bengal Warriors"))
            {
                holder.tvupdatescore1.setTextColor(Color.parseColor("#f26724"));
            }
            else if(team.equals("Dabang Delhi"))
            {
                holder.tvupdatescore1.setTextColor(Color.parseColor("#d91f2d"));
            }
            else if(team.equals("Jaipur Pink Panthers"))
            {
                holder.tvupdatescore1.setTextColor(Color.parseColor("#ee4a9b"));
                Log.d("hi pink panthers","color ");
            }
            else if(team.equals("Puneri Paltan"))
            {
                holder.tvupdatescore1.setTextColor(Color.parseColor("#f04e23"));
            }
            else if(team.equals("Telugu Titans"))
            {
                holder.tvupdatescore1.setTextColor(Color.parseColor("#da2131"));
            }
            else if(team.equals("U Mumba"))
            {
                holder.tvupdatescore1.setTextColor(Color.parseColor("#f15922"));
                Log.d("hi u mumba","color ");
            }}

        else{
            if(team.equals("Patna Pirates"))
            {
                holder.tvupdatescore2.setTextColor(Color.parseColor("#0a4436"));
            }
            else if(team.equals("Bengaluru Bulls"))
            {
                holder.tvupdatescore2.setTextColor(Color.parseColor("#b01d21"));
            }
            else if(team.equals("Bengal Warriors"))
            {
                holder.tvupdatescore2.setTextColor(Color.parseColor("#f26724"));
            }
            else if(team.equals("Dabang Delhi"))
            {
                holder.tvupdatescore2.setTextColor(Color.parseColor("#d91f2d"));
            }
            else if(team.equals("Jaipur Pink Panthers"))
            {
                holder.tvupdatescore2.setTextColor(Color.parseColor("#ee4a9b"));
                Log.d("hi pink panthers","color ");
            }
            else if(team.equals("Puneri Paltan"))
            {
                holder.tvupdatescore2.setTextColor(Color.parseColor("#f04e23"));
            }
            else if(team.equals("Telugu Titans"))
            {
                holder.tvupdatescore2.setTextColor(Color.parseColor("#da2131"));
            }
            else if(team.equals("U Mumba"))
            {
                holder.tvupdatescore2.setTextColor(Color.parseColor("#f15922"));
                Log.d("hi u mumba","color ");
            }
        }
    }
/*    public int getTeamDrawable(String id) {

        int teamId = Integer.parseInt(id);
        TypedArray teamLogos = activity.getResources().obtainTypedArray(R.array.teamLogo);

        return teamLogos.getResourceId(teamId - 1, -1);
    }*/
    private static final int[] teamUrl = new int[] {
            R.drawable.t1,
            R.drawable.t2,
            R.drawable.t3,
            R.drawable.t4,
            R.drawable.t5,
            R.drawable.t6,
            R.drawable.t7,
            R.drawable.t8
    };
}
