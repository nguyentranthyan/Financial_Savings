package com.example.financial_savings.fragments.trans_book;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import com.example.financial_savings.modules.dates.DateChooseCustomModule;
import com.example.financial_savings.modules.displays.TransactionDisplayModule;
import com.example.financial_savings.modules.formats.DateFormatModule;
import com.example.financial_savings.modules.formats.FormatMoneyModule;
import com.example.financial_savings.modules.others.GetYearMonthChooseModule;
import com.example.financial_savings.modules.transactions.MoneyTransactionModule;
import com.example.financial_savings.modules.wallets.MoneyWalletModule;
import com.example.financial_savings.sessions.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class Trans_Book_Fragment extends Fragment implements IMappingView {
    private ImageButton buttonIconWallet, buttonIconDown, buttonIconFilter, buttonIconTime;
    private TextView textViewNameWallet, textViewMoneyWallet, textViewMoneyRevenue,
            textViewMoneyExpenses, textViewMoneyTotal, textViewTime;
    private ListView listView;
    private View root;
    private DBHelper dbHelper;
    private Session session;
    private static final String WALLET_TOTAL = "total";
    private static final String DOANHTHU = "doanhthu";
    private static final String KHOANCHI = "khoanchi";
    private String[] FILTER_ARRAY;
    private String[] TIME_ARRAY;
    private String[] YEAR_ARRAY;
    private String[] MONTH_ARRAY;
    private ArrayList<SoGiaoDich> soGiaoDichs;
    private String timeStart;
    private String timeEnd;
    private int year = 0;
    private int month = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.transaction_home, container, false);
        init();
        if(getUserVisibleHint()) loadData();
        loadData();
        eventChooseWallet();
        eventFilter();
        eventChooseTime();
        return root;
    }

    private void eventChooseTime() {
        buttonIconTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setTitle(getResources().getString(R.string.times));
                dialog.setContentView(R.layout.transaction_time_dialog);
                ListView listViewChooseTime = dialog.findViewById(R.id.listViewChooseTime_time_dialog);
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, TIME_ARRAY);
                listViewChooseTime.setAdapter(adapter);
                listViewChooseTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        handlingTime(position);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void handlingTime(int position) {
        if(position == 1) {
            handlingChooseYear(0);
        }
        else if(position == 2) {
            handlingChooseMonth();
        }
        else if(position == 3) {
            eventChooseDate();
        }
        else if(position == 4) {
            Runnable runnable = new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    DateChooseCustomModule.dialog.dismiss();
                    timeStart = session.getTimeStart();
                    timeEnd = session.getTimeEnd();
                    year = 0;
                    textViewTime.setText(timeStart + " - " + timeEnd);
                    handlingDate(soGiaoDichs, 4);
                }
            };
            DateChooseCustomModule.handlingChooseCustom(getActivity(), runnable, false);
        }
        else if(position == 0) {
            textViewTime.setText("");
            clearTime();
            handlingDate(soGiaoDichs, 0);
        }
    }

    private void handlingChooseMonth() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(getResources().getString(R.string.times));
        dialog.setContentView(R.layout.report_list_month_dialog);
        ListView listViewChooseTime = dialog.findViewById(R.id.listViewYear_report_list_month);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, MONTH_ARRAY);
        listViewChooseTime.setAdapter(adapter);
        listViewChooseTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                int month = Integer.parseInt(name.trim());
                handlingChooseYear(month);
                dialog.dismiss();
            }
        });
    }

    private void handlingChooseYear(final int monthrs) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle(getResources().getString(R.string.times));
        dialog.setContentView(R.layout.report_list_year_dialog);
        final ListView listViewChooseTime = dialog.findViewById(R.id.listViewYear_report_list_year);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, YEAR_ARRAY);
        listViewChooseTime.setAdapter(adapter);
        listViewChooseTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String) parent.getItemAtPosition(position);
                year = Integer.parseInt(name.trim());
                clearDate();
                if(monthrs == 0) {
                    textViewTime.setText(String.valueOf(year));
                    handlingDate(soGiaoDichs, 1);
                }
                else {
                    month = monthrs;
                    textViewTime.setText(month + "/" + year);
                    handlingDate(soGiaoDichs, 2);
                }
                dialog.dismiss();
            }
        });
    }

    private void eventChooseDate() {
        Calendar c = Calendar.getInstance();
        int nYear = c.get(Calendar.YEAR);
        int nMonth = c.get(Calendar.MONTH);
        int nDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                textViewTime.setText(date);
                clearTime();
                handlingDate(soGiaoDichs, 3);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                root.getContext(), callback, nYear, nMonth, nDay);
        datePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void handlingDate(ArrayList<SoGiaoDich> soGiaoDichs, int position) {
        ArrayList<SoGiaoDich> list = new ArrayList<>();
        String dateStr = textViewTime.getText().toString();
        if(!dateStr.isEmpty() && !dateStr.equals("") && dateStr != null) {
            if(position == 1) {
                for (int i = 0; i < soGiaoDichs.size(); i++) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(soGiaoDichs.get(i).getNgayGiaoDich());
                    if(calendar.get(Calendar.YEAR) == year) {
                        list.add(soGiaoDichs.get(i));
                    }
                }
            }
            else if(position == 2) {
                for (int i = 0; i < soGiaoDichs.size(); i++) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(soGiaoDichs.get(i).getNgayGiaoDich());
                    int rs = calendar.get(Calendar.MONTH);
                    if(calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) + 1 == month) {
                        list.add(soGiaoDichs.get(i));
                    }
                }
            }
            else if(position == 3) {
                java.sql.Date sqlDate = DateFormatModule.getDateSQL(dateStr);
                for (int i = 0; i < soGiaoDichs.size(); i++) {
                    if(soGiaoDichs.get(i).getNgayGiaoDich().equals(sqlDate)) {
                        list.add(soGiaoDichs.get(i));
                    }
                }
            }
            else if(position == 4) {
                java.sql.Date sqlDateStart = DateFormatModule.getDateSQL(timeStart);
                java.sql.Date sqlDateEnd = DateFormatModule.getDateSQL(timeEnd);
                for (int i = 0; i < soGiaoDichs.size(); i++) {
                    if(soGiaoDichs.get(i).getNgayGiaoDich().equals(sqlDateStart)
                            || soGiaoDichs.get(i).getNgayGiaoDich().equals(sqlDateEnd)
                            || soGiaoDichs.get(i).getNgayGiaoDich().after(sqlDateStart)
                            && soGiaoDichs.get(i).getNgayGiaoDich().before(sqlDateEnd)) {
                        list.add(soGiaoDichs.get(i));
                    }
                }
            }
        }
        else {
            list.addAll(soGiaoDichs);
        }
        displayTrans_Book(list);
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
        checkIsEmptyDate(list);
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
        checkIsEmptyDate(soGiaoDichs);
    }

    private void checkIsEmptyDate(ArrayList<SoGiaoDich> list) {
        if(textViewTime.getText().toString().isEmpty()) {
            displayTrans_Book(list);
        }
        else if(timeStart != null && !timeStart.equals("") && !timeStart.isEmpty()){
            handlingDate(list, 4);
        }
        else if(year != 0) {
            handlingDate(list, 1);
        }
        else if(year != 0 && month != 0) {
            handlingDate(list, 2);
        }
        else {
            handlingDate(list, 3);
        }
    }

    private void clearDate() {
        session.clearTimeStart();
        session.clearTimeEnd();
        timeStart = "";
        timeEnd = "";
    }

    private void clearTime() {
        session.clearTimeStart();
        session.clearTimeEnd();
        timeStart = "";
        timeEnd = "";
        year = 0;
        month = 0;
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

    @SuppressLint("SetTextI18n")
    private void displayTrans_Book(ArrayList<SoGiaoDich> list) {
        double total = MoneyTransactionModule.getMoneyTransaction(dbHelper, list);
        double revenue = MoneyTransactionModule.getMoneyRevenue(dbHelper, list);
        double expense = MoneyTransactionModule.getMoneyExpenses(dbHelper, list);
        textViewMoneyRevenue.setText(FormatMoneyModule.formatAmount(revenue) + " VND");
        textViewMoneyExpenses.setText(FormatMoneyModule.formatAmount(expense) + " VND");
        textViewMoneyTotal.setText(FormatMoneyModule.formatAmount(total) + " VND");
        TransactionDisplayModule.showListViewHome_Transaction(list, getActivity(), listView, dbHelper);
    }

    private void sortList(ArrayList<SoGiaoDich> list) {
        Collections.sort(list, new Comparator<SoGiaoDich>() {
            @Override
            public int compare(SoGiaoDich o1, SoGiaoDich o2) {
                return o2.getNgayGiaoDich().compareTo(o1.getNgayGiaoDich());
            }
        });
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
        buttonIconWallet = root.findViewById(R.id.buttonIconWallet_transaction_home);
        buttonIconDown = root.findViewById(R.id.buttonIconDown_transaction_home);
        buttonIconFilter = root.findViewById(R.id.buttonIconFilter_transaction_home);
        buttonIconTime = root.findViewById(R.id.buttonIconTime_transaction_home);
        textViewNameWallet = root.findViewById(R.id.textViewNameWallet_transaction_home);
        textViewMoneyWallet = root.findViewById(R.id.textViewMoneyWallet_transaction_home);
        textViewMoneyRevenue = root.findViewById(R.id.textViewMoneyRevenue_transaction_home);
        textViewMoneyExpenses = root.findViewById(R.id.textViewMoneyExpenses_transaction_home);
        textViewMoneyTotal = root.findViewById(R.id.textViewMoneyTotal_transaction_home);
        textViewTime = root.findViewById(R.id.textViewTime_transaction_home);
        listView = root.findViewById(R.id.listView_transaction_home);
        dbHelper = new DBHelper(root.getContext());
        session = new Session(root.getContext());
        session.clear();
        FILTER_ARRAY = getResources().getStringArray(R.array.filter_array);
        TIME_ARRAY = getResources().getStringArray(R.array.time_array);
        YEAR_ARRAY = (GetYearMonthChooseModule.getArrayYear().toArray(new String[0]));
        MONTH_ARRAY = GetYearMonthChooseModule.getArrayMonth().toArray(new String[0]);
    }
}
