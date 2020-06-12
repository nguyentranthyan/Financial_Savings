package com.example.financial_savings.modules.wallets;

import com.example.financial_savings.entities.ViCaNhan;

import java.util.ArrayList;

public class MoneyWalletModule {

    public static double getMoneyWallet(ArrayList<ViCaNhan> list) {
        double total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).getSoTien();
        }
        return total;
    }
}
