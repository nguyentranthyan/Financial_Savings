package com.example.financial_savings.modules.savings;

import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.helper.DBHelper;

import java.util.ArrayList;

public class MoneySavingsModule {

    public static double getMoneySavings(DBHelper dbHelper, TietKiem tietKiem) {
        double total = 0;
        ArrayList<SoGiaoDich> list = dbHelper.getBySavings_SoGiaoDich(tietKiem.getMaTietKiem());
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

    public static double getAverageMoney(DBHelper dbHelper, TietKiem tietKiem) {
        ArrayList<SoGiaoDich> list = dbHelper.getBySavings_SoGiaoDich(tietKiem.getMaTietKiem());
        int count = list.size();
        double total = getMoneySavings(dbHelper, tietKiem);
        return total / count;
    }
}
