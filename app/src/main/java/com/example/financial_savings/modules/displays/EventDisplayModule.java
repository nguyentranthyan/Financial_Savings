package com.example.financial_savings.modules.displays;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.chooses.ChooseEventActivity;
import com.example.financial_savings.controllers.events.DetailEventActivity;
import com.example.financial_savings.controllers.events.HomeEventActivity;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.events.MoneyEventModule;
import com.example.financial_savings.sessions.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventDisplayModule {
    private static Session session;

    public static void showListViewHome_Event(final ArrayList<SuKien> list, final Context context,
                                              ListView listView, DBHelper dbHelper) {
        List<HashMap<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            double total = MoneyEventModule.getMoneyEvent(dbHelper, list.get(i));
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("listView_name", list.get(i).getTenSuKien());
            hashMap.put("listView_money", FormatMoneyModule.formatAmount(total) + " VND");
            hashMap.put("image", String.valueOf(R.drawable.ic_event_available_black_24dp));
            mapList.add(hashMap);
        }

        String[] from = {"image", "listView_name", "listView_money"};
        int[] to = {R.id.imageView_Event, R.id.textView_Name_Event, R.id.textView_Money_Event};

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.event_list_item_layout, from, to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idEvent = list.get(position).getMaSuKien();
                Intent intent = new Intent(context.getApplicationContext(), DetailEventActivity.class);
                intent.putExtra("idEvent", idEvent);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.getApplicationContext().startActivity(intent);
                HomeEventActivity.activity.onBackPressed();
            }
        });
    }

    public static void showListViewChoose_Event(final ArrayList<SuKien> list, final Context context,
                                                ListView listView, DBHelper dbHelper) {
        session = new Session(context);
        List<HashMap<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            double total = MoneyEventModule.getMoneyEvent(dbHelper, list.get(i));
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("listView_name", list.get(i).getTenSuKien());
            hashMap.put("listView_money", FormatMoneyModule.formatAmount(total) + " VND");
            hashMap.put("image", String.valueOf(R.drawable.ic_event_available_black_24dp));
            mapList.add(hashMap);
        }

        String[] from = {"image", "listView_name", "listView_money"};
        int[] to = {R.id.imageView_Event, R.id.textView_Name_Event, R.id.textView_Money_Event};

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.event_list_item_layout, from, to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                session.clearEvent();
                String idEvent = list.get(position).getMaSuKien();
                session.setIDEvent(idEvent);
                ChooseEventActivity.activity.onBackPressed();
            }
        });
    }
}
