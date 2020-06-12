package com.example.financial_savings.modules.formats;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatModule {
    private static final String NOW = "Hôm nay";

    public static Date getDate(String dateStr) {
        Date date = null;
        if(dateStr.equals(NOW))
            return new Date(Calendar.getInstance().getTime().getTime());
        try {
            @SuppressLint("SimpleDateFormat") Date initDate = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String parsedDate = dateFormat.format(initDate);
            date = dateFormat.parse(parsedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatDate(String dateStr) {
        Date date = null;
        try {
            @SuppressLint("SimpleDateFormat") Date initDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String parsedDate = dateFormat.format(initDate);
            date = dateFormat.parse(parsedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static java.sql.Date getDateSQL(String dateStr) {
        Date date = DateFormatModule.getDate(dateStr);
        return new java.sql.Date(date.getTime());
    }
}
