package com.example.financial_savings.modules.displays;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.transactions.DetailTransactionActivity;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.icons.IconsDrawableModule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionDisplayModule {

    public static void showListViewHome_Transaction(final ArrayList<SoGiaoDich> list, final Context context,
                                                    ListView listView, DBHelper dbHelper) {
        List<HashMap<String, String>> mapList = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < list.size(); i++) {
            DanhMuc danhMuc = dbHelper.getByID_DanhMuc(list.get(i).getMaDanhMuc());
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("listView_name", danhMuc.getTenDanhMuc());
            hashMap.put("listView_note", list.get(i).getGhiChu());
            hashMap.put("listView_date", formatter.format(list.get(i).getNgayGiaoDich()));
            if(danhMuc.getLoaiDanhMuc().equals("doanhthu")) {
                hashMap.put("listView_money", "+" + FormatMoneyModule.formatAmount(list.get(i).getSoTien()) + " VND");
            }
            else {
                hashMap.put("listView_money", "-" + FormatMoneyModule.formatAmount(list.get(i).getSoTien()) + " VND");
            }
            hashMap.put("image", String.valueOf(IconsDrawableModule.getResourcesDrawble(context, danhMuc.getBieuTuong())));
            mapList.add(hashMap);
        }

        String[] from = {"image", "listView_name", "listView_note", "listView_date", "listView_money"};
        int[] to = {R.id.imageView_Trans_book, R.id.textViewNameCate_Trans_book, R.id.textViewNote_Trans_book,
                R.id.textViewDate_Trans_book, R.id.textViewMoney_Trans_book};

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.transaction_list_item_layout, from, to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idTrans = list.get(position).getMaGiaoDich();
                Intent intent = new Intent(context.getApplicationContext(), DetailTransactionActivity.class);
                intent.putExtra("idTrans", idTrans);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
//
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                String idTrans = list.get(position).getMaGiaoDich();
//                Log.d(null, idTrans);
//                Bundle bundle = new Bundle();
//                bundle.putString("idTrans", idTrans);
//                Fragment fragment = new Add_Trans_Fragment();
//                fragment.setArguments(bundle);
//                return true;
//            }
//        });
    }
}
