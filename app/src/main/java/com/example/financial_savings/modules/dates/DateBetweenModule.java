package com.example.financial_savings.modules.dates;

import java.util.Date;

public class DateBetweenModule {
    public static int daysBetween(Date d1, Date d2){
        return (int)( (d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24) + 1);
    }
}
