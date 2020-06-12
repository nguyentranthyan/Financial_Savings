package com.example.financial_savings.controllers.events;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.formats.DateFormatModule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditEventActivity extends AppCompatActivity implements IMappingView {
    private Button buttonSave;
    private EditText editTextName, editTextDate;
    private ImageButton buttonCancel;
    private DBHelper dbHelper;
    private SuKien suKien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_edit);
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
                String name = editTextName.getText().toString();
                String dateStr = editTextDate.getText().toString();
                if(CheckEmptyModule.isEmpty(name, dateStr, name)) {
                    java.sql.Date sqlDate = DateFormatModule.getDateSQL(dateStr);
                    if(suKien.getTenSuKien().equals(name)) {
                        handlingSave(name, sqlDate);
                    }
                    else {
                        try {
                            dbHelper.getByName_SuKien(name);
                            Toast.makeText(getApplicationContext(), R.string.name_exist_event, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            handlingSave(name, sqlDate);
                        }
                    }
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingSave(final String name, final java.sql.Date date) {
        suKien.setTenSuKien(name);
        suKien.setNgayKetThuc(date);
        dbHelper.update_SuKien(suKien);
        onBackPressed();
        Toast.makeText(getApplicationContext(), R.string.success_event_edit, Toast.LENGTH_SHORT).show();
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
                        EditEventActivity.this, callback, nYear, nMonth, nDay);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

    private void loadData() {
        String idEvent = getIntent().getExtras().getString("idEvent");
        suKien = dbHelper.getByID_SuKien(idEvent);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        editTextName.setText(suKien.getTenSuKien());
        editTextDate.setText(formatter.format(suKien.getNgayKetThuc()));
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
        buttonCancel = findViewById(R.id.buttonCancel_event_edit);
        buttonSave = findViewById(R.id.buttonSave_event_edit);
        editTextName = findViewById(R.id.editTextName_event_edit);
        editTextDate = findViewById(R.id.editTextDate_event_edit);
        dbHelper = new DBHelper(this);
        editTextName.requestFocus();
        getSupportActionBar().hide();
    }
}
