package com.example.financial_savings.entities;

import java.sql.Date;

public class NganSach {
    private String maNganSach;
    private double soTien;
    private Date ngayBatDau;
    private Date ngayKetThuc;

    public NganSach() {
    }

    public NganSach(String maNganSach, double soTien, Date ngayBatDau, Date ngayKetThuc) {
        this.maNganSach = maNganSach;
        this.soTien = soTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getMaNganSach() {
        return maNganSach;
    }

    public void setMaNganSach(String maNganSach) {
        this.maNganSach = maNganSach;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
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
        return "NganSach{" +
                "maNganSach='" + maNganSach + '\'' +
                ", soTien=" + soTien +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}
