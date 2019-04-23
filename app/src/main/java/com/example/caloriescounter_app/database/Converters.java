package com.example.caloriescounter_app.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
    /*static DateFormat df = new SimpleDateFormat(Constants.TIME_STAMP_FORMAT);
    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }*/
    public static String getCurrentDate() {
        String date = "";
        Calendar calendarSmecher = Calendar.getInstance();
        Integer an = calendarSmecher.get(Calendar.YEAR);
        Integer luna = calendarSmecher.get(Calendar.MONTH) + 1;
        Integer zi = calendarSmecher.get(Calendar.DAY_OF_MONTH);
        date = date + zi.toString() + "-" + luna.toString() + "-" + an.toString();
        return date;
    }
}
