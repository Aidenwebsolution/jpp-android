package com.jaipurpinkpanthers.android.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.CustomFonts;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jay on 25-01-2016.
 */
public class PointsAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    int lastAnimatedPosition=-1;

    public PointsAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
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
        TextView tvNo, tvTeam, tvP, tvW, tvL, tvPts;
        LinearLayout llFull;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_points_row, null); //change the name of the layout

            holder.tvNo = (TextView) convertView.findViewById(R.id.tvNo); //find the different Views
            holder.tvTeam = (TextView) convertView.findViewById(R.id.tvTeam);
            holder.tvP = (TextView) convertView.findViewById(R.id.tvP);
            holder.tvW = (TextView) convertView.findViewById(R.id.tvW);
            holder.tvL = (TextView) convertView.findViewById(R.id.tvL);
            holder.tvPts = (TextView) convertView.findViewById(R.id.tvPts);

            holder.llFull = (LinearLayout) convertView.findViewById(R.id.llFull);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        HashMap<String, String> map = list.get(position);

        String no = map.get("tvNo");
        String team = map.get("tvTeam");
        String p = map.get("tvP");
        String w = map.get("tvW");
        String l = map.get("tvL");
        String pts = map.get("tvPts");

        holder.tvNo.setText(no);


        if(team.equals("Jaipur Pink Panthers")){
            /*holder.tvNo.setTypeface(CustomFonts.getRegularFont(activity));
            holder.tvTeam.setTypeface(CustomFonts.getRegularFont(activity));
            holder.tvP.setTypeface(CustomFonts.getRegularFont(activity));
            holder.tvW.setTypeface(CustomFonts.getRegularFont(activity));
            holder.tvL.setTypeface(CustomFonts.getRegularFont(activity));
            holder.tvPts.setTypeface(CustomFonts.getRegularFont(activity));*/
            holder.tvNo.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvNo.setTextColor(Color.parseColor("#ee4a9b"));
            holder.tvTeam.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvTeam.setTextColor(Color.parseColor("#ee4a9b"));
            holder.tvP.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvP.setTextColor(Color.parseColor("#ee4a9b"));
            holder.tvW.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvW.setTextColor(Color.parseColor("#ee4a9b"));
            holder.tvL.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvL.setTextColor(Color.parseColor("#ee4a9b"));
            holder.tvPts.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvPts.setTextColor(Color.parseColor("#ee4a9b"));
            holder.llFull.setBackgroundColor(Color.parseColor("#7bd9fa"));
            /*holder.tvNo.setTextColor(Color.parseColor("#4ECAF5"));
            holder.tvTeam.setTextColor(Color.parseColor("#4ECAF5"));
            holder.tvP.setTextColor(Color.parseColor("#4ECAF5"));
            holder.tvW.setTextColor(Color.parseColor("#4ECAF5"));
            holder.tvL.setTextColor(Color.parseColor("#4ECAF5"));
            holder.tvPts.setTextColor(Color.parseColor("#4ECAF5"));*/
        } else {
            holder.tvNo.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvNo.setTextColor(Color.parseColor("black"));
            holder.tvTeam.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvTeam.setTextColor(Color.parseColor("black"));
            holder.tvP.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvP.setTextColor(Color.parseColor("black"));
            holder.tvW.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvW.setTextColor(Color.parseColor("black"));
            holder.tvL.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvL.setTextColor(Color.parseColor("black"));
            holder.tvPts.setTypeface(CustomFonts.getLightFont(activity));
            holder.tvPts.setTextColor(Color.parseColor("black"));
            holder.llFull.setBackgroundColor(Color.parseColor("#7bd9fa"));

        }

        holder.tvTeam.setText(team);
        holder.tvP.setText(p);
        holder.tvW.setText(w);
        holder.tvL.setText(l);
        holder.tvPts.setText(pts);

        runEnterAnimation(convertView,position);

        return convertView;
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