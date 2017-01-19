package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaipurpinkpanthers.android.MainActivity;
import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.CustomFonts;

import java.util.Arrays;
import java.util.List;

public class PlayerDescriptionFragment extends Fragment {
    private View view;
    private LinearLayout llPlayerDesc;
    private ImageView ivPlayer;
    private ImageView toolbar;

    private TextView tvName, tvType, tvNationality, tvDOB, tvJerseyNo, tvDesc, tvNat, tvBorn, tvJer, tvTour, tvTourDesc, tvAchieve, tvAchieveDesc;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_player_description, container, false);

        int id = ((MainActivity) this.getActivity()).getPlayerId();

        //((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        initilizeViews();
        getPlayerDetails(id);
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

        ivPlayer = (ImageView) llPlayerDesc.findViewById(R.id.ivPlayer);
        tvName = (TextView) llPlayerDesc.findViewById(R.id.tvName);
        tvType = (TextView) llPlayerDesc.findViewById(R.id.tvType);
        tvNationality = (TextView) llPlayerDesc.findViewById(R.id.tvNationality);
        tvDOB = (TextView) llPlayerDesc.findViewById(R.id.tvDOB);
        tvJerseyNo = (TextView) llPlayerDesc.findViewById(R.id.tvJerseyNo);
        //tvDesc = (TextView) llPlayerDesc.findViewById(R.id.tvDesc);
        tvTourDesc = (TextView) llPlayerDesc.findViewById(R.id.tvTourDesc);
        tvAchieveDesc = (TextView) llPlayerDesc.findViewById(R.id.tvAchieveDesc);

        tvNat = (TextView) llPlayerDesc.findViewById(R.id.tvNat);
        tvBorn = (TextView) llPlayerDesc.findViewById(R.id.tvBorn);
        tvJer = (TextView) llPlayerDesc.findViewById(R.id.tvJer);
        tvTour = (TextView) llPlayerDesc.findViewById(R.id.tvTour);
        tvAchieve = (TextView) llPlayerDesc.findViewById(R.id.tvAchieve);

        tvName.setTypeface(CustomFonts.getProfileFont(activity));
        tvType.setTypeface(CustomFonts.getProfileFont(activity));
        tvNationality.setTypeface(CustomFonts.getLightFont(activity));
        tvDOB.setTypeface(CustomFonts.getLightFont(activity));
        tvJerseyNo.setTypeface(CustomFonts.getLightFont(activity));
        //tvDesc.setTypeface(CustomFonts.getLightFont(activity));
        tvTourDesc.setTypeface(CustomFonts.getLightFont(activity));
        tvAchieveDesc.setTypeface(CustomFonts.getLightFont(activity));

        tvNat.setTypeface(CustomFonts.getRegularFont(activity));
        tvBorn.setTypeface(CustomFonts.getRegularFont(activity));
        tvJer.setTypeface(CustomFonts.getRegularFont(activity));
        tvTour.setTypeface(CustomFonts.getRegularFont(activity));
        tvAchieve.setTypeface(CustomFonts.getRegularFont(activity));
        toolbar= (ImageView) view.findViewById(R.id.toolbar_image);

    }

    public void getPlayerDetails(int id) {
        String[] players = getResources().getStringArray(R.array.playersDesc);
        String singlePlayer = players[id - 1];

        //String player = getResources().getString(R.string.player1);
        List<String> playerDetail = Arrays.asList(singlePlayer.split("#"));

        String playerName = playerDetail.get(0);                //PlayerName
        String playerType = playerDetail.get(1);                //PlayerType
        String playerNationality = playerDetail.get(8);         //PlayerNationality
        String playerDob = playerDetail.get(7);                 //PlayerDob
        String playerJerseyNum = playerDetail.get(4);           //PlayerJerseyNum
        //String playerDesc = playerDetail.get(5);              //PlayerDesc

        String playerTourDesc = playerDetail.get(5);            //PlayerTour
        String playerAchieveDesc = playerDetail.get(6);         //PlayerAchieve
        // String playerAge = playerDetail.get(7);              //PlayerAge

        TypedArray playerImages = getActivity().getResources().obtainTypedArray(R.array.playerImages);

        ivPlayer.setImageResource(playerImages.getResourceId(id-1, 0));

        tvName.setText(playerName);
        tvType.setText(playerType);
        tvNationality.setText(playerNationality);
        tvDOB.setText(playerDob);
        tvJerseyNo.setText(playerJerseyNum);
        // tvDesc.setText(playerDesc);
        tvTourDesc.setText(playerTourDesc);

        if(playerTourDesc.startsWith("--")){
            tvTourDesc.setVisibility(View.GONE);
            tvTour.setVisibility(View.GONE);
        }

        tvAchieveDesc.setText(playerAchieveDesc);

        // tvAge.setText(playerAge);

        playerImages.recycle();
    }

}
