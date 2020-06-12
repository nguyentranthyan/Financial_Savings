package com.example.financial_savings.modules.dates;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateGetStringModule {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getDateByMonth() {
        LocalDate now = LocalDate.now();
        LocalDate fd = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate ld = now.with(TemporalAdjusters.lastDayOfMonth());
        int month = now.getMonthValue();
        int first_day = fd.getDayOfMonth();
        int last_day = ld.getDayOfMonth();
        return first_day + "/" + month + " - " + last_day + "/" + month;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getDateByQuarter() {
        return getFirstLastDay(getQuarter(LocalDate.now().getMonthValue()));
    }

    private static String getFirstLastDay(int quarter) {
        if(quarter == 1) {
            return "1/1 - 31/3";
        }
        else if(quarter == 2) {
            return "1/4 - 30/6";
        }
        else if(quarter == 3) {
            return "1/7 - 30/9";
        }
        else {
            return "1/10 - 31/12";
        }
    }

    private static int getQuarter(int month) {
        if(month == 1 || month == 2 || month == 3)
            return 1;
        else if(month == 4 || month == 5 || month == 6)
            return 2;
        else if(month == 7 || month == 8 || month == 9)
            return 3;
        else
            return 4;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getDateByYear() {
        int year = LocalDate.now().getYear();
        return "1/1/" + year + " - " + "31/12/" + year;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getDayStartByNameTime(String nameTime) {
        final String MONTH = "Tháng này", QUARTER = "Quý này", YEAR = "Năm này";
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        switch (nameTime) {
            case MONTH:
                LocalDate fd = now.with(TemporalAdjusters.firstDayOfMonth());
                int first_day = fd.getDayOfMonth();
                return first_day + "/" + month + "/" + year;
            case QUARTER:
                int quater = getQuarter(month);
                switch (quater) {
                    case 1:
                        return "1/1/" + year;
                    case 2:
                        return "1/4/" + year;
                    case 3:
                        return "1/7/" + year;
                    default:
                        return "1/10/" + year;
                }
            default:
                return "1/1/" + year;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getDayEndByNameTime(String nameTime) {
        final String MONTH = "Tháng này", QUARTER = "Quý này", YEAR = "Năm này";
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        switch (nameTime) {
            case MONTH:
                LocalDate ld = now.with(TemporalAdjusters.lastDayOfMonth());
                int last_day = ld.getDayOfMonth();
                return last_day + "/" + month + "/" + year;
            case QUARTER:
                int quater = getQuarter(month);
                switch (quater) {
                    case 1:
                        return "31/3/" + year;
                    case 2:
                        return "30/6/" + year;
                    case 3:
                        return "30/9/" + year;
                    default:
                        return "31/12/" + year;
                }
            default:
                return "31/12/" + year;
        }
    }
}
