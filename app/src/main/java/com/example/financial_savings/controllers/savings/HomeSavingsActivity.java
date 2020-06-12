package com.example.financial_savings.controllers.savings;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;

import com.example.financial_savings.R;
import com.example.financial_savings.interfaces.IMappingView;

public class HomeSavingsActivity extends TabActivity implements IMappingView {
    private ImageButton imageButtonAdd, buttonReturn;
    private static final String APDUNG = "Đang áp dụng";
    private static final String KETTHUC = "Đã kết thúc";
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savings_home);
        activity = this;
        init();
        tabHost();
        eventReturn();
        eventAddSavings();
    }

    private void eventAddSavings() {
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeSavingsActivity.this, AddSavingsActivity.class);
                startActivity(intent);
                onBackPressed();
            }
        });
    }

    private void eventReturn() {
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void tabHost() {
        TabHost tabHost = getTabHost();

        TabHost.TabSpec tab1 = tabHost.newTabSpec(APDUNG);
        tab1.setIndicator(APDUNG, getResources().getDrawable(R.color.colorLightBlue));
        Intent photosIntent = new Intent(HomeSavingsActivity.this, TabApplySavingsActivity.class);
        tab1.setContent(photosIntent);

        TabHost.TabSpec tab2 = tabHost.newTabSpec(KETTHUC);
        tab2.setIndicator(KETTHUC, getResources().getDrawable(R.color.colorLightBlue));
        Intent songsIntent = new Intent(HomeSavingsActivity.this, TabFinishSavingsActivity.class);
        tab2.setContent(songsIntent);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
    }

    @Override
    public void init() {
        buttonReturn = findViewById(R.id.buttonReturn_savings_home);
        imageButtonAdd = findViewById(R.id.imageButtonAdd_savings_home);
    }
}
