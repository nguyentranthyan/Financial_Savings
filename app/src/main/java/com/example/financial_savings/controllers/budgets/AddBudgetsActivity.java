package com.example.financial_savings.controllers.budgets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.NganSach;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.dates.DateGetStringModule;
import com.example.financial_savings.modules.formats.DateFormatModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.randoms.RandomIDModule;
import com.example.financial_savings.sessions.Session;

import java.sql.Date;

public class AddBudgetsActivity extends AppCompatActivity implements IMappingView {
    private Button buttonSave;
    private ImageButton buttonCancel;
    private EditText editTextDate, editTextMoney;
    private DBHelper dbHelper;
    private String timeStart, timeEnd;
    private Session session;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_add);
        init();
        loadData();
        eventReturn();
        eventChooseDate();
        eventSave();
    }

    private void eventSave() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = editTextMoney.getText().toString().replace(",", "");
                if(CheckEmptyModule.isEmpty(money, money, money)) {
                    if(Double.parseDouble(money) >= 300000) {
                        Date sqlStart = DateFormatModule.getDateSQL(timeStart);
                        Date sqlEnd = DateFormatModule.getDateSQL(timeEnd);
                        handlingSave(sqlStart, sqlEnd, money);
                    } else Toast.makeText(getApplicationContext(), R.string.condition_money, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingSave(final  Date sqlStart, final  Date sqlEnd, final String money) {
        NganSach nganSach = new NganSach();
        nganSach.setMaNganSach(RandomIDModule.getBudgetsID(dbHelper));
        nganSach.setNgayBatDau(sqlStart);
        nganSach.setSoTien(Double.parseDouble(money));
        nganSach.setNgayKetThuc(sqlEnd);
        dbHelper.insert_NganSach(nganSach);
        onBackPressed();
        Toast.makeText(getApplicationContext(), R.string.success_budgets_add, Toast.LENGTH_SHORT).show();
    }

    private void eventChooseDate() {
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBudgetsActivity.this, ChooseTimeActivity.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void loadData() {
        String nameTime = session.getNameTime();
        timeStart = session.getTimeStart();
        timeEnd = session.getTimeEnd();
        if(timeStart != null && !timeStart.isEmpty() && timeEnd != null && !timeEnd.isEmpty()) {
            editTextDate.setText(timeStart + " - " + timeEnd);
        }
        else if(nameTime != null && !nameTime.isEmpty()) {
            editTextDate.setText(nameTime);
            timeStart = DateGetStringModule.getDayStartByNameTime(nameTime);
            timeEnd = DateGetStringModule.getDayEndByNameTime(nameTime);
        }
        else {
            editTextDate.setHint(R.string.choosetime);
        }
    }

    private void eventReturn() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddBudgetsActivity.this, HomeBudgetActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void init() {
        buttonCancel = findViewById(R.id.buttonCancel_budget_add);
        buttonSave = findViewById(R.id.buttonSave_budget_add);
        editTextMoney = findViewById(R.id.editTextMoney_budget_add);
        editTextDate = findViewById(R.id.editTextDate_budget_add);
        dbHelper = new DBHelper(this);
        session = new Session(this);
        editTextMoney.requestFocus();
        FormatMoneyModule.formatEditTextMoney(editTextMoney);
        getSupportActionBar().hide();
    }
}
