package com.jaipurpinkpanthers.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Jay on 26-01-2016.
 */
public class TimeFunction {

    public static long stringToDate(String date) throws ParseException {
        return new SimpleDateFormat("dd MMM yyyy, kk:mm").parse(date).getTime();
    }
}
