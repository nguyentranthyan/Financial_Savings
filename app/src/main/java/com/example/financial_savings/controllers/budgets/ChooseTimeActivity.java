package com.example.financial_savings.controllers.budgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.TimeDisplayModule;

public class ChooseTimeActivity extends AppCompatActivity implements IMappingView {
    private ListView listView;
    private ImageButton buttonCancel;
    private EditText editTextDateStart, editTextDateEnd;
    private Button buttonChooseTime;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budgets_choose_time);
        activity = this;
        init();
        eventReturn();
        loadData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadData() {
        TimeDisplayModule.showListView_Budget_time(ChooseTimeActivity.this, listView);
    }

    private void eventReturn() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void init() {
        listView = findViewById(R.id.Listview_budget_choosetime);
        buttonCancel = findViewById(R.id.buttonCancel_budget_choosetime);
        getSupportActionBar().hide();
    }
}
