package com.example.financial_savings.modules.randoms;

import com.example.financial_savings.entities.NganSach;
import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.helper.DBHelper;

import java.util.ArrayList;
import java.util.Collections;

public class RandomIDModule {

    public static long getRandomID() {
        return (long) Math.floor(Math.random() * 900000000000L) + 100000000000L;
    }

    // Vi ca nhan
    public static String getWalletID(DBHelper dbHelper) {
        String id;
        do {
            id = String.valueOf(RandomIDModule.getRandomID());
        } while (!isExistWalletID(id, dbHelper));
        return id;
    }

    private static boolean isExistWalletID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_ViCaNhan(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    // Danh muc
    public static String getCategoryID(DBHelper dbHelper) {
        String id;
        do {
            id = String.valueOf(RandomIDModule.getRandomID());
        } while (!isExistCategoryID(id, dbHelper));
        return id;
    }

    private static boolean isExistCategoryID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_DanhMuc(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    // So giao dich
    public static String getTransID(DBHelper dbHelper) {
        String id;
        do {
            ArrayList<Integer> list = getListIDTrans(dbHelper);
            if(list.isEmpty()) {
                id = "1000000000";
            }
            else {
                id = String.valueOf(Collections.max(list) + 1);
            }
        } while (!isExistTransID(id, dbHelper));
        return id;
    }

    private static boolean isExistTransID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_SoGiaoDich(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private static ArrayList<Integer> getListIDTrans(DBHelper dbHelper) {
        ArrayList<SoGiaoDich> all = dbHelper.getAll_SoGiaoDich();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            list.add(Integer.parseInt(all.get(i).getMaGiaoDich()));
        }
        return list;
    }

    // Su kien
    public static String getEventID(DBHelper dbHelper) {
        String id;
        do {
            ArrayList<Integer> list = getListIDEvent(dbHelper);
            if(list.isEmpty()) {
                id = "1000000000";
            }
            else {
                id = String.valueOf(Collections.max(list) + 1);
            }
        } while (!isExistEventID(id, dbHelper));
        return id;
    }

    private static boolean isExistEventID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_SuKien(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private static ArrayList<Integer> getListIDEvent(DBHelper dbHelper) {
        ArrayList<SuKien> all = dbHelper.getAll_SuKien();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            list.add(Integer.parseInt(all.get(i).getMaSuKien()));
        }
        return list;
    }

    // Tiet kiem
    public static String getSavingsID(DBHelper dbHelper) {
        String id;
        do {
            ArrayList<Integer> list = getListIDSavings(dbHelper);
            if(list.isEmpty()) {
                id = "1000000000";
            }
            else {
                id = String.valueOf(Collections.max(list) + 1);
            }
        } while (!isExistSavingsID(id, dbHelper));
        return id;
    }

    private static boolean isExistSavingsID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_TietKiem(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private static ArrayList<Integer> getListIDSavings(DBHelper dbHelper) {
        ArrayList<TietKiem> all = dbHelper.getAll_TietKiem();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            list.add(Integer.parseInt(all.get(i).getMaTietKiem()));
        }
        return list;
    }

    // Ngan sach
    public static String getBudgetsID(DBHelper dbHelper) {
        String id;
        do {
            ArrayList<Integer> list = getListIDBudget(dbHelper);
            if(list.isEmpty()) {
                id = "1000000000";
            }
            else {
                id = String.valueOf(Collections.max(list) + 1);
            }
        } while (!isExistBudgetsID(id, dbHelper));
        return id;
    }

    private static boolean isExistBudgetsID(String id, DBHelper dbHelper) {
        try {
            dbHelper.getByID_NganSach(id);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private static ArrayList<Integer> getListIDBudget(DBHelper dbHelper) {
        ArrayList<NganSach> all = dbHelper.getAll_NganSach();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            list.add(Integer.parseInt(all.get(i).getMaNganSach()));
        }
        return list;
    }
}
