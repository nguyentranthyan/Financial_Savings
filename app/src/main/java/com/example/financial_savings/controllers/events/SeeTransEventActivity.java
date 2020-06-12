package com.example.financial_savings.controllers.events;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.TransactionDisplayModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.events.MoneyEventModule;

import java.util.ArrayList;

public class SeeTransEventActivity extends AppCompatActivity implements IMappingView {
    private ImageButton buttonReturn;
    private TextView textViewMoney;
    private ListView listView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_see_trans);
        init();
        loadData();
        eventReturn();
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        String idEvent = getIntent().getExtras().getString("idEvent");
        ArrayList<SoGiaoDich> list = dbHelper.getByEvent_SoGiaoDich(idEvent);
        SuKien suKien = dbHelper.getByID_SuKien(idEvent);
        double total = MoneyEventModule.getMoneyEvent(dbHelper, suKien);
        textViewMoney.setText(FormatMoneyModule.formatAmount(total) + " VND");
        TransactionDisplayModule.showListViewHome_Transaction(list, getApplicationContext(), listView, dbHelper);
    }

    private void eventReturn() {
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    @Override
    public void init() {
        buttonReturn = findViewById(R.id.buttonReturn_event_see_trans);
        textViewMoney = findViewById(R.id.textViewMoney_event_see_trans);
        listView = findViewById(R.id.listView_event_see_trans);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
