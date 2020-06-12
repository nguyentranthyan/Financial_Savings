package com.example.financial_savings.modules.dates;

public class DateFinishModule {
    private static final String DATE = "01/01/2100";

    public static String getDateFinish(String date) {
        if(date.equals(DATE)) {
            return "Chưa xác định";
        }
        return date;
    }
}
