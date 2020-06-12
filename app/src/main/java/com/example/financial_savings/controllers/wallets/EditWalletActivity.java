package com.example.financial_savings.controllers.wallets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;

import java.util.ArrayList;

public class EditWalletActivity extends AppCompatActivity implements IMappingView {
    private EditText editTextName, editTextMoney;
    private ImageButton buttonCancel, buttonDeleteIcon;
    private Button buttonSave, buttonDeleteWallet;
    private DBHelper dbHelper;
    private ViCaNhan viCaNhan;
    private String idWallet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_edit);
        init();
        loadData();
        eventReturn();
        eventSave();
        eventDelete();
    }

    private void eventDelete() {
        buttonDeleteWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWallet();
            }
        });
        buttonDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWallet();
            }
        });
    }

    private void deleteWallet() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                deleteTransaction();
                dbHelper.delete_ViCaNhan(viCaNhan);
                Toast.makeText(getApplicationContext(), R.string.delete_wallet_success, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        };
        AlertConfirmModule.alertDialogConfirm(EditWalletActivity.this, R.string.mes_delete_wallet, runnable);
    }

    private void deleteTransaction() {
        ArrayList<SoGiaoDich> list = dbHelper.getByWallet_SoGiaoDich(idWallet);
        for (int i = 0; i < list.size(); i++) {
            dbHelper.delete_SoGiaoDich(list.get(i));
        }
    }

    private void eventSave() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String money = editTextMoney.getText().toString().replace(",", "");
                if(CheckEmptyModule.isEmpty(name, money, money)) {
                    if(Double.parseDouble(money) > 0) {
                        if(viCaNhan.getTenVi().equals(name)) {
                            handlingSave(name, money);
                        } else {
                            try {
                                dbHelper.getByName_ViCaNhan(name);
                                Toast.makeText(getApplicationContext(), R.string.name_exist_wallet_add, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                handlingSave(name, money);
                            }
                        }
                    } else Toast.makeText(getApplicationContext(), R.string.invalid_money, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingSave(final String name, final String money) {
        viCaNhan.setTenVi(name);
        viCaNhan.setSoTien(Double.parseDouble(money));
        try {
            dbHelper.update_ViCaNhan(viCaNhan);
            onBackPressed();
            Toast.makeText(getApplicationContext(), R.string.success_wallet_edit, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.unsuccess_wallet_edit, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        idWallet = getIntent().getExtras().getString("idWallet");
        viCaNhan = dbHelper.getByID_ViCaNhan(idWallet);
        editTextName.setText(viCaNhan.getTenVi());
        editTextMoney.setText(FormatMoneyModule.formatAmount(viCaNhan.getSoTien()));
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
        editTextName = findViewById(R.id.editTextName_wallet_edit);
        editTextMoney = findViewById(R.id.editTextMoney_wallet_edit);
        buttonCancel = findViewById(R.id.buttonCancel_wallet_edit);
        buttonSave = findViewById(R.id.buttonSave_wallet_edit);
        buttonDeleteWallet = findViewById(R.id.buttonDeleteWallet_wallet_edit);
        buttonDeleteIcon = findViewById(R.id.buttonDeleteIcon_wallet_edit);
        dbHelper = new DBHelper(this);
        editTextName.requestFocus();
        FormatMoneyModule.formatEditTextMoney(editTextMoney);
        getSupportActionBar().hide();
    }
}
