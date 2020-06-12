package com.example.financial_savings.entities;

import java.sql.Date;

public class SuKien {
    private String maSuKien;
    private String tenSuKien;
    private Date ngayBatDau;
    private Date ngayKetThuc;

    public SuKien() {
    }

    public SuKien(String maSuKien, String tenSuKien, Date ngayBatDau, Date ngayKetThuc) {
        this.maSuKien = maSuKien;
        this.tenSuKien = tenSuKien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getMaSuKien() {
        return maSuKien;
    }

    public void setMaSuKien(String maSuKien) {
        this.maSuKien = maSuKien;
    }

    public String getTenSuKien() {
        return tenSuKien;
    }

    public void setTenSuKien(String tenSuKien) {
        this.tenSuKien = tenSuKien;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    @Override
    public String toString() {
        return "SuKien{" +
                "maSuKien='" + maSuKien + '\'' +
                ", tenSuKien='" + tenSuKien + '\'' +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}
