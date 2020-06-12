package com.example.financial_savings.controllers.savings;

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
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.formats.DateFormatModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditSavingsActivity extends AppCompatActivity implements IMappingView {
    private Button buttonSave;
    private ImageButton buttonCancel;
    private EditText editTextName, editTextDate, editTextMoney;
    private DBHelper dbHelper;
    private TietKiem tietKiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saving_edit);
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
                String money = editTextMoney.getText().toString().replace(",", "");
                if(CheckEmptyModule.isEmpty(name, dateStr, money)) {
                    if(Double.parseDouble(money) > 0) {
                        java.sql.Date sqlDate = DateFormatModule.getDateSQL(dateStr);
                        if(tietKiem.getTenTietKiem().equals(name)) {
                            handlingSave(name, sqlDate, money);
                        }
                        else {
                            try {
                                dbHelper.getByName_TietKiem(name);
                                Toast.makeText(getApplicationContext(), R.string.name_exist_savings, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                handlingSave(name, sqlDate, money);
                            }
                        }
                    } else Toast.makeText(getApplicationContext(), R.string.invalid_money, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingSave(final String name, final java.sql.Date date, final String money) {
        tietKiem.setTenTietKiem(name);
        tietKiem.setNgayBatDau(date);
        tietKiem.setSoTien(Double.parseDouble(money));
        dbHelper.update_TietKiem(tietKiem);
        onBackPressed();
        Toast.makeText(getApplicationContext(), R.string.success_savings_edit, Toast.LENGTH_SHORT).show();
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
                        EditSavingsActivity.this, callback, nYear, nMonth, nDay);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        String idSavings = getIntent().getExtras().getString("idSavings");
        tietKiem = dbHelper.getByID_TietKiem(idSavings);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        editTextName.setText(tietKiem.getTenTietKiem());
        editTextDate.setText(formatter.format(tietKiem.getNgayBatDau()));
        editTextMoney.setText(FormatMoneyModule.formatAmount(tietKiem.getSoTien()));
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
        buttonCancel = findViewById(R.id.buttonCancel_saving_edit);
        buttonSave = findViewById(R.id.buttonSave_saving_edit);
        editTextName = findViewById(R.id.editTextName_saving_edit);
        editTextDate = findViewById(R.id.editTextDate_saving_edit);
        editTextMoney = findViewById(R.id.editTextMoney_saving_edit);
        dbHelper = new DBHelper(this);
        FormatMoneyModule.formatEditTextMoney(editTextMoney);
        editTextName.requestFocus();
        getSupportActionBar().hide();
    }
}
