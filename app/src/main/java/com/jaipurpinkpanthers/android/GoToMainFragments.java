package com.jaipurpinkpanthers.android;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Jay on 21-01-2016.
 */
public class GoToMainFragments {

    public static void goHome(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("FragmentId", 0);
        context.startActivity(intent);
    }

    public static void goSchedule(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("FragmentId", 1);
        context.startActivity(intent);
    }
//    public static void gomatchupdate(Context context){
//        Intent intent = new Intent(context, MainActivity.class);
//        //intent.putExtra("FragmentId", 4);
//        intent.putExtra("FragmentId", 2);
//        context.startActivity(intent);
//    }
    public static void gomatchupdate1(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        //intent.putExtra("FragmentId", 4);
        intent.putExtra("FragmentId", 2);
        context.startActivity(intent);
    }

    public static void goGallery(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        //intent.putExtra("FragmentId", 2);
        intent.putExtra("FragmentId", 3);
        context.startActivity(intent);
    }
    public static void goJppTv(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        //intent.putExtra("FragmentId", 2);
        intent.putExtra("FragmentId", 4);
        context.startActivity(intent);
    }

    public static void goNews(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        //intent.putExtra("FragmentId", 3);
        intent.putExtra("FragmentId", 5);
        context.startActivity(intent);
    }

    public static void goPanthers(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        //intent.putExtra("FragmentId", 4);
        intent.putExtra("FragmentId", 6);
        context.startActivity(intent);
    }




}
