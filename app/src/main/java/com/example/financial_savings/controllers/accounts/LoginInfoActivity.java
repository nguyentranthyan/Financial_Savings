package com.example.financial_savings.controllers.accounts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.mains.HomeActivity;
import com.example.financial_savings.entities.SinhVien;
import com.example.financial_savings.entities.TaiKhoan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.fingerprints.BiometricUtils;
import com.example.financial_savings.modules.fingerprints.FingerprintModule;

public class LoginInfoActivity extends AppCompatActivity implements IMappingView {
    private EditText editTextPassword;
    private Button buttonLogin, buttonForgotPass, buttonExit;
    private ImageView imgPicture;
    private TextView textViewName, textViewFingerprint;
    private ImageButton buttonFingerprint;
    private DBHelper dbHelper;
    private TaiKhoan taiKhoan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login_info);
        init();
        loadData();
        eventForgotPassword();
        eventLogout();
        eventLogin();
        eventFingerprint();
    }

    private void eventFingerprint() {
        buttonFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingFingerprint();
            }
        });
        textViewFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlingFingerprint();
            }
        });
    }

    private void handlingFingerprint() {
        if(BiometricUtils.isSdkVersionSupported()) {
            if(BiometricUtils.isHardwareSupported(LoginInfoActivity.this)) {
                if(BiometricUtils.isBiometricPromptEnabled()) {
                    FingerprintModule.handlingFingerprint(LoginInfoActivity.this);
                } else Toast.makeText(getApplicationContext(), R.string.isActive_fingerprint, Toast.LENGTH_SHORT).show();
            } else Toast.makeText(getApplicationContext(), R.string.not_support, Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getApplicationContext(), R.string.not_support, Toast.LENGTH_SHORT).show();
    }

    private void eventLogin() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pasword = editTextPassword.getText().toString();
                if(CheckEmptyModule.isEmpty(pasword, pasword, pasword)) {
                    if(taiKhoan.getMatKhau().equals(pasword)) {
                        Intent intent = new Intent(LoginInfoActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else Toast.makeText(getApplicationContext(), R.string.password_incorrect, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eventLogout() {
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        taiKhoan.setStatus(0);
                        dbHelper.update_TaiKhoan(taiKhoan);
                        Intent intent = new Intent(LoginInfoActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                };
                AlertConfirmModule.alertDialogConfirm(LoginInfoActivity.this, R.string.mes_logout_info, runnable);
            }
        });
    }

    private void eventForgotPassword() {
        buttonForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginInfoActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        try {
            taiKhoan = dbHelper.getByCode_TaiKhoan(1);
            SinhVien sinhVien = dbHelper.getByID_SinhVien(taiKhoan.getEmail());
            textViewName.setText(sinhVien.getTen());
            String avatar = sinhVien.getHinhAnh();
            Uri uri = Uri.parse(avatar);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(LoginInfoActivity.this.getContentResolver(), uri);
            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,500, 500, true);
            imgPicture.setImageBitmap(bitmap2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        handlingFingerprint();
    }

    @Override
    public void init() {
        editTextPassword = findViewById(R.id.editTextPassword_login_info);
        buttonLogin = findViewById(R.id.buttonLogin_login_info);
        buttonForgotPass = findViewById(R.id.buttonForgotPass_login_info);
        buttonExit = findViewById(R.id.buttonExit_login_info);
        imgPicture = findViewById(R.id.imgPicture_login_info);
        textViewName = findViewById(R.id.textViewName_login_info);
        textViewFingerprint = findViewById(R.id.textViewFingerprint_login_info);
        buttonFingerprint = findViewById(R.id.buttonFingerprint_login_info);
        dbHelper = new DBHelper(this);
        editTextPassword.requestFocus();
        getSupportActionBar().hide();
    }
}
