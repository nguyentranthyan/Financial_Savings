package com.example.financial_savings.modules.events;

import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.helper.DBHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class EventApplyModule {

    public static ArrayList<SuKien> getEventApply(DBHelper dbHelper) {
        java.util.Date uNow = new java.util.Date(Calendar.getInstance().getTime().getTime());
        java.sql.Date sNow = new java.sql.Date(uNow.getTime());
        ArrayList<SuKien> list = new ArrayList<>();
        ArrayList<SuKien> all = dbHelper.getAll_SuKien();
        for (int i = 0; i < all.size(); i++) {
            Date date = all.get(i).getNgayKetThuc();
            if(sNow.compareTo(date) < 0) {
                list.add(all.get(i));
            }
        }
        return list;
    }
}
