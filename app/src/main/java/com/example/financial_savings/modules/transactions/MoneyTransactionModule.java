package com.example.financial_savings.modules.transactions;

import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.helper.DBHelper;

import java.util.ArrayList;

public class MoneyTransactionModule {

    public static double getMoneyTransaction(DBHelper dbHelper, ArrayList<SoGiaoDich> list) {
        double total = 0;
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

    public static double getMoneyRevenue(DBHelper dbHelper, ArrayList<SoGiaoDich> list) {
        double total = 0;
        for (int i = 0; i < list.size(); i++) {
            String idCate = list.get(i).getMaDanhMuc();
            DanhMuc danhMuc = dbHelper.getByID_DanhMuc(idCate);
            if(danhMuc.getLoaiDanhMuc().equals("doanhthu")) {
                total += list.get(i).getSoTien();
            }
        }
        return total;
    }

    public static double getMoneyExpenses(DBHelper dbHelper, ArrayList<SoGiaoDich> list) {
        double total = 0;
        for (int i = 0; i < list.size(); i++) {
            String idCate = list.get(i).getMaDanhMuc();
            DanhMuc danhMuc = dbHelper.getByID_DanhMuc(idCate);
            if(danhMuc.getLoaiDanhMuc().equals("khoanchi")) {
                total -= list.get(i).getSoTien();
            }
        }
        return Math.abs(total);
    }
}
