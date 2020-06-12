package com.example.financial_savings.controllers.transactions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.ChiTietNganSach;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.icons.IconsDrawableModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailTransactionActivity extends AppCompatActivity implements IMappingView {
    private ImageButton buttonCancel, buttonEdit, buttonDelete;
    private TextView editTextNameCate, editTextMoney, editTextNote, editTextDate, editTextWallet, editTextEvent, editTextSavings;
    private ImageButton buttonIconCate;
    private DBHelper dbHelper;
    private SoGiaoDich soGiaoDich;
    private String idTrans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail);
        init();
        loadData();
        eventReturn();
        eventEdit();
        eventDelete();
    }

    private void eventDelete() {
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        deleteDetail();
                        dbHelper.delete_SoGiaoDich(soGiaoDich);
                        Toast.makeText(getApplicationContext(), R.string.delete_trans_success, Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                };
                AlertConfirmModule.alertDialogConfirm(DetailTransactionActivity.this, R.string.mes_delete_trans, runnable);
            }
        });
    }

    private void deleteDetail() {
        ArrayList<ChiTietNganSach> list = dbHelper.getByIDTrans_ChiTietNganSach(soGiaoDich.getMaGiaoDich());
        for (int i = 0; i < list.size(); i++) {
            dbHelper.delete_ChiTietNganSach(list.get(i));
        }
    }

    private void eventEdit() {
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTransactionActivity.this, EditTransactionActivity.class);
                intent.putExtra("idTrans", idTrans);
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        idTrans = getIntent().getExtras().getString("idTrans");
        soGiaoDich = dbHelper.getByID_SoGiaoDich(idTrans);
        DanhMuc danhMuc = dbHelper.getByID_DanhMuc(soGiaoDich.getMaDanhMuc());
        buttonIconCate.setImageResource(IconsDrawableModule.getResourcesDrawble(getApplicationContext(), danhMuc.getBieuTuong()));
        editTextNameCate.setText(danhMuc.getTenDanhMuc());
        editTextDate.setText(formatter.format(soGiaoDich.getNgayGiaoDich()) + " (Ngày giao dịch)");
        checkMoney(danhMuc, soGiaoDich.getSoTien());
        checkNote(soGiaoDich.getGhiChu());
        checkWallet(soGiaoDich.getMaVi());
        checkEvent(soGiaoDich.getMaSuKien());
        checkSavings(soGiaoDich.getMaTietKiem());
    }

    @SuppressLint("SetTextI18n")
    private void checkSavings(String idSavings) {
        if(idSavings.equals("null") || idSavings.equals("") || idSavings.isEmpty()) {
            editTextSavings.setTextColor(getResources().getColor(R.color.colorGray));
            editTextSavings.setText("Không có" + " (Tiết kiệm)");
        }
        else {
            try {
                TietKiem tietKiem = dbHelper.getByID_TietKiem(idSavings);
                editTextSavings.setTextColor(getResources().getColor(R.color.colorBlack));
                editTextSavings.setText(tietKiem.getTenTietKiem() + " (Tiết kiệm)");
            } catch (Exception e) {
                e.printStackTrace();
                editTextSavings.setTextColor(getResources().getColor(R.color.colorGray));
                editTextSavings.setText("Không có" + " (Tiết kiệm)");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkEvent(String idEvent) {
        if(idEvent.equals("null") || idEvent.equals("") || idEvent.isEmpty()) {
            editTextEvent.setTextColor(getResources().getColor(R.color.colorGray));
            editTextEvent.setText("Không có" + " (Sự kiện)");
        }
        else {
            try {
                SuKien suKien = dbHelper.getByID_SuKien(idEvent);
                editTextEvent.setTextColor(getResources().getColor(R.color.colorBlack));
                editTextEvent.setText(suKien.getTenSuKien() + " (Sự kiện)");
            } catch (Exception e) {
                e.printStackTrace();
                editTextEvent.setTextColor(getResources().getColor(R.color.colorGray));
                editTextEvent.setText("Không có" + " (Sự kiện)");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkWallet(String idWallet) {
        if(idWallet.equals("null") || idWallet.equals("") || idWallet.isEmpty()) {
            editTextWallet.setTextColor(getResources().getColor(R.color.colorGray));
            editTextWallet.setText("Không có" + " (Ví cá nhân)");
        }
        else {
            ViCaNhan viCaNhan = dbHelper.getByID_ViCaNhan(idWallet);
            editTextWallet.setTextColor(getResources().getColor(R.color.colorBlack));
            editTextWallet.setText(viCaNhan.getTenVi() + " (Ví cá nhân)");
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkNote(String note) {
        if(note.isEmpty() || note.equals("")) {
            editTextNote.setTextColor(getResources().getColor(R.color.colorGray));
            editTextNote.setText("Không có" + " (Ghi chú)");
        }
        else {
            editTextNote.setTextColor(getResources().getColor(R.color.colorBlack));
            editTextNote.setText(note + " (Ghi chú)");
        }
    }

    @SuppressLint("SetTextI18n")
    private void checkMoney(DanhMuc danhMuc, double money) {
        if(danhMuc.getLoaiDanhMuc().equals("doanhthu")) {
            editTextMoney.setText(FormatMoneyModule.formatAmount(money) + " VND");
        }
        else {
            editTextMoney.setText("- " + FormatMoneyModule.formatAmount(money) + " VND");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    @Override
    public void init() {
        buttonCancel = findViewById(R.id.buttonCancel_transaction_detail);
        buttonEdit = findViewById(R.id.buttonEdit_transaction_detail);
        buttonDelete = findViewById(R.id.buttonDelete_transaction_detail);
        editTextNameCate = findViewById(R.id.editTextNameCate_transaction_detail);
        editTextMoney = findViewById(R.id.editTextMoney__transaction_detail);
        editTextNote = findViewById(R.id.editTextNote_transaction_detail);
        editTextDate = findViewById(R.id.editTextDate_transaction_detail);
        editTextWallet = findViewById(R.id.editTextWallet_transaction_detail);
        editTextEvent = findViewById(R.id.editTextEvent_transaction_detail);
        editTextSavings = findViewById(R.id.editTextSavings_transaction_detail);
        buttonIconCate = findViewById(R.id.buttonIconCate_transaction_detail);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
