package com.example.financial_savings.controllers.transactions;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.chooses.ChooseEventActivity;
import com.example.financial_savings.controllers.chooses.ChooseSavingsActivity;
import com.example.financial_savings.controllers.chooses.TabHostCateActivity;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.formats.DateFormatModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.icons.IconsDrawableModule;
import com.example.financial_savings.sessions.Session;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTransactionActivity extends AppCompatActivity implements IMappingView {
    private Button buttonSave;
    private ImageButton buttonCate, buttonCancel;
    private EditText editTextMoney, editTextCategory, editTextNote, editTextDate, editTextWallet, editTextEvent, editTextSavings;
    private DBHelper dbHelper;
    private SoGiaoDich soGiaoDich;
    private Session session;
    private DanhMuc danhMuc;
    private SuKien suKien;
    private static final String DOANHTHU = "doanhthu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_edit);
        init();
        loadData();
        eventReturn();
        eventChooseCate();
        eventChooseDate();
        eventChooseEvent();
        eventClearEvent();
        eventChooseSavings();
        eventSave();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void eventClearEvent() {
        editTextEvent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (editTextEvent.getRight() -
                            editTextEvent.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        session.clearEvent();
                        suKien = null;
                        editTextEvent.setText("");
                        editTextEvent.setHint(getResources().getString(R.string.event));
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void eventSave() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViCaNhan viCaNhan = handlingUpdateWallet();
                double money = Double.parseDouble(editTextMoney.getText().toString().replace(",", ""));
                String dateStr = editTextDate.getText().toString();
                String note = editTextNote.getText().toString();
                if(CheckEmptyModule.isEmpty(String.valueOf(money), dateStr, dateStr)) {
                    if(money > 0) {
                        if(!danhMuc.getLoaiDanhMuc().equals(DOANHTHU) && money > viCaNhan.getSoTien()) {
                            Toast.makeText(getApplicationContext(), R.string.invalid_money_old, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            java.sql.Date sqlDate = DateFormatModule.getDateSQL(dateStr);
                            handlingSave(money, sqlDate, note);
                        }
                    } else Toast.makeText(getApplicationContext(), R.string.invalid_money, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingSave(final double money, final java.sql.Date date, final String note) {
        updateMoneyWalletOld();
        soGiaoDich.setSoTien(money);
        soGiaoDich.setNgayGiaoDich(date);
        soGiaoDich.setGhiChu(note);
        soGiaoDich.setMaDanhMuc(danhMuc.getMaDanhMuc());
        if(suKien == null) {
            soGiaoDich.setMaSuKien("null");
        }
        else {
            soGiaoDich.setMaSuKien(suKien.getMaSuKien());
        }
        dbHelper.update_SoGiaoDich(soGiaoDich);
        updateMoneyWalletNew(money, danhMuc.getMaDanhMuc(), soGiaoDich.getMaVi());
        onBackPressed();
        Toast.makeText(getApplicationContext(), R.string.success_trans_edit, Toast.LENGTH_SHORT).show();
    }

    private void updateMoneyWalletNew(double money, String idCate, String idWallet) {
        ViCaNhan viCaNhan = dbHelper.getByID_ViCaNhan(idWallet);
        DanhMuc danhmuc = dbHelper.getByID_DanhMuc(idCate);
        if(danhmuc.getLoaiDanhMuc().equals(DOANHTHU)) {
            viCaNhan.napTien(String.valueOf(money));
        }
        else {
            viCaNhan.rutTien(String.valueOf(money));
        }
        dbHelper.update_ViCaNhan(viCaNhan);
    }

    private void updateMoneyWalletOld() {
        ViCaNhan viCaNhan = handlingUpdateWallet();
        dbHelper.update_ViCaNhan(viCaNhan);
    }

    private ViCaNhan handlingUpdateWallet() {
        double moneyOld = soGiaoDich.getSoTien();
        ViCaNhan viCaNhan = dbHelper.getByID_ViCaNhan(soGiaoDich.getMaVi());
        DanhMuc danhMucOld = dbHelper.getByID_DanhMuc(soGiaoDich.getMaDanhMuc());
        if(danhMucOld.getLoaiDanhMuc().equals(DOANHTHU)) {
            viCaNhan.rutTien(String.valueOf(moneyOld));
        }
        else {
            viCaNhan.napTien(String.valueOf(moneyOld));
        }
        return viCaNhan;
    }

    private void eventChooseEvent() {
        editTextEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTransactionActivity.this, ChooseEventActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventChooseSavings() {
        editTextSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTransactionActivity.this, ChooseSavingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventChooseCate() {
        editTextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTransactionActivity.this, TabHostCateActivity.class);
                startActivity(intent);
            }
        });
        buttonCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTransactionActivity.this, TabHostCateActivity.class);
                startActivity(intent);
            }
        });
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
                        EditTransactionActivity.this, callback, nYear, nMonth, nDay);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

    private void loadData() {
        String idTrans = getIntent().getExtras().getString("idTrans");
        soGiaoDich = dbHelper.getByID_SoGiaoDich(idTrans);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        editTextMoney.setText(FormatMoneyModule.formatAmount(soGiaoDich.getSoTien()));
        editTextDate.setText(formatter.format(soGiaoDich.getNgayGiaoDich()));
        loadNote(soGiaoDich.getGhiChu());
        loadWallet(soGiaoDich.getMaVi());
        loadSessionCate(soGiaoDich.getMaDanhMuc());
        loadSessionEvent(soGiaoDich.getMaSuKien());
        loadSessionSavings(soGiaoDich.getMaTietKiem());
    }

    private void loadWallet(String idWallet) {
        if(idWallet.isEmpty() || idWallet.equals("null")) {
            editTextWallet.setText("");
            editTextWallet.setHint(getResources().getString(R.string.wallet));
        }
        else {
            ViCaNhan viCaNhan = dbHelper.getByID_ViCaNhan(idWallet);
            editTextWallet.setText(viCaNhan.getTenVi());
        }
    }

    private void loadNote(String note) {
        if(note.equals("") || note.isEmpty()) {
            editTextNote.setText("");
            editTextNote.setHint(getResources().getString(R.string.note));
        }
        else {
            editTextNote.setText(note);
        }
    }

    private void loadSessionCate(String id) {
        String idCate = session.getIDCate();
        if(idCate != null && !idCate.isEmpty()) {
            try {
                danhMuc = dbHelper.getByID_DanhMuc(idCate);
                int resID = IconsDrawableModule.getResourcesDrawble(EditTransactionActivity.this, danhMuc.getBieuTuong());
                buttonCate.setImageResource(resID);
                editTextCategory.setText(danhMuc.getTenDanhMuc());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            danhMuc = dbHelper.getByID_DanhMuc(id);
            buttonCate.setImageResource(IconsDrawableModule.getResourcesDrawble(getApplicationContext(), danhMuc.getBieuTuong()));
            editTextCategory.setText(danhMuc.getTenDanhMuc());
        }
    }

    private void loadSessionEvent(String id) {
        String idEvent = session.getIDEvent();
        if(idEvent != null && !idEvent.isEmpty()) {
            try {
                suKien = dbHelper.getByID_SuKien(idEvent);
                editTextEvent.setText(suKien.getTenSuKien());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(!id.equals("null")){
            suKien = dbHelper.getByID_SuKien(id);
            editTextEvent.setText(suKien.getTenSuKien());
        }
        else {
            editTextEvent.setText("");
            editTextEvent.setHint(getResources().getString(R.string.event));
        }
    }

    private void loadSessionSavings(String id) {
        String idSavings = session.getIDEvent();
        TietKiem tietKiem;
        if(idSavings != null && !idSavings.isEmpty()) {
            try {
                tietKiem = dbHelper.getByID_TietKiem(idSavings);
                editTextSavings.setText(tietKiem.getTenTietKiem());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(!id.equals("null")){
            tietKiem = dbHelper.getByID_TietKiem(id);
            editTextSavings.setText(tietKiem.getTenTietKiem());
        }
        else {
            editTextSavings.setText("");
            editTextSavings.setHint(getResources().getString(R.string.saving));
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
    protected void onRestart() {
        super.onRestart();
        loadSessionCate(soGiaoDich.getMaDanhMuc());
        loadSessionEvent(soGiaoDich.getMaSuKien());
    }

    @Override
    public void init() {
        buttonCancel = findViewById(R.id.buttonCancel_transaction_edit);
        buttonSave = findViewById(R.id.buttonSave_transaction_edit);
        buttonCate = findViewById(R.id.buttonCate_transaction_edit);
        editTextMoney = findViewById(R.id.editTextMoney_transaction_edit);
        editTextCategory = findViewById(R.id.editTextCategory_transaction_edit);
        editTextNote = findViewById(R.id.editTextNote_transaction_edit);
        editTextDate= findViewById(R.id.editTextDate_transaction_edit);
        editTextWallet = findViewById(R.id.editTextWallet_transaction_edit);
        editTextEvent = findViewById(R.id.editTextEvent_transaction_edit);
        editTextSavings = findViewById(R.id.editTextSaving_transaction_edit);
        dbHelper = new DBHelper(this);
        session = new Session(this);
        session.clear();
        FormatMoneyModule.formatEditTextMoney(editTextMoney);
        editTextMoney.requestFocus();
        getSupportActionBar().hide();
    }
}
