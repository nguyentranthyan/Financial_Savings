package com.example.financial_savings.entities;

public class TaiKhoan {
    private String email;
    private String matKhau;
    private int status;

    public TaiKhoan() {
    }

    public TaiKhoan(String email, String matKhau, int status) {
        this.email = email;
        this.matKhau = matKhau;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int code) {
        this.status = code;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "email='" + email + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", status=" + status +
                '}';
    }
}
