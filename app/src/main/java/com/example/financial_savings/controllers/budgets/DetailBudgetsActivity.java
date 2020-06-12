package com.example.financial_savings.controllers.budgets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.ChiTietNganSach;
import com.example.financial_savings.entities.NganSach;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.budgets.MoneyBudgetModule;
import com.example.financial_savings.modules.dates.DateBetweenModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DetailBudgetsActivity extends AppCompatActivity implements IMappingView{
    private Button buttonSeeTrans;
    private ImageButton buttonCancel, buttonEdit, buttonDelete;
    private TextView editTextDate, editTextMoney, moneyEveryDate, expectedmoneyspent, actualspending, editTextDayRest;
    private DBHelper dbHelper;
    private NganSach nganSach;
    private String idBudgets;
//    LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_detail);
        init();
        loadData();
        eventReturn();
        eventEdit();
        eventDelete();
        eventdetailtrans();
//        drawchart();
    }

//    private void drawchart() {
//        double y, x;
//        x = -5.0;
//
//        GraphView graph = findViewById(R.id.graphview);
//        series = new LineGraphSeries<>();
//        for(int i = 0; i < 100; i++) {
//            x = x + 0.1;
//            y = Math.sin(x);
//            series.appendData(new DataPoint(x, y), true, 100);
//        }
//        graph.addSeries(series);
//    }

    private void eventdetailtrans() {
        buttonSeeTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBudgetsActivity.this, SeeTransBudgetsActivity.class);
                intent.putExtra("idBudgets", idBudgets);
                startActivity(intent);
            }
        });
    }

    private void eventDelete() {
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        deleteDetail();
                        dbHelper.delete_NganSach(nganSach);
                        Toast.makeText(getApplicationContext(), R.string.delete_budgets_success, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                };
                AlertConfirmModule.alertDialogConfirm(DetailBudgetsActivity.this, R.string.mes_delete_budgets, runnable);
            }
        });
    }

    private void deleteDetail() {
        ArrayList<ChiTietNganSach> list = dbHelper.getByIDBudget_ChiTietNganSach(nganSach.getMaNganSach());
        for (int i = 0; i < list.size(); i++) {
            dbHelper.delete_ChiTietNganSach(list.get(i));
        }
    }

    private void eventEdit() {
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBudgetsActivity.this, EditBudgetsActivity.class);
                intent.putExtra("idBudgets", idBudgets);
                startActivity(intent);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void loadData() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        idBudgets = getIntent().getExtras().getString("idBudgets");
        if(!idBudgets.isEmpty() && idBudgets != null) {
            nganSach = dbHelper.getByID_NganSach(idBudgets);
            editTextMoney.setText(FormatMoneyModule.formatAmount(nganSach.getSoTien()) + " VND");
            editTextDate.setText(formatter.format(nganSach.getNgayBatDau()) + " - " + formatter.format(nganSach.getNgayKetThuc()));

            int tongsongay = DateBetweenModule.daysBetween(nganSach.getNgayKetThuc(), nganSach.getNgayBatDau());
            int songayconlai = DateBetweenModule.daysBetween(nganSach.getNgayKetThuc(), new Date(Calendar.getInstance().getTime().getTime()));
            int songaydachi = tongsongay - songayconlai;

            int money = (int) (MoneyBudgetModule.getMoneyRestBudget(dbHelper, nganSach) / songayconlai);
            int totalMoneyExpenses = (int) MoneyBudgetModule.getTotalMoneyExpenses(dbHelper, idBudgets);
            int moneyspending = (totalMoneyExpenses / songaydachi);
            int expectMoneySpent = moneyspending * tongsongay;

            editTextDayRest.setText("Còn lại " + songayconlai + " ngày");

            //so tien nen chi moi ngay = tổng số tiền còn lại chia số ngày còn lai
            moneyEveryDate.setText(FormatMoneyModule.formatAmount(money) + " VND");

            //so tien thuc te chi = số tiền da chi chia cho so ngay da chi
            actualspending.setText(FormatMoneyModule.formatAmount(moneyspending) + " VND");

            //so tien du kien chi tieu = so tien thuc te chi * tong so ngay
            expectedmoneyspent.setText(FormatMoneyModule.formatAmount(expectMoneySpent) + " VND");
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailBudgetsActivity.this, HomeBudgetActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    @Override
    public void init() {
        buttonCancel = findViewById(R.id.buttonCancel_Budgets_detail);
        buttonEdit = findViewById(R.id.buttonEdit_Budgets_detail);
        buttonDelete = findViewById(R.id.buttonDelete_Budgets_detail);
        buttonSeeTrans = findViewById(R.id.buttonSeeTrans_budgets_detail);
        editTextMoney = findViewById(R.id.editTextMoney_Budgets_detail);
        editTextDate = findViewById(R.id.editTextDate_Budgets_detail);
        editTextDayRest = findViewById(R.id.editTextDayRest_Budgets_detail);
        moneyEveryDate = findViewById(R.id.moneyEveryDate);
        expectedmoneyspent = findViewById(R.id.Expectedmoneyspent);
        actualspending = findViewById(R.id.actualspending);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
