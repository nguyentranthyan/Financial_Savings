package com.example.financial_savings.modules.displays;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.categories.EditCateActivity;
import com.example.financial_savings.controllers.categories.HomeCateActivity;
import com.example.financial_savings.controllers.chooses.TabHostCateActivity;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.modules.icons.IconsDrawableModule;
import com.example.financial_savings.sessions.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryDisplayModule {
    private static Session session;

    public static void showListViewHome_Category(final ArrayList<DanhMuc> list, final Context context, ListView listView) {
        List<HashMap<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("listView_name", list.get(i).getTenDanhMuc());
            hashMap.put("image", String.valueOf(IconsDrawableModule.getResourcesDrawble(context,
                    list.get(i).getBieuTuong())));
            mapList.add(hashMap);
        }

        String[] from = {"image", "listView_name"};
        int[] to = {R.id.imageView_Cate, R.id.textView_Name_Cate};

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.category_list_item_layout, from, to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idCategory = list.get(position).getMaDanhMuc();
                Intent intent = new Intent(context.getApplicationContext(), EditCateActivity.class);
                intent.putExtra("idCategory", idCategory);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.getApplicationContext().startActivity(intent);
                HomeCateActivity.activity.onBackPressed();
            }
        });
    }

    public static void showListViewChoose_Category(final ArrayList<DanhMuc> list, final Context context, ListView listView) {
        session = new Session(context);
        List<HashMap<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("listView_name", list.get(i).getTenDanhMuc());
            hashMap.put("image", String.valueOf(IconsDrawableModule.getResourcesDrawble(context,
                    list.get(i).getBieuTuong())));
            mapList.add(hashMap);
        }

        String[] from = {"image", "listView_name"};
        int[] to = {R.id.imageView_Cate, R.id.textView_Name_Cate};

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.category_list_item_layout, from, to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                session.clearCate();
                String idCate = list.get(position).getMaDanhMuc();
                session.setIDCate(idCate);
                TabHostCateActivity.activity.onBackPressed();
            }
        });
    }
}
