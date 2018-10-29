package com.jaipurpinkpanthers.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.InternetOperations;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;


public class MatchRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<MatchUpdateGetter> arrayList=new ArrayList<MatchUpdateGetter>();
    Context ctx;
    private static final int ANIMATED_ITEMS_COUNT = 2;

    //private Context context;
    private int lastAnimatedPosition = -1;
    private int itemsCount = 0;
    private int time = 600;
    ImageLoader imageLoader;
    DisplayImageOptions options;
    Activity activity;




    public MatchRecycleAdapter(ArrayList<MatchUpdateGetter> arrayList, Context ctx){
        this.arrayList=arrayList;
        this.ctx=ctx;
        activity = (Activity) ctx;
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder().cacheInMemory(false)
                .cacheOnDisc(false).resetViewBeforeLoading(true).build();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                activity)
                .defaultDisplayImageOptions(defaultOptions)
                .discCacheSize(1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_matchupdate, parent, false);

        return new MatchViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        MatchViewHolder vh=(MatchViewHolder) holder;
        MatchUpdateGetter current=arrayList.get(position);
        //runEnterAnimation(vh.itemView, position);

        vh.textView.setText(current.getScore1());
        vh.score2.setText(current.getScore2());
        vh.time.setText(current.getStarttimedate());
        vh.venue.setText(current.getStadium());
        vh.halftime.setText(current.getMatchtime());
        Log.d("current.getCount()", current.getCount());
        if (Integer.parseInt(current.getCount())>= 10){
            Log.d("current.getCount() yes", current.getCount());

            vh.matchnumbertv.setText("MATCH - "+current.getCount());
        }else{
            Log.d("current.getCount() no", current.getCount());

            vh.matchnumbertv.setText("MATCH - 0"+current.getCount());

        }


        /*vh.textView.setTypeface(CustomFonts.getScoreFont(ctx));
        vh.score2.setTypeface(CustomFonts.getScoreFont(ctx));
        vh.matchnumbertv.setTypeface(CustomFonts.getRegularFont(ctx));
        vh.halftime.setTypeface(CustomFonts.getRegularFont(ctx));
        vh.venue.setTypeface(CustomFonts.getRegularFont(ctx));
        vh.time.setTypeface(CustomFonts.getRegularFont(ctx));*/




        String jpp="http://jaipurpinkpanthers.com/img/team/4.png";
        String patnap="http://jaipurpinkpanthers.com/img/team/5.png";
        String teluguttitans="http://jaipurpinkpanthers.com/img/team/7.png";
        String delhi="http://jaipurpinkpanthers.com/img/team/3.png";
        String bbulls="http://jaipurpinkpanthers.com/img/team/1.png";
        String bwarriors="http://jaipurpinkpanthers.com/img/team/2.png";
        String puneripaltan="http://jaipurpinkpanthers.com/img/team/6.png";
        String umumba="http://jaipurpinkpanthers.com/img/team/8.png";

//
//        switch (position){
//            case 0:vh.matchnumbertv.setText("MATCH - 16");
//                break;
//            case 1:vh.matchnumbertv.setText("MATCH - 15");
//                break;
//            case 2:vh.matchnumbertv.setText("MATCH - 14");
//                break;
//            case 3:vh.matchnumbertv.setText("MATCH - 13");
//                break;
//            case 4:vh.matchnumbertv.setText("MATCH - 12");
//                break;
//            case 5:vh.matchnumbertv.setText("MATCH - 11");
//                break;
//            case 6:vh.matchnumbertv.setText("MATCH - 10");
//                break;
//            case 7:vh.matchnumbertv.setText("MATCH - 9");
//                break;
//            case 8:vh.matchnumbertv.setText("MATCH - 8");
//                break;
//            case 9:vh.matchnumbertv.setText("MATCH - 7");
//                break;
//            case 10:vh.matchnumbertv.setText("MATCH - 6");
//                break;
//            case 11:vh.matchnumbertv.setText("MATCH - 5");
//                break;
//            case 12:vh.matchnumbertv.setText("MATCH - 4");
//                break;
//            case 13:vh.matchnumbertv.setText("MATCH - 3");
//                break;
//            case 14:vh.matchnumbertv.setText("MATCH - 2");
//                break;
//            case 15:vh.matchnumbertv.setText("MATCH - 1");
//                break;
//        }

        String team1_link ="http://admin.jaipurpinkpanthers.com/uploads/"+current.getTeamimage1() ;
        String team2_link ="http://admin.jaipurpinkpanthers.com/uploads/"+current.getTeamimage2() ;
        Log.d("current.getTeam1()",current.getTeamimage2()+current.getTeamimage1());

