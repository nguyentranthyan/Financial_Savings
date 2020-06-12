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
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.WalletDisplayModule;
import com.example.financial_savings.sessions.Session;

import java.util.ArrayList;

public class ChooseWalletActivity extends AppCompatActivity implements IMappingView {
    private ListView listViewMyWallet;
    private ImageButton buttonReturn;
    private DBHelper dbHelper;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    private static final String WALLET_FROM = "from";
    private static final String WALLET_TO = "to";
    private static final String WALLET_TOTAL = "total";
    private Session session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_home_choose);
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
        String name = getIntent().getExtras().getString("name");
        if(name.equals(WALLET_FROM)) {
            ArrayList<ViCaNhan> list = dbHelper.getAll_ViCaNhan();
            WalletDisplayModule.showListViewChoose_Wallet(list, getApplicationContext(), listViewMyWallet, WALLET_FROM);
        }
        else if(name.equals(WALLET_TO)){
            ViCaNhan viCaNhan = getViFrom();
            ArrayList<ViCaNhan> list = dbHelper.getAllAnother_ViCaNhan(viCaNhan.getMaVi());
            WalletDisplayModule.showListViewChoose_Wallet(list, getApplicationContext(), listViewMyWallet, WALLET_TO);
        }
        else {
            ArrayList<ViCaNhan> list = dbHelper.getAll_ViCaNhan();
            WalletDisplayModule.showListViewChoose_Wallet(list, getApplicationContext(), listViewMyWallet, WALLET_TOTAL);
        }
    }

    private ViCaNhan getViFrom() {
        ViCaNhan viCaNhan = null;
        String idWallet = session.getIDWallet();
        if(idWallet != null && !idWallet.isEmpty()) {
            try {
                viCaNhan = dbHelper.getByID_ViCaNhan(idWallet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return viCaNhan;
    }

    @Override
    public void init() {
        listViewMyWallet = findViewById(R.id.listView_mywallet_choose);
        buttonReturn = findViewById(R.id.buttonReturn_wallet_home_choose);
        dbHelper = new DBHelper(this);
        session = new Session(this);
        getSupportActionBar().hide();
    }
}
