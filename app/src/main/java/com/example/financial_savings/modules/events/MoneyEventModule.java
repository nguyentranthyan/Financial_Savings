package com.example.financial_savings.modules.events;

import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.helper.DBHelper;

import java.util.ArrayList;

public class MoneyEventModule {

    public static double getMoneyEvent(DBHelper dbHelper, SuKien suKien) {
        double total = 0;
        ArrayList<SoGiaoDich> list = dbHelper.getByEvent_SoGiaoDich(suKien.getMaSuKien());
        for (int i = 0; i < list.size(); i++) {
            String idCate = list.get(i).getMaDanhMuc();
            DanhMuc danhMuc = dbHelper.getByID_DanhMuc(idCate);
            if(danhMuc.getLoaiDanhMuc().equals("doanhthu")) {
                total += list.get(i).getSoTien();
            }
            else {
                total -= list.get(i).getSoTien();
            }
        }
        return total;
    }
}
