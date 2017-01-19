package com.jaipurpinkpanthers.android.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Jay on 26-01-2016.
 */
public class CalendarEvent {
    private String title;
    private String descr;
    private String location;
    private long startTime;
    private long endTime;
    private String idCalendar;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getIdCalendar() {
        return idCalendar;
    }

    public void setIdCalendar(String idCalendar) {
        this.idCalendar = idCalendar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static ContentValues toICSContentValues(CalendarEvent evt) {

        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.CALENDAR_ID, evt.getIdCalendar());
        cv.put(CalendarContract.Events.TITLE, evt.getTitle());
        cv.put(CalendarContract.Events.DESCRIPTION, evt.getDescr());
        //cv.put(CalendarContract.Events.EVENT_LOCATION, evt.getLocation());
        cv.put(CalendarContract.Events.DTSTART, evt.getStartTime());
        cv.put(CalendarContract.Events.DTEND, evt.getEndTime());
        cv.put(CalendarContract.Events.HAS_ALARM, true);
        cv.put(CalendarContract.Events.EVENT_COLOR, 15616667);

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        cv.put(CalendarContract.Events.EVENT_TIMEZONE, tz.getDisplayName());
        /*cv.put(Events.STATUS, 1);
        cv.put(Events.VISIBLE, 0);
        cv.put("transparency", 0);*/

        return cv;
    }

    public static ContentValues setReminder(Long eventID){
        ContentValues reminders = new ContentValues();
        reminders.put(CalendarContract.Reminders.EVENT_ID, eventID);
        reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        reminders.put(CalendarContract.Reminders.MINUTES, 0);

        return  reminders;
    }

    public static void setReminder(Context context, long startTime, String title) {

        CalendarEvent evt = new CalendarEvent();
        //evt.setDescr("this is desc");
        evt.setTitle(title);
        //evt.setLocation("Mumbai");
        evt.setStartTime(startTime - 1800000);
        evt.setEndTime(startTime + 3600000);
        evt.setIdCalendar("1");

        ContentResolver cr = context.getContentResolver();
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, toICSContentValues(evt));

        long eventID = Long.parseLong(uri.getLastPathSegment());

        ContentResolver crReminder = context.getContentResolver();
        Uri uriReminder = crReminder.insert(CalendarContract.Reminders.CONTENT_URI, setReminder(eventID));
        //System.out.println("Event URI ["+uri+"]");

        Toast.makeText(context,"Added to Calendar!",Toast.LENGTH_SHORT).show();
    }

    public static void remind(Context context, String tag){
        List<String> playerInfoList = Arrays.asList(tag.split("#"));

        String teamA = playerInfoList.get(0);
        String teamB = playerInfoList.get(1);
        String time = playerInfoList.get(2);
        String title = teamA + " Vs " + teamB;
        long timeLong;
        try{
            timeLong = TimeFunction.stringToDate(time);
            setReminder(context, timeLong, title);
        }catch (ParseException pe){
        }
    }
}
