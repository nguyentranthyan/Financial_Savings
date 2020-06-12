package com.example.financial_savings.controllers.mains;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.accounts.LoginActivity;
import com.example.financial_savings.controllers.accounts.LoginInfoActivity;
import com.example.financial_savings.helper.DBHelper;

public class MainActivity extends FragmentActivity {
    private static final int TIME_SLEEP = 1000;
    private Handler handler;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.view_splashscreen);

        dbHelper = new DBHelper(this);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    dbHelper.getByCode_TaiKhoan(1);
                    Intent intent = new Intent(MainActivity.this, LoginInfoActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        Runnable background = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(TIME_SLEEP);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                handler.post(runnable);
            }
        };
        new Thread(background).start();
    }
}
