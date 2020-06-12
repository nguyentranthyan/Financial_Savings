package com.example.financial_savings.modules.displays;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.chooses.ChooseWalletActivity;
import com.example.financial_savings.controllers.wallets.EditWalletActivity;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.wallets.MoneyWalletModule;
import com.example.financial_savings.sessions.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WalletDisplayModule {
    private static Session session;
    private static final String WALLET_FROM = "from";
    private static final String WALLET_TOTAL = "total";

    public static void showListViewHome_Wallet(final ArrayList<ViCaNhan> list, final Context context, ListView listView) {
        List<HashMap<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("listView_name", list.get(i).getTenVi());
            hashMap.put("listView_money", FormatMoneyModule.formatAmount(list.get(i).getSoTien()) + " VND");
            hashMap.put("image", String.valueOf(R.drawable.ic_account_balance_wallet_black_24dp));
            mapList.add(hashMap);
        }

        String[] from = {"image", "listView_name", "listView_money"};
        int[] to = {R.id.imageView_wallet, R.id.textView_Name_Wallet, R.id.textView_Money_Wallet};

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.wallet_list_item_layout, from, to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idWallet = list.get(position).getMaVi();
                Intent intent = new Intent(context.getApplicationContext(), EditWalletActivity.class);
                intent.putExtra("idWallet", idWallet);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
    }

    public static void showListViewChoose_Wallet(final ArrayList<ViCaNhan> list, final Context context, ListView listView, final String name) {
        session = new Session(context);
        double totalWallet = MoneyWalletModule.getMoneyWallet(list);
        List<HashMap<String, String>> mapList = new ArrayList<>();

        if(name.equals(WALLET_TOTAL)) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("listView_name", context.getResources().getString(R.string.all_wallet));
            hashMap.put("listView_money", FormatMoneyModule.formatAmount(totalWallet) + " VND");
            hashMap.put("image", String.valueOf(R.drawable.ic_language_black_24dp));
            mapList.add(hashMap);
        }

        for (int i = 0; i < list.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("listView_name", list.get(i).getTenVi());
            hashMap.put("listView_money", FormatMoneyModule.formatAmount(list.get(i).getSoTien()) + " VND");
            hashMap.put("image", String.valueOf(R.drawable.ic_account_balance_wallet_black_24dp));
            mapList.add(hashMap);
        }

        String[] from = {"image", "listView_name", "listView_money"};
        int[] to = {R.id.imageView_wallet, R.id.textView_Name_Wallet, R.id.textView_Money_Wallet};

        SimpleAdapter simpleAdapter = new SimpleAdapter(context, mapList, R.layout.wallet_list_item_layout, from, to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(name.equals(WALLET_TOTAL)) {
                    if(position == 0) {
                        session.clearWallet();
                        session.setIDWallet("total");
                    }
                    else {
                        String idWallet = list.get(position - 1).getMaVi();
                        session.clearWallet();
                        session.setIDWallet(idWallet);
                    }
                }
                else {
                    String idWallet = list.get(position).getMaVi();
                    if(name.equals(WALLET_FROM)) {
                        session.clearWallet();
                        session.clearWalletReceive();
                        session.setIDWallet(idWallet);
                    }
                    else {
                        session.clearWalletReceive();
                        session.setIDWalletReceive(idWallet);
                    }
                }
                ChooseWalletActivity.activity.onBackPressed();
            }
        });
    }
}
