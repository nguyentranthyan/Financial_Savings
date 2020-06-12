package com.example.financial_savings.modules.others;

import com.example.financial_savings.entities.SinhVien;
import com.example.financial_savings.entities.TaiKhoan;
import com.example.financial_savings.helper.DBHelper;

public class AccountCurrentModule {

    public static SinhVien getSinhVienCurrent(DBHelper dbHelper) {
        TaiKhoan taiKhoan = dbHelper.getByCode_TaiKhoan(1);
        return dbHelper.getByID_SinhVien(taiKhoan.getEmail());
    }
}
