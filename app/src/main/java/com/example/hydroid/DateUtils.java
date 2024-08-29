package com.example.hydroid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String getReadableDateFromLong(long dateInMillis) {

        Date date = new Date(dateInMillis);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        return dateFormat.format(date);
    }
}