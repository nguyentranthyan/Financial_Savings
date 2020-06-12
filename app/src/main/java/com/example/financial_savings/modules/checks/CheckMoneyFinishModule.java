package com.example.financial_savings.modules.checks;

import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.modules.savings.MoneySavingsModule;

public class CheckMoneyFinishModule {

    public static boolean isFinish(DBHelper dbHelper, TietKiem tietKiem) {
        double total = MoneySavingsModule.getMoneySavings(dbHelper, tietKiem);
        return total >= tietKiem.getSoTien();
    }
}
