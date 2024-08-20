package com.example.hydroid;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {

    public static String getReadableDateFromLong(long dateInMillis) {

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT);

        return dateFormat.format(new Date(dateInMillis * 1000));
    }
}