//        Glide.with(ctx)
//                .load(team1_link)
//                .asBitmap()
//                .override(100, 100)
//                .centerCrop()
//                .into(vh.imageView1);
//        Glide.with(ctx)
//                .load(team2_link)
//                .asBitmap()
//                .override(100, 100)
//                .centerCrop()
//                .into(vh.imageView2);

        imageLoader.displayImage(team1_link, vh.imageView1, options);
        imageLoader.displayImage(team2_link, vh.imageView2, options);

        if (current.getTeam2().equals("Puneri Paltan")){

            vh.score2.setTextColor(Color.parseColor("#fff04e23"));
        }
        if (current.getTeam1().equals("Puneri Paltan")){

            vh.textView.setTextColor(Color.parseColor("#fff04e23"));
        }
        if (current.getTeam2().equals("Jaipur Pink Panthers")){

            vh.score2.setTextColor(Color.parseColor("#ffee4a9b"));
        }
        if (current.getTeam1().equals("Jaipur Pink Panthers")){

            vh.textView.setTextColor(Color.parseColor("#ffee4a9b"));
        }
        if (current.getTeam2().equals("Bengal Warriors")){

            vh.score2.setTextColor(Color.parseColor("#fff26724"));
        }
        if (current.getTeam1().equals("Bengal Warriors")){

            vh.textView.setTextColor(Color.parseColor("#fff26724"));
        }

        if (current.getTeam2().equals("Dabang Delhi")){

            vh.score2.setTextColor(Color.parseColor("#ffd91f2d"));
        }
        if (current.getTeam1().equals("Dabang Delhi")){

            vh.textView.setTextColor(Color.parseColor("#ffd91f2d"));
        }

        if (current.getTeam2().equals("Patna Pirates")){

            vh.score2.setTextColor(Color.parseColor("#ff0a4436"));
        }
        if (current.getTeam1().equals("Patna Pirates")){

            vh.textView.setTextColor(Color.parseColor("#ff0a4436"));
        }

        if (current.getTeam2().equals("Telugu Titans")){

            vh.score2.setTextColor(Color.parseColor("#ffda2131"));
        }
        if (current.getTeam1().equals("Telugu Titans")){

            vh.textView.setTextColor(Color.parseColor("#ffda2131"));
        }

        if (current.getTeam2().equals("U Mumba")){

            vh.score2.setTextColor(Color.parseColor("#fff15922"));
        }
        if (current.getTeam1().equals("U Mumba")){

            vh.textView.setTextColor(Color.parseColor("#fff15922"));
        }

        if (current.getTeam2().equals("Bengaluru Bulls")){

            vh.score2.setTextColor(Color.parseColor("#ffb01d21"));
        }
        if (current.getTeam1().equals("Bengaluru Bulls")){

            vh.textView.setTextColor(Color.parseColor("#ffb01d21"));
        }





        Glide.with(ctx)
                .load("http://jaipurpinkpanthers.com/extraimages/schedule_back.jpg")
                .asBitmap()
                .override(100, 100)
                .centerCrop()
                .into(vh.imageView3);
        Glide.with(ctx)
                .load("http://jaipurpinkpanthers.com/extraimages/jppvs.png")
                .asBitmap()
                .override(100, 100)
                .centerCrop()
                .into(vh.vs);
        Glide.with(ctx)
                .load("http://jaipurpinkpanthers.com/extraimages/headerstyle.png")
                .asBitmap()
                .into(vh.matchnumberiv);
        //http://jaipurpinkpanthers.com/img/object/vs.png
        /*Glide.with(ctx)
                .load(patnap)
                .asBitmap()
                .override(400, 400)
                .centerCrop()
                .into(vh.imageView1);
        Glide.with(ctx)
                .load(umumba)
                .asBitmap()
                .override(400,400)
                .centerCrop()
                .into(vh.imageView2);*/

        /*setScaleAnimation(vh.imageView2);
        setScaleAnimation(vh.imageView3);
        setScaleAnimation(vh.imageView1);
        setScaleAnimation(vh.textView);
        setScaleAnimation(vh.score2);*/

        //setScaleAnimation(vh.itemView);
//        runEnterAnimation(vh.itemView,position);




    }


    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(300);
        view.startAnimation(anim);

        /*AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(300);
        view.startAnimation(anim);*/
    }

    private void runEnterAnimation(View view, int position) {


        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            DisplayMetrics metrics = new DisplayMetrics();
            //ctx.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int height = metrics.heightPixels;
            int width = metrics.widthPixels;
            Log.e("runEnterAnimation: ",position+"" );

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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView venue;
        TextView time;
        TextView matchnumbertv;
        TextView score2;
        TextView textView;
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
        ImageView matchnumberiv;
        ImageView vs;
        TextView halftime;

        public MatchViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tvupdatescore1);
            matchnumbertv = (TextView) itemView.findViewById(R.id.matchup_tv_pinkhead);
            halftime = (TextView) itemView.findViewById(R.id.halftime_fulltime);
            score2 = (TextView) itemView.findViewById(R.id.tvupdatescore2);
            venue = (TextView) itemView.findViewById(R.id.tvmatchupdateVenue);
            time = (TextView) itemView.findViewById(R.id.lltvMatchTime);
            imageView1=(ImageView)itemView.findViewById(R.id.ivT1matchupdate);
            imageView2=(ImageView)itemView.findViewById(R.id.ivT2matchupdate);
            imageView3=(ImageView)itemView.findViewById(R.id.ivHomeMainlive);
            matchnumberiv=(ImageView)itemView.findViewById(R.id.matchup_iv_pinkhead);
            vs=(ImageView)itemView.findViewById(R.id.imageView7live);
            //imageView3=(ImageView)itemView.findViewById(R.id.ivHomeMainlive);

            Typeface regular = Typeface.createFromAsset(ctx.getAssets(),
                    "fonts/Oswald-Regular.ttf");
            Typeface score = Typeface.createFromAsset(ctx.getAssets(),
                    "fonts/Kenyan-Coffee-Rg.ttf");
            time.setTypeface(regular);
            matchnumbertv.setTypeface(regular);
            halftime.setTypeface(regular);
            venue.setTypeface(regular);
            score2.setTypeface(score);
            textView.setTypeface(score);


        }
    }
}
