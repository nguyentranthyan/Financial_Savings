package com.example.financial_savings.controllers.transactions;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.chooses.ChooseEventActivity;
import com.example.financial_savings.controllers.chooses.ChooseSavingsActivity;
import com.example.financial_savings.controllers.chooses.ChooseWalletActivity;
import com.example.financial_savings.controllers.chooses.TabHostCateActivity;
import com.example.financial_savings.entities.ChiTietNganSach;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.NganSach;
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
import com.example.financial_savings.modules.others.AccountCurrentModule;
import com.example.financial_savings.modules.randoms.RandomIDModule;
import com.example.financial_savings.modules.savings.MoneySavingsModule;
import com.example.financial_savings.sessions.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddTransactionActivity extends AppCompatActivity implements IMappingView {
    private Button buttonSave;
    private EditText editTextMoney, editTextCategory, editTextNote, editTextDate, editTextWallet, editTextEvent, editTextSaving, editTextRemind;
    private CheckBox checkBoxRepeat, checkBoxFast;
    private ImageButton buttonIconCate, buttonHelpRepeat, buttonHelpFast;
    private TextView textViewRepeat;
    private DBHelper dbHelper;
    private Session session;
    private DanhMuc danhMuc;
    private ViCaNhan viCaNhan;
    private SuKien suKien;
    private TietKiem tietKiem;
    private static final String WALLET_FROM = "from";
    private static final String DATE = "01/01/2100";
    private static final String KHOANCHI = "khoanchi";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_add);
        init();
        loadData();
        eventChooseWallet();
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
                String note = editTextNote.getText().toString();
                String money = editTextMoney.getText().toString().replace(",", "");
                String dateStr = editTextDate.getText().toString();
                java.sql.Date sqlDate = DateFormatModule.getDateSQL(dateStr);
                if(tietKiem != null) {
                    if(danhMuc != null) {
                        handlingInput(money, note, sqlDate);
                    } else Toast.makeText(AddTransactionActivity.this, R.string.empty_cate, Toast.LENGTH_SHORT).show();
                }
                else {
                    if(viCaNhan != null && danhMuc != null) {
                        handlingInput(money, note, sqlDate);
                    } else Toast.makeText(AddTransactionActivity.this, R.string.empty_cate_wallet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handlingInput(final String money, final String note, final java.sql.Date sqlDate) {
        if(CheckEmptyModule.isEmpty(money, money, money)) {
            if(viCaNhan != null) {
                if(danhMuc.getLoaiDanhMuc().equals("khoanchi")) {
                    if(viCaNhan.getSoTien() >= Double.parseDouble(money)) {
                        handlingSaveTrans(money, note, sqlDate);
                    } else Toast.makeText(AddTransactionActivity.this, R.string.invalid_money, Toast.LENGTH_SHORT).show();
                } else handlingSaveTrans(money, note, sqlDate);
            }
            else {
                double total = MoneySavingsModule.getMoneySavings(dbHelper, tietKiem);
                if(danhMuc.getLoaiDanhMuc().equals("khoanchi")) {
                    if(total >= Double.parseDouble(money)) {
                        handlingSaveTrans(money, note, sqlDate);
                    } else Toast.makeText(AddTransactionActivity.this, R.string.invalid_money, Toast.LENGTH_SHORT).show();
                } else handlingSaveTrans(money, note, sqlDate);
            }
        } else Toast.makeText(AddTransactionActivity.this, R.string.empty_money, Toast.LENGTH_SHORT).show();
    }

    private void handlingSaveTrans(final String money, final String note, final java.sql.Date sqlDate) {
        if(Double.parseDouble(money) > 0) {
            SoGiaoDich soGiaoDich = new SoGiaoDich();
            soGiaoDich.setMaGiaoDich(RandomIDModule.getTransID(dbHelper));
            soGiaoDich.setSoTien(Double.parseDouble(money));
            soGiaoDich.setGhiChu(note);
            soGiaoDich.setNgayGiaoDich(sqlDate);
            soGiaoDich.setMasv(AccountCurrentModule.getSinhVienCurrent(dbHelper).getMasv());
            soGiaoDich.setMaDanhMuc(danhMuc.getMaDanhMuc());
            handlingSetProperty(soGiaoDich);
            handlingSetStatus(soGiaoDich);
            dbHelper.insert_SoGiaoDich(soGiaoDich);
            handlingUpdateWallet(money);
            handlingCheckBudget(soGiaoDich);
            AddTransactionActivity.this.onBackPressed();
            Toast.makeText(AddTransactionActivity.this, R.string.success_add_trans, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(AddTransactionActivity.this, R.string.invalid_money, Toast.LENGTH_SHORT).show();
    }

    private void handlingSetStatus(SoGiaoDich soGiaoDich) {
        if(checkBoxFast.isChecked()) {
            soGiaoDich.setStatus(1);
        }
        else {
            soGiaoDich.setStatus(0);
        }
    }

    private void handlingSetProperty(SoGiaoDich soGiaoDich) {
        if(viCaNhan != null) {
            soGiaoDich.setMaVi(viCaNhan.getMaVi());
        }
        else {
            soGiaoDich.setMaVi("null");
        }
        if(tietKiem != null) {
            soGiaoDich.setMaTietKiem(tietKiem.getMaTietKiem());
        }
        else {
            soGiaoDich.setMaTietKiem("null");
        }
        if(suKien != null) {
            soGiaoDich.setMaSuKien(suKien.getMaSuKien());
        }
        else {
            soGiaoDich.setMaSuKien("null");
        }
    }

    private void handlingCheckBudget(SoGiaoDich soGiaoDich) {
        DanhMuc danhmuc = dbHelper.getByID_DanhMuc(soGiaoDich.getMaDanhMuc());
        if(danhmuc.getLoaiDanhMuc().equals(KHOANCHI)) {
            ChiTietNganSach chiTietNganSach = new ChiTietNganSach();
            java.sql.Date dateSGD = soGiaoDich.getNgayGiaoDich();
            ArrayList<NganSach> allNganSach = dbHelper.getAll_NganSach();
            for (int i = 0; i < allNganSach.size(); i++) {
                NganSach nganSach = allNganSach.get(i);
                if(dateSGD.equals(nganSach.getNgayBatDau())
                        || dateSGD.equals(nganSach.getNgayKetThuc())
                        || dateSGD.after(nganSach.getNgayBatDau())
                        && dateSGD.before(nganSach.getNgayKetThuc())) {
                    chiTietNganSach.setMaGiaoDich(soGiaoDich.getMaGiaoDich());
                    chiTietNganSach.setMaNganSach(nganSach.getMaNganSach());
                    dbHelper.insert_ChiTietNganSach(chiTietNganSach);
                }
            }
        }
    }

    private void handlingUpdateWallet(String money) {
        if(viCaNhan != null) {
            if(danhMuc.getLoaiDanhMuc().equals("doanhthu")) {
                viCaNhan.napTien(money);
            }
            else {
                viCaNhan.rutTien(money);
            }
            dbHelper.update_ViCaNhan(viCaNhan);
        }
        else {
            double total = MoneySavingsModule.getMoneySavings(dbHelper, tietKiem);
            if(total == 0) {
                tietKiem.setNgayKetThuc(new java.sql.Date((DateFormatModule.getDate(DATE).getTime())));
            }
            else {
                try {
                    double goal = tietKiem.getSoTien();
                    double average = MoneySavingsModule.getAverageMoney(dbHelper, tietKiem);
                    int num_day = (int) (goal / average);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date now = new Date(Calendar.getInstance().getTime().getTime());
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTime(now);
                    calendar.add(GregorianCalendar.DAY_OF_MONTH, num_day);
                    tietKiem.setNgayKetThuc(java.sql.Date.valueOf(df.format(calendar.getTime())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dbHelper.update_TietKiem(tietKiem);
        }
    }

    private void eventChooseSavings() {
        editTextSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, ChooseSavingsActivity.class);
                startActivity(intent);
                session.clearWallet();
            }
        });
    }

    private void eventChooseEvent() {
        editTextEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, ChooseEventActivity.class);
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
                        AddTransactionActivity.this.getApplicationContext(), callback, nYear, nMonth, nDay);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

    private void eventChooseCate() {
        editTextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, TabHostCateActivity.class);
                startActivity(intent);
            }
        });
        buttonIconCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, TabHostCateActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventChooseWallet() {
        editTextWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionActivity.this, ChooseWalletActivity.class);
                intent.putExtra("name", WALLET_FROM);
                startActivity(intent);
                session.clearSavings();
            }
        });
    }

    private void loadData() {
        loadSessionCate();
        loadSessionWallet();
        loadSessionEvent();
        loadSessionSavings();
    }

    private void loadSessionCate() {
        String idCate = session.getIDCate();
        if(idCate != null && !idCate.isEmpty()) {
            try {
                danhMuc = dbHelper.getByID_DanhMuc(idCate);
                int resID = IconsDrawableModule.getResourcesDrawble(AddTransactionActivity.this.getApplicationContext(),
                        danhMuc.getBieuTuong());
                buttonIconCate.setImageResource(resID);
                editTextCategory.setText(danhMuc.getTenDanhMuc());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            buttonIconCate.setImageResource(R.drawable.ic_help_black_24dp);
            editTextCategory.setText("");
            editTextCategory.setHint(getResources().getString(R.string.category));
        }
    }

    private void loadSessionWallet() {
        String idWallet = session.getIDWallet();
        if(idWallet != null && !idWallet.isEmpty()) {
            try {
                viCaNhan = dbHelper.getByID_ViCaNhan(idWallet);
                editTextWallet.setText(viCaNhan.getTenVi());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(danhMuc != null) {
            viCaNhan = dbHelper.getByID_ViCaNhan(danhMuc.getMaVi());
            editTextWallet.setText(viCaNhan.getTenVi());
        }
        else {
            viCaNhan = null;
            editTextWallet.setText("");
            editTextWallet.setHint(getResources().getString(R.string.wallet));
        }
    }

    private void loadSessionEvent() {
        String idEvent = session.getIDEvent();
        if(idEvent != null && !idEvent.isEmpty()) {
            try {
                suKien = dbHelper.getByID_SuKien(idEvent);
                editTextEvent.setText(suKien.getTenSuKien());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            editTextEvent.setText("");
            editTextEvent.setHint(getResources().getString(R.string.event));
        }
    }

    private void loadSessionSavings() {
        String idSavings = session.getIDSavings();
        if(idSavings != null && !idSavings.isEmpty()) {
            try {
                tietKiem = dbHelper.getByID_TietKiem(idSavings);
                editTextSaving.setText(tietKiem.getTenTietKiem());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            tietKiem = null;
            editTextSaving.setText("");
            editTextSaving.setHint(getResources().getString(R.string.saving));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void init() {
        buttonSave = findViewById(R.id.buttonSave_transaction_add);
        editTextMoney = findViewById(R.id.editTextMoney_transaction_add);
        editTextCategory = findViewById(R.id.editTextCategory_transaction_add);
        editTextNote = findViewById(R.id.editTextNote_transaction_add);
        editTextDate = findViewById(R.id.editTextDate_transaction_add);
        editTextWallet = findViewById(R.id.editTextWallet_transaction_add);
        editTextEvent = findViewById(R.id.editTextEvent_transaction_add);
        editTextSaving = findViewById(R.id.editTextSaving_transaction_add);
        buttonIconCate = findViewById(R.id.buttonCate_transaction_add);
        editTextRemind = findViewById(R.id.editTextRemind_transaction_add);
        checkBoxRepeat = findViewById(R.id.checkBoxRepeat_transaction_add);
        checkBoxFast = findViewById(R.id.checkBoxFast_transaction_add);
        buttonHelpRepeat = findViewById(R.id.buttonHelpRepeat_transaction_add);
        buttonHelpFast = findViewById(R.id.buttonHelpFast_transaction_add);
        textViewRepeat = findViewById(R.id.textViewRepeat_transaction_add);
        dbHelper = new DBHelper(getApplicationContext());
        session = new Session(getApplicationContext());
        session.clear();
        FormatMoneyModule.formatEditTextMoney(editTextMoney);
        editTextMoney.requestFocus();
    }
}
