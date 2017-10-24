

package com.jaipurpinkpanthers.android.adapters;

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

import java.util.ArrayList;


    public class MarchandiseAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<MatchUpdateGetter> arrayList=new ArrayList<MatchUpdateGetter>();
        Context ctx;
        private static final int ANIMATED_ITEMS_COUNT = 2;

        //private Context context;
        private int lastAnimatedPosition = -1;
        private int itemsCount = 0;
        private int time = 600;



        public MarchandiseAdaptor(ArrayList<MatchUpdateGetter> arrayList, Context ctx){
            this.arrayList=arrayList;
            this.ctx=ctx;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_layout_marchandise, parent, false);

            return new com.jaipurpinkpanthers.android.adapters.MarchandiseAdaptor.MarchandiseHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
            com.jaipurpinkpanthers.android.adapters.MatchRecycleAdapter.MatchViewHolder vh=(com.jaipurpinkpanthers.android.adapters.MatchRecycleAdapter.MatchViewHolder) holder;
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

            runEnterAnimation(vh.itemView,position);



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

        public class MarchandiseHolder extends RecyclerView.ViewHolder {
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

            public MarchandiseHolder(View itemView) {
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
