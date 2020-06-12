package com.example.financial_savings.controllers.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.chooses.ChooseWalletActivity;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.others.CategoryNameModule;
import com.example.financial_savings.modules.icons.IconsDrawableModule;
import com.example.financial_savings.sessions.Session;

public class EditCateActivity extends AppCompatActivity implements IMappingView {
    private Button buttonSave, buttonDeleteCate;
    private ImageButton buttonIconCate, buttonCancel, buttonDeleteIcon;
    private EditText editTextNameCate, editTextWallet;
    private RadioGroup radioGroupCate;
    private RadioButton radioButton, radioButtonRev, radioButtonExp;
    private DBHelper dbHelper;
    private String icon;
    private DanhMuc danhMuc;
    private static final String DOANHTHU = "doanhthu";
    private Session session;
    private static final String WALLET_FROM = "from";
    private ViCaNhan viCaNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_edit);
        init();
        loadData();
        eventReturn();
        eventSave();
        eventChooseWallet();
        eventIconCate();
        eventDelete();
    }

    private void eventChooseWallet() {
        editTextWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCateActivity.this, ChooseWalletActivity.class);
                intent.putExtra("name", WALLET_FROM);
                startActivity(intent);
            }
        });
    }

    private void eventDelete() {
        buttonDeleteCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCate();
            }
        });
        buttonDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCate();
            }
        });
    }

    private void deleteCate() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dbHelper.delete_DanhMuc(danhMuc);
                Toast.makeText(getApplicationContext(), R.string.delete_category_success, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        };
        AlertConfirmModule.alertDialogConfirm(EditCateActivity.this, R.string.mes_delete_category, runnable);
    }

    private void eventIconCate() {
        buttonIconCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCateActivity.this, CateIconsActivity.class);
                startActivityForResult(intent, 999);
            }
        });
    }

    private void eventSave() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextNameCate.getText().toString();
                int select = radioGroupCate.getCheckedRadioButtonId();
                radioButton = findViewById(select);
                if(CheckEmptyModule.isEmpty(name, name, name)) {
                    if(icon != null) {
                        if(danhMuc.getTenDanhMuc().equals(name)) {
                            handlingSave(name);
                        } else {
                            try {
                                dbHelper.getByName_DanhMuc(name);
                                Toast.makeText(getApplicationContext(), R.string.name_exist_cate_add, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                handlingSave(name);
                            }
                        }
                    } else Toast.makeText(getApplicationContext(), R.string.icon_empty_cate_add, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingSave(final String name) {
        danhMuc.setTenDanhMuc(name);
        danhMuc.setBieuTuong(icon);
        danhMuc.setLoaiDanhMuc(CategoryNameModule.getLabelByText(radioButton.getText().toString()));
        danhMuc.setMaVi(viCaNhan.getMaVi());
        try {
            dbHelper.update_DanhMuc(danhMuc);
            onBackPressed();
            Toast.makeText(getApplicationContext(), R.string.success_category_edit, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.unsuccess_category_edit, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        String idCategory = getIntent().getExtras().getString("idCategory");
        danhMuc = dbHelper.getByID_DanhMuc(idCategory);
        editTextNameCate.setText(danhMuc.getTenDanhMuc());
        icon = danhMuc.getBieuTuong();
        buttonIconCate.setImageResource(IconsDrawableModule.getResourcesDrawble(getApplicationContext(), danhMuc.getBieuTuong()));
        String cate = danhMuc.getLoaiDanhMuc();
        if(cate.equals(DOANHTHU)) {
            radioButtonRev.setChecked(true);
        }
        else {
            radioButtonExp.setChecked(true);
        }
        loadSessionWallet();
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
            viCaNhan = dbHelper.getByID_ViCaNhan(danhMuc.getMaVi());
            editTextWallet.setText(viCaNhan.getTenVi());
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
        Intent intent = new Intent(EditCateActivity.this, HomeCateActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadSessionWallet();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999 && resultCode == RESULT_OK) {
            icon = data.getStringExtra("icon");
            int resID = IconsDrawableModule.getResourcesDrawble(getApplicationContext(), icon);
            buttonIconCate.setImageResource(resID);
        }
    }

    @Override
    public void init() {
        buttonCancel = findViewById(R.id.buttonCancel_category_edit);
        buttonSave = findViewById(R.id.buttonSave_category_edit);
        buttonIconCate = findViewById(R.id.buttonIconCate_category_edit);
        buttonDeleteCate = findViewById(R.id.buttonDeleteCategory_cate_edit);
        buttonDeleteIcon = findViewById(R.id.buttonDeleteIcon_cate_edit);
        editTextNameCate = findViewById(R.id.editTextNameCate_category_edit);
        editTextWallet = findViewById(R.id.editTextWallet_category_edit);
        radioGroupCate = findViewById(R.id.radioGroupCate_category_edit);
        radioButtonRev = findViewById(R.id.radioButtonRev_category_edit);
        radioButtonExp = findViewById(R.id.radioButtonExp_category_edit);
        dbHelper = new DBHelper(this);
        session = new Session(EditCateActivity.this);
        session.clear();
        editTextNameCate.requestFocus();
        getSupportActionBar().hide();
    }
}
