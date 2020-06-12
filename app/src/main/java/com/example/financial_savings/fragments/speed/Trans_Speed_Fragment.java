package com.example.financial_savings.fragments.speed;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.financial_savings.R;
import com.example.financial_savings.controllers.chooses.ChooseWalletActivity;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.ViCaNhan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.TransactionFastDisplayModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.transactions.TransactionFastModule;
import com.example.financial_savings.modules.wallets.MoneyWalletModule;
import com.example.financial_savings.sessions.Session;

import java.util.ArrayList;

public class Trans_Speed_Fragment extends Fragment implements IMappingView {
    private ImageButton buttonIconWallet, buttonIconDown, buttonIconFilter;
    private TextView textViewNameWallet, textViewMoneyWallet;
    private ListView listView;
    private View root;
    private DBHelper dbHelper;
    private Session session;
    private static final String WALLET_TOTAL = "total";
    private static final String DOANHTHU = "doanhthu";
    private static final String KHOANCHI = "khoanchi";
    private String[] FILTER_ARRAY;
    private ArrayList<SoGiaoDich> soGiaoDichs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.transaction_fast, container, false);
        init();
        if(getUserVisibleHint()) loadData();
        loadData();
        eventChooseWallet();
        eventFilter();
        return root;
    }

    private void eventFilter() {
        buttonIconFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setTitle(getResources().getString(R.string.filter_dialog));
                dialog.setContentView(R.layout.transaction_filter_dialog);
                ListView listViewFilter = dialog.findViewById(R.id.listViewFilter_filter_dialog);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, FILTER_ARRAY);
                listViewFilter.setAdapter(adapter);
                listViewFilter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        handlingFilter(position);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void handlingFilter(int position) {
        ArrayList<SoGiaoDich> list = new ArrayList<>();
        for (int i = 0; i < soGiaoDichs.size(); i++) {
            String idCate = soGiaoDichs.get(i).getMaDanhMuc();
            DanhMuc danhMuc = dbHelper.getByID_DanhMuc(idCate);
            if(position == 1 && danhMuc.getLoaiDanhMuc().equals(DOANHTHU)) {
                list.add(soGiaoDichs.get(i));
            }
            else if(position == 2 && danhMuc.getLoaiDanhMuc().equals(KHOANCHI)) {
                list.add(soGiaoDichs.get(i));
            }
            else if(position == 0) {
                list.add(soGiaoDichs.get(i));
            }
        }
        TransactionFastDisplayModule.showListViewHomeFast_Transaction(TransactionFastModule.getSoGiaoDichesStatus(list),
                getActivity(), listView, dbHelper);
    }

    private void eventChooseWallet() {
        buttonIconWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseWalletActivity.class);
                intent.putExtra("name", WALLET_TOTAL);
                startActivity(intent);
            }
        });
        buttonIconDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseWalletActivity.class);
                intent.putExtra("name", WALLET_TOTAL);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        String idWallet = session.getIDWallet();
        if(idWallet.equals(WALLET_TOTAL) || idWallet == null || idWallet.isEmpty()) {
            soGiaoDichs = dbHelper.getAll_SoGiaoDich();
            getListWalletTotal();
        }
        else {
            soGiaoDichs = dbHelper.getByWallet_SoGiaoDich(idWallet);
            getListIDWallet(idWallet);
        }
        TransactionFastDisplayModule.showListViewHomeFast_Transaction(TransactionFastModule.getSoGiaoDichesStatus(soGiaoDichs),
                getActivity(), listView, dbHelper);
    }

    @SuppressLint("SetTextI18n")
    private void getListWalletTotal() {
        ArrayList<ViCaNhan> wallets = dbHelper.getAll_ViCaNhan();
        double totalWallet = MoneyWalletModule.getMoneyWallet(wallets);
        textViewNameWallet.setText(getResources().getString(R.string.all_wallet));
        textViewMoneyWallet.setText(FormatMoneyModule.formatAmount(totalWallet) + " VND");
        buttonIconWallet.setBackgroundResource(R.drawable.ic_language_black_24dp);
    }

    @SuppressLint("SetTextI18n")
    private void getListIDWallet(String idWallet) {
        ViCaNhan viCaNhan = dbHelper.getByID_ViCaNhan(idWallet);
        textViewNameWallet.setText(viCaNhan.getTenVi());
        textViewMoneyWallet.setText(FormatMoneyModule.formatAmount(viCaNhan.getSoTien()) + " VND");
        buttonIconWallet.setBackgroundResource(R.drawable.ic_account_balance_wallet_black_24dp);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed()) {
            loadData();
        }
    }

    @Override
    public void init() {
        buttonIconWallet = root.findViewById(R.id.buttonIconWallet_transaction_fast);
        buttonIconDown = root.findViewById(R.id.buttonIconDown_transaction_fast);
        buttonIconFilter = root.findViewById(R.id.buttonIconFilter_transaction_fast);
        textViewNameWallet = root.findViewById(R.id.textViewNameWallet_transaction_fast);
        textViewMoneyWallet = root.findViewById(R.id.textViewMoneyWallet_transaction_fast);
        listView = root.findViewById(R.id.listView_transaction_fast);
        dbHelper = new DBHelper(root.getContext());
        session = new Session(root.getContext());
        session.clear();
        FILTER_ARRAY = getResources().getStringArray(R.array.filter_array);
    }
}