package com.example.financial_savings.controllers.events;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.EventDisplayModule;
import com.example.financial_savings.modules.events.EventFinishModule;

import java.util.ArrayList;

public class TabFinishEventActivity extends AppCompatActivity implements IMappingView {
    private ListView listViewEvent;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_tab_finish);
        init();
        loadData();
    }

    private void loadData() {
        ArrayList<SuKien> list = EventFinishModule.getEventFinish(dbHelper);
        EventDisplayModule.showListViewHome_Event(list, getApplicationContext(), listViewEvent, dbHelper);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void init() {
        listViewEvent = findViewById(R.id.listView_event_tab_finish);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
