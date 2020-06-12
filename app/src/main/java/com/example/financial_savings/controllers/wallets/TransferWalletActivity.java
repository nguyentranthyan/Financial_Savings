package com.example.financial_savings.controllers.wallets;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.chooses.ChooseWalletActivity;
import com.example.financial_savings.controllers.chooses.TabHostCateActivity;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.others.AccountCurrentModule;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.formats.DateFormatModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.icons.IconsDrawableModule;
import com.example.financial_savings.modules.randoms.RandomIDModule;
import com.example.financial_savings.sessions.Session;

import java.util.Calendar;
import java.util.Date;

public class TransferWalletActivity extends AppCompatActivity implements IMappingView {
    private EditText editTextMoney, editTextWallet, editTextCategory, editTextNote, editTextWalletReceive, editTextDate;
    private Button buttonSave;
    private ImageButton buttonIconCate, buttonCancel;
    private DBHelper dbHelper;
    private Session session;
    private DanhMuc danhMuc;
    private ViCaNhan viCaNhan;
    private ViCaNhan viCaNhanReceive;
    private static final String WALLET_FROM = "from";
    private static final String WALLET_TO = "to";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_transfers);
        init();
        loadData();
        eventReturn();
        eventChooseWallet();
        eventChooseCate();
        eventChooseDate();
        eventSave();
    }

    private void eventSave() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = editTextNote.getText().toString();
                String money = editTextMoney.getText().toString().replace(",", "");
                String dateStr = editTextDate.getText().toString();
                java.sql.Date sqlDate = DateFormatModule.getDateSQL(dateStr);
                if(viCaNhan != null && viCaNhanReceive != null && danhMuc != null) {
                    if(CheckEmptyModule.isEmpty(money, money, money)) {
                        if(viCaNhan.getSoTien() >= Double.parseDouble(money) && Double.parseDouble(money) > 0) {
                            handlingSaveTrans(money, note, sqlDate);
                        } else Toast.makeText(getApplicationContext(), R.string.invalid_money, Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getApplicationContext(), R.string.empty_money, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingUpdateWallet(String money) {
        viCaNhan.rutTien(money);
        dbHelper.update_ViCaNhan(viCaNhan);
        viCaNhanReceive.napTien(money);
        dbHelper.update_ViCaNhan(viCaNhanReceive);
    }

    private void handlingSaveTrans(final String money, final String note, final java.sql.Date sqlDate) {
        SoGiaoDich soGiaoDich = new SoGiaoDich();
        soGiaoDich.setMaGiaoDich(RandomIDModule.getTransID(dbHelper));
        soGiaoDich.setSoTien(Double.parseDouble(money));
        soGiaoDich.setGhiChu(note);
        soGiaoDich.setNgayGiaoDich(sqlDate);
        soGiaoDich.setMasv(AccountCurrentModule.getSinhVienCurrent(dbHelper).getMasv());
        soGiaoDich.setMaSuKien("null");
        soGiaoDich.setMaDanhMuc(danhMuc.getMaDanhMuc());
        soGiaoDich.setMaVi(viCaNhan.getMaVi());
        soGiaoDich.setMaTietKiem("null");
        dbHelper.insert_SoGiaoDich(soGiaoDich);
        handlingUpdateWallet(money);
        onBackPressed();
        Toast.makeText(getApplicationContext(), R.string.success_wallet_transfer, Toast.LENGTH_SHORT).show();
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
                        TransferWalletActivity.this, callback, nYear, nMonth, nDay);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
    }

    private void eventChooseCate() {
        editTextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TabHostCateActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventChooseWallet() {
        editTextWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChooseWalletActivity.class);
                intent.putExtra("name", WALLET_FROM);
                startActivity(intent);
            }
        });
        editTextWalletReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idWallet = session.getIDWallet();
                if(idWallet != null && !idWallet.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), ChooseWalletActivity.class);
                    intent.putExtra("name", WALLET_TO);
                    startActivity(intent);
                } else Toast.makeText(getApplicationContext(), R.string.chooseWallet, Toast.LENGTH_SHORT).show();
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

    private void loadData() {
        loadSessionCate();
        loadSessionWallet();
        loadSessionWalletReceive();
    }

    @SuppressLint("SetTextI18n")
    private void loadSessionWalletReceive() {
        String idWallet = session.getIDWalletReceive();
        if(idWallet != null && !idWallet.isEmpty()) {
            try {
                viCaNhanReceive = dbHelper.getByID_ViCaNhan(idWallet);
                editTextWalletReceive.setText(viCaNhanReceive.getTenVi());
                editTextNote.setText(getResources().getString(R.string.note_wallet_transfer) + " " + viCaNhanReceive.getTenVi());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            editTextWalletReceive.setText("");
            editTextWalletReceive.setHint(getResources().getString(R.string.wallet));
            editTextNote.setText("");
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
        else {
            editTextWallet.setText("");
            editTextWallet.setHint(getResources().getString(R.string.wallet));
        }
    }

    private void loadSessionCate() {
        String idCate = session.getIDCate();
        if(idCate != null && !idCate.isEmpty()) {
            try {
                danhMuc = dbHelper.getByID_DanhMuc(idCate);
                int resID = IconsDrawableModule.getResourcesDrawble(getApplicationContext(), danhMuc.getBieuTuong());
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

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    @Override
    public void init() {
        editTextWallet = findViewById(R.id.editTextWallet_wallet_transfers);
        editTextMoney = findViewById(R.id.editTextMoney_wallet_transfers);
        editTextCategory = findViewById(R.id.editTextCategory_wallet_transfers);
        editTextNote = findViewById(R.id.editTextNote_wallet_transfers);
        editTextWalletReceive = findViewById(R.id.editTextWalletReceive_wallet_transfers);
        editTextDate = findViewById(R.id.editTextDate_wallet_transfers);
        buttonCancel = findViewById(R.id.buttonCancel_wallet_transfers);
        buttonSave = findViewById(R.id.buttonSave_wallet_transfers);
        buttonIconCate = findViewById(R.id.buttonCate_wallet_transfers);
        dbHelper = new DBHelper(this);
        session = new Session(this);
        session.clear();
        FormatMoneyModule.formatEditTextMoney(editTextMoney);
        editTextMoney.requestFocus();
        getSupportActionBar().hide();
    }
}
