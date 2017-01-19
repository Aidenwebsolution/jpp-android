package com.jaipurpinkpanthers.android.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaipurpinkpanthers.android.R;
import com.jaipurpinkpanthers.android.util.CustomFonts;
import com.jaipurpinkpanthers.android.util.ImageCircle;

public class AboutUsFragment extends Fragment {

    View view;
    LinearLayout llTeam, llTeam1;
    ImageView ivIndiPic;
    TextView aboutMain, aboutBottom;
    TextView aboutFounded, aboutOwner, aboutCoach, aboutCaptain, aboutGround, aboutTeam, aboutSquad;
    TextView aboutFoundedYear, aboutOwnerName, aboutCoachName, aboutCaptainName, aboutGroundName, aboutTeamColor, aboutCurrentSquad;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about_us, container, false);

        initilizeViews();

        return view;
    }

    public void initilizeViews(){
        activity = getActivity();
        TextView tvAboutContent = (TextView) view.findViewById(R.id.tvAboutContent);
        tvAboutContent.setTypeface(CustomFonts.getLightFont(getActivity()));

        llTeam = (LinearLayout) view.findViewById(R.id.llTeam);
        TextView tvTeam = (TextView) llTeam.findViewById(R.id.tvCrossHeader);
        tvTeam.setTypeface(CustomFonts.getRegularFont(getActivity()));
        tvTeam.setText("TEAM");

        llTeam1 = (LinearLayout) view.findViewById(R.id.llTeam1);
        ivIndiPic = (ImageView) llTeam1.findViewById(R.id.ivIndiPic);

        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.avatar);

        ivIndiPic.setImageBitmap(ImageCircle.getRoundedCornerImage(ImageCircle.getSquareImage(bitmap)));

        aboutMain = (TextView) view.findViewById(R.id.tvAboutMain);
        aboutBottom = (TextView) view.findViewById(R.id.tvAboutBottom);

        aboutFounded = (TextView) view.findViewById(R.id.tvAboutFounded);
        aboutOwner = (TextView) view.findViewById(R.id.tvAboutOwner);
        aboutCoach = (TextView) view.findViewById(R.id.tvAboutCoach);
        aboutCaptain = (TextView) view.findViewById(R.id.tvAboutCaptain);
        aboutGround = (TextView) view.findViewById(R.id.tvAboutGround);
        aboutTeam = (TextView) view.findViewById(R.id.tvAboutTeam);
        aboutSquad = (TextView) view.findViewById(R.id.tvAboutSquad);

        aboutFoundedYear = (TextView) view.findViewById(R.id.tvAboutFoundedYear);
        aboutOwnerName = (TextView) view.findViewById(R.id.tvAboutOwnerName);
        aboutCoachName = (TextView) view.findViewById(R.id.tvAboutCoachName);
        aboutCaptainName = (TextView) view.findViewById(R.id.tvAboutCaptainName);
        aboutGroundName = (TextView) view.findViewById(R.id.tvAboutGroundName);
        aboutTeamColor = (TextView) view.findViewById(R.id.tvAboutTeamColors);
        aboutCurrentSquad = (TextView) view.findViewById(R.id.tvAboutCurrentSquad);

        aboutMain.setTypeface(CustomFonts.getLightFont(activity));
        aboutBottom.setTypeface(CustomFonts.getLightFont(activity));

        aboutFounded.setTypeface(CustomFonts.getRegularFont(activity));
        aboutOwner.setTypeface(CustomFonts.getRegularFont(activity));
        aboutCoach.setTypeface(CustomFonts.getRegularFont(activity));
        aboutCaptain.setTypeface(CustomFonts.getRegularFont(activity));
        aboutGround.setTypeface(CustomFonts.getRegularFont(activity));
        aboutTeam.setTypeface(CustomFonts.getRegularFont(activity));
        aboutSquad.setTypeface(CustomFonts.getRegularFont(activity));

        aboutFoundedYear.setTypeface(CustomFonts.getLightFont(activity));
        aboutOwnerName.setTypeface(CustomFonts.getLightFont(activity));
        aboutCoachName.setTypeface(CustomFonts.getLightFont(activity));
        aboutCaptainName.setTypeface(CustomFonts.getLightFont(activity));
        aboutGroundName.setTypeface(CustomFonts.getLightFont(activity));
        aboutTeamColor.setTypeface(CustomFonts.getLightFont(activity));
        aboutCurrentSquad.setTypeface(CustomFonts.getLightFont(activity));

    }


}
