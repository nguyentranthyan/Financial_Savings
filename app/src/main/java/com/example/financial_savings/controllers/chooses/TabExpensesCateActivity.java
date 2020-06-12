package com.example.financial_savings.controllers.chooses;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.CategoryDisplayModule;

import java.util.ArrayList;

public class TabExpensesCateActivity extends AppCompatActivity implements IMappingView {
    private ListView listViewCategory;
    private DBHelper dbHelper;
    private static final String DOANHTHU = "doanhthu";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_tab_expenses);
        init();
        loadData();
    }

    private void loadData() {
        ArrayList<DanhMuc> list = dbHelper.getByCate_DanhMuc(DOANHTHU);
        CategoryDisplayModule.showListViewChoose_Category(list, getApplicationContext(), listViewCategory);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void init() {
        listViewCategory = findViewById(R.id.listView_catetory_tab_expenses);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
