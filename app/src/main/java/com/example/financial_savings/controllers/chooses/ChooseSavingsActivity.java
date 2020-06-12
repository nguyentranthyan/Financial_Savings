package com.example.financial_savings.controllers.chooses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.SavingsDisplayModule;
import com.example.financial_savings.modules.savings.SavingsApplyModule;

import java.util.ArrayList;

public class ChooseSavingsActivity extends AppCompatActivity implements IMappingView {
    private ListView listView;
    private ImageButton buttonReturn;
    private DBHelper dbHelper;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savings_home_choose);
        activity = this;
        init();
        loadData();
        eventReturn();
    }

    private void eventReturn() {
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadData() {
        ArrayList<TietKiem> list = SavingsApplyModule.getSavingsApply(dbHelper);
        SavingsDisplayModule.showListViewChoose_Savings(list, getApplicationContext(), listView, dbHelper);
    }

    @Override
    public void init() {
        listView = findViewById(R.id.listView_savings_home_choose);
        buttonReturn = findViewById(R.id.buttonReturn_savings_home_choose);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
