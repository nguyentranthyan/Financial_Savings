package com.example.financial_savings.entities;

import java.sql.Date;

public class TietKiem {
    private String maTietKiem;
    private String tenTietKiem;
    private double soTien;
    private Date ngayBatDau;
    private Date ngayKetThuc;

    public TietKiem() {
    }

    public TietKiem(String maTietKiem, String tenTietKiem, double soTien, Date ngayBatDau, Date ngayKetThuc) {
        this.maTietKiem = maTietKiem;
        this.tenTietKiem = tenTietKiem;
        this.soTien = soTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getMaTietKiem() {
        return maTietKiem;
    }

    public void setMaTietKiem(String maTietKiem) {
        this.maTietKiem = maTietKiem;
    }

    public String getTenTietKiem() {
        return tenTietKiem;
    }

    public void setTenTietKiem(String tenTietKiem) {
        this.tenTietKiem = tenTietKiem;
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
        return "TietKiem{" +
                "maTietKiem='" + maTietKiem + '\'' +
                ", tenTietKiem='" + tenTietKiem + '\'' +
                ", soTien=" + soTien +
                ", ngayBatDau=" + ngayBatDau +
                ", ngayKetThuc=" + ngayKetThuc +
                '}';
    }
}
