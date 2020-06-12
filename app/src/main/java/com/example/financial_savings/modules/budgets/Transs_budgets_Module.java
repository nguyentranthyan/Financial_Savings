package com.example.financial_savings.modules.budgets;

import com.example.financial_savings.entities.ChiTietNganSach;
import com.example.financial_savings.entities.NganSach;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.helper.DBHelper;

import java.util.ArrayList;

public class Transs_budgets_Module {

    public static ArrayList<SoGiaoDich> gettranssBudget(DBHelper dbHelper, NganSach nganSach) {
      ArrayList<SoGiaoDich>listtrans=new ArrayList<>();
        ArrayList<ChiTietNganSach> list = dbHelper.getByIDBudget_ChiTietNganSach(nganSach.getMaNganSach());
        for (int i = 0; i < list.size(); i++) {
            SoGiaoDich sogiaodich = dbHelper.getByID_SoGiaoDich(list.get(i).getMaGiaoDich());
          listtrans.add(sogiaodich);
        }
        return listtrans;
    }
}