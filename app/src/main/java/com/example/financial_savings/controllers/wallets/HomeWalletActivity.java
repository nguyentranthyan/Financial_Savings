package com.example.financial_savings.controllers.wallets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.WalletDisplayModule;

import java.util.ArrayList;

public class HomeWalletActivity extends AppCompatActivity implements IMappingView {
    private ListView listViewMyWallet;
    private ImageButton imageButtonAdd, buttonReturn;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_home);
        init();
        loadData();
        eventReturn();
        eventAddWallet();
    }

    private void eventAddWallet() {
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeWalletActivity.this, AddWalletActivity.class);
                startActivity(intent);
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

    private void loadData() {
        ArrayList<ViCaNhan> list = dbHelper.getAll_ViCaNhan();
        WalletDisplayModule.showListViewHome_Wallet(list, getApplicationContext(), listViewMyWallet);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void init() {
        listViewMyWallet = findViewById(R.id.listView_mywallet);
        buttonReturn = findViewById(R.id.buttonReturn_wallet_home);
        imageButtonAdd = findViewById(R.id.imageButtonAdd_wallet_home);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
