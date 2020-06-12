package com.example.financial_savings.controllers.events;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.checks.CheckDateFinishModule;
import com.example.financial_savings.modules.dates.DateBetweenModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DetailEventActivity extends AppCompatActivity implements IMappingView {
    private Button buttonTick, buttonSeeTrans;
    private TextView editTextName, editTextDate, editTextDay, editTextDateStart;
    private ImageButton buttonCancel, buttonDelete, buttonEdit;
    private DBHelper dbHelper;
    private String idEvent;
    private SuKien suKien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);
        init();
        loadData();
        eventReturn();
        eventEdit();
        eventDelete();
        eventTick();
        eventSeeTrans();
    }

    private void eventSeeTrans() {
        buttonSeeTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailEventActivity.this, SeeTransEventActivity.class);
                intent.putExtra("idEvent", idEvent);
                startActivity(intent);
            }
        });
    }

    private void eventTick() {
        buttonTick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckDateFinishModule.isFinish(suKien.getNgayKetThuc())) {
                    Toast.makeText(getApplicationContext(), R.string.pls_edit_detail_event, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailEventActivity.this, EditEventActivity.class);
                    intent.putExtra("idEvent", idEvent);
                    startActivity(intent);
                }
                else {
                    suKien.setNgayKetThuc(new java.sql.Date(new Date(Calendar.getInstance().getTime().getTime()).getTime()));
                    dbHelper.update_SuKien(suKien);
                    Toast.makeText(getApplicationContext(), R.string.success_tick_detail_event, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
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
                        updateTransaction();
                        dbHelper.delete_SuKien(suKien);
                        Toast.makeText(getApplicationContext(), R.string.delete_event_success, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                };
                AlertConfirmModule.alertDialogConfirm(DetailEventActivity.this, R.string.mes_delete_event, runnable);
            }
        });
    }

    private void updateTransaction() {
        ArrayList<SoGiaoDich> list = dbHelper.getByEvent_SoGiaoDich(idEvent);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setMaSuKien("null");
            dbHelper.update_SoGiaoDich(list.get(i));
        }
    }

    private void eventEdit() {
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailEventActivity.this, EditEventActivity.class);
                intent.putExtra("idEvent", idEvent);
                startActivity(intent);
            }
        });
    }

    private void eventReturn() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        idEvent = getIntent().getExtras().getString("idEvent");
        suKien = dbHelper.getByID_SuKien(idEvent);
        int date = DateBetweenModule.daysBetween(suKien.getNgayKetThuc(), new Date(Calendar.getInstance().getTime().getTime()));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        editTextName.setText(suKien.getTenSuKien());
        editTextDateStart.setText(formatter.format(suKien.getNgayBatDau()) + " (Ngày bắt đầu)");
        editTextDate.setText(formatter.format(suKien.getNgayKetThuc()) + " (Ngày kết thúc)");
        if(CheckDateFinishModule.isFinish(suKien.getNgayKetThuc())) {
            buttonTick.setText(getResources().getString(R.string.untick_event));
            editTextDay.setText("Đã kết thúc");
        }
        else {
            buttonTick.setText(getResources().getString(R.string.tick_event));
            editTextDay.setText("Còn lại " + date + " ngày");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailEventActivity.this, HomeEventActivity.class);
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
        buttonCancel = findViewById(R.id.buttonCancel_event_detail);
        buttonEdit = findViewById(R.id.buttonEdit_event_detail);
        buttonDelete = findViewById(R.id.buttonDelete_event_detail);
        buttonTick = findViewById(R.id.buttonTick_event_detail);
        buttonSeeTrans = findViewById(R.id.buttonSeeTrans_event_detail);
        editTextName = findViewById(R.id.editTextName_event_detail);
        editTextDate = findViewById(R.id.editTextDate_event_detail);
        editTextDateStart = findViewById(R.id.editTextDateStart_event_detail);
        editTextDay = findViewById(R.id.editTextDay_event_detail);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
