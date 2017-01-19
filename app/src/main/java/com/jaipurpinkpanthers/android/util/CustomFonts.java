package com.jaipurpinkpanthers.android.util;

import android.app.Activity;
import android.graphics.Typeface;

/**
 * Created by Jay on 19-01-2016.
 */
public class CustomFonts {

    public static Typeface getRegularFont(Activity activity){
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Oswald-Regular.ttf");
    }

    public static Typeface getLightFont(Activity activity){
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Oswald-Light.ttf");
    }

    public static Typeface getBoldFont(Activity activity){
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Oswald-Bold.ttf");
    }

    public static Typeface getProfileFont(Activity activity){
        return Typeface.createFromAsset(activity.getAssets(), "fonts/voyagefantcond.ttf");
    }

    public static Typeface getScoreFont(Activity activity){
        return Typeface.createFromAsset(activity.getAssets(), "fonts/Kenyan-Coffee-Rg.ttf");
    }
}
