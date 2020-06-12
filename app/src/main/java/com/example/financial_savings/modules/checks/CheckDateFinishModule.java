package com.example.financial_savings.modules.checks;

import java.sql.Date;
import java.util.Calendar;

public class CheckDateFinishModule {

    public static boolean isFinish(Date date) {
        java.util.Date uNow = new java.util.Date(Calendar.getInstance().getTime().getTime());
        java.sql.Date sNow = new java.sql.Date(uNow.getTime());
        return sNow.compareTo(date) >= 0;
    }
}
