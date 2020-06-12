package com.example.financial_savings.controllers.accounts;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.SinhVien;
import com.example.financial_savings.entities.TaiKhoan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.checks.CheckRegexModule;

public class EditInfoActivity extends AppCompatActivity implements IMappingView {
    private EditText editTextEmail, editTextName;
    private ImageButton imageButtonChoose, buttonReturn;
    private ImageView imageViewPicture;
    private Button buttonSave;
    private static final int CHOOSEN = 1;
    private DBHelper dbHelper;
    private SinhVien sinhVien;
    private Uri avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_edit_info);
        init();
        loadData();
        eventReturn();
        eventOpenGallery();
        eventSave();
    }

    private void eventSave() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = editTextName.getText().toString();
                if(CheckEmptyModule.isEmpty(name, name, name)) {
                    if(CheckRegexModule.isName(name)) {
                        sinhVien.setTen(name);
                        sinhVien.setHinhAnh(getRealPathFromURI(avatar));
                        if(dbHelper.update_SinhVien(sinhVien)) {
                            onBackPressed();
                            Toast.makeText(getApplicationContext(), R.string.success_edit_info, Toast.LENGTH_SHORT).show();
                        } else Toast.makeText(getApplicationContext(), R.string.unsuccess_edit_info, Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(getApplicationContext(), R.string.regex_name, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eventOpenGallery() {
        imageButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, CHOOSEN);
            }
        });
    }

    private void eventReturn() {
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private String getRealPathFromURI(Uri contentUri) {
        if(contentUri != null) {
            String [] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return "none";
    }

    private void loadData() {
        TaiKhoan taiKhoan = dbHelper.getByCode_TaiKhoan(1);
        sinhVien = dbHelper.getByID_SinhVien(taiKhoan.getEmail());
        editTextEmail.setText(sinhVien.getEmail());
        editTextName.setText(sinhVien.getTen());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSEN && resultCode == RESULT_OK && data != null) {
            try {
                avatar = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), avatar);
                Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,600, 600, true);
                imageViewPicture.setImageBitmap(bitmap2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init() {
        editTextEmail = findViewById(R.id.editTextEmail_edit_info);
        editTextName = findViewById(R.id.editTextName_edit_info);
        imageButtonChoose = findViewById(R.id.imageButtonChoose_edit_info);
        imageViewPicture = findViewById(R.id.imageViewPicture_edit_info);
        buttonSave = findViewById(R.id.buttonSave_edit_info);
        buttonReturn = findViewById(R.id.buttonReturn_edit_info);
        dbHelper = new DBHelper(this);
        editTextName.requestFocus();
        getSupportActionBar().hide();
    }
}
