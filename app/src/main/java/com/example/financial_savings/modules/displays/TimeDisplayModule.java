package com.example.financial_savings.modules.displays;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.RequiresApi;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.budgets.ChooseTimeActivity;
import com.example.financial_savings.modules.dates.DateChooseCustomModule;
import com.example.financial_savings.modules.dates.DateGetStringModule;
import com.example.financial_savings.sessions.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimeDisplayModule {
    private static Session session;
    private static String[] arrTime = {"Tháng này", "Quý này", "Năm này", "Thời gian tùy chỉnh"};

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void showListView_Budget_time(final Context context, ListView listView) {
        session = new Session(context);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<HashMap<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < arrTime.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("listView_name", arrTime[i]);
            hashMap.put("listView_date", getIndexArray(i));
            hashMap.put("image", String.valueOf(R.drawable.ic_baseline_watch_later_24));
            mapList.add(hashMap);
        }

        String[] from = {"image", "listView_name", "listView_date"};
        int[] to = {R.id.imageView_Budget_time, R.id.textView_Name_Budget_time, R.id.textView_Date_Budget_time};

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.budget_time_list_layout, from, to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != 3) {
                    session.clearNameTime();
                    session.clearTimeStart();
                    session.clearTimeEnd();
                    session.setNameTime(arrTime[position]);
                    ChooseTimeActivity.activity.onBackPressed();
                }
                else {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            ChooseTimeActivity.activity.onBackPressed();
                        }
                    };
                    DateChooseCustomModule.handlingChooseCustom(context, runnable, true);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String getIndexArray(int index) {
        String result;
        switch (index) {
            case 0:
                result = DateGetStringModule.getDateByMonth();
                break;
            case 1:
                result = DateGetStringModule.getDateByQuarter();
                break;
            case 2:
                result = DateGetStringModule.getDateByYear();
                break;
            default:
                result = "----------";
                break;
        }
        return result;
    }
}
