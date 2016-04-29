package com.jackal.cowbeapp.utility;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jackalkao on 2/28/16.
 */
public class Utility {

    public static final String TAG = "JACKAL";



    public static void logStatus(String Message) {
        Log.d(Utility.TAG, Message);
    }

    public static Date FBStringToDate(String dtStart) {
        //2016-02-27T04:15:00+0000
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        try {
            Date date = format.parse(dtStart);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String FBDateToString(Date date) {
        int period = date.getHours();
        SimpleDateFormat dateFormat;
        if (period >= 12) {
            dateFormat = new SimpleDateFormat("MM月dd日下午HH:mm");
        } else
            dateFormat = new SimpleDateFormat("MM月dd日上午HH:mm");

        try {
            String datetime = dateFormat.format(date);
            return datetime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
