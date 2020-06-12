package com.example.financial_savings.controllers.events;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.formats.DateFormatModule;
import com.example.financial_savings.modules.randoms.RandomIDModule;

import java.util.Calendar;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity implements IMappingView {
    private Button buttonSave;
    private ImageButton buttonCancel;
    private EditText editTextName, editTextDate;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_add);
        init();
        eventReturn();
        eventChooseDate();
        eventSave();
    }

    private void eventSave() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String dateStr = editTextDate.getText().toString();
                if(CheckEmptyModule.isEmpty(name, dateStr, name)) {
                    java.sql.Date sqlDate = DateFormatModule.getDateSQL(dateStr);
                    try {
                        dbHelper.getByName_SuKien(name);
                        Toast.makeText(getApplicationContext(), R.string.name_exist_event, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        handlingSave(name, sqlDate);
                    }
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingSave(final String name, final java.sql.Date date) {
        SuKien suKien = new SuKien();
        suKien.setMaSuKien(RandomIDModule.getEventID(dbHelper));
        suKien.setTenSuKien(name);
        suKien.setNgayBatDau(new java.sql.Date(new Date(Calendar.getInstance().getTime().getTime()).getTime()));
        suKien.setNgayKetThuc(date);
        dbHelper.insert_SuKien(suKien);
        onBackPressed();
        Toast.makeText(getApplicationContext(), R.string.success_event_add, Toast.LENGTH_SHORT).show();
    }

    private void eventChooseDate() {
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int nYear = c.get(Calendar.YEAR);
                int nMonth = c.get(Calendar.MONTH);
                int nDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDate.setText(date);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddEventActivity.this, callback, nYear, nMonth, nDay);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddEventActivity.this, HomeEventActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void init() {
        buttonCancel = findViewById(R.id.buttonCancel_event_add);
        buttonSave = findViewById(R.id.buttonSave_event_add);
        editTextName = findViewById(R.id.editTextName_event_add);
        editTextDate = findViewById(R.id.editTextDate_event_add);
        dbHelper = new DBHelper(this);
        editTextName.requestFocus();
        getSupportActionBar().hide();
    }
}
