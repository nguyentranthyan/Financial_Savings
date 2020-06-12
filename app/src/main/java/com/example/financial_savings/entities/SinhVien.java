package com.example.financial_savings.entities;

public class SinhVien {
    private String masv;
    private String ten;
    private String hinhAnh;

    private String email;

    public SinhVien() {
    }

    public SinhVien(String masv, String ten, String hinhAnh, String email) {
        this.masv = masv;
        this.ten = ten;
        this.hinhAnh = hinhAnh;
        this.email = email;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "masv='" + masv + '\'' +
                ", ten='" + ten + '\'' +
                ", hinhAnh='" + hinhAnh + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
