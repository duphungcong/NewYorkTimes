package com.duphungcong.newyorktimes.ulti;

import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by udcun on 2/26/2017.
 */

public class DateUlti {

    public static void setDatePickerValue(Date date, DatePicker dp) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        dp.init(year, month, dayOfMonth, null);
    }

    public static Date getDatePickerValue(DatePicker dp) {
        Calendar c = Calendar.getInstance();
        int year = dp.getYear();
        int month = dp.getMonth();
        int dayOfMonth = dp.getDayOfMonth();
        c.set(year, month, dayOfMonth);

        return c.getTime();
    }

    public static String dateFormat(Date date) {
        if (date != null) {
            String dateString = "";
            String pattern = "yyyyMMdd";

            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateString = dateFormat.format(date);

            return dateString;
        } else {
            return null;
        }
    }
}
