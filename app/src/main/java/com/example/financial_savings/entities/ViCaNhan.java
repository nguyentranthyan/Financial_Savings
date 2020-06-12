package com.example.financial_savings.entities;

public class ViCaNhan {
    private String maVi;
    private double soTien;
    private String tenVi;

    public ViCaNhan() {
    }

    public ViCaNhan(String maVi, double soTien, String tenVi) {
        this.maVi = maVi;
        this.soTien = soTien;
        this.tenVi = tenVi;
    }

    public String getMaVi() {
        return maVi;
    }

    public void setMaVi(String maVi) {
        this.maVi = maVi;
    }

    public String getTenVi() {
        return tenVi;
    }

    public void setTenVi(String tenVi) {
        this.tenVi = tenVi;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    @Override
    public String toString() {
        return "ViCaNhan{" +
                "maVi='" + maVi + '\'' +
                ", soTien=" + soTien +
                ", tenVi='" + tenVi + '\'' +
                '}';
    }

    public void rutTien(String money) {
        setSoTien(getSoTien() - Double.parseDouble(money));
    }

    public void napTien(String money) {
        setSoTien(getSoTien() + Double.parseDouble(money));
    }
}
