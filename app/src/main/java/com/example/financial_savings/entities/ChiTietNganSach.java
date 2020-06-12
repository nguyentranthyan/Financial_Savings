package com.example.financial_savings.entities;

public class ChiTietNganSach {
    private String maGiaoDich;
    private String maNganSach;

    public ChiTietNganSach() {
    }

    public ChiTietNganSach(String maGiaoDich, String maNganSach) {
        this.maGiaoDich = maGiaoDich;
        this.maNganSach = maNganSach;
    }

    public String getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getMaNganSach() {
        return maNganSach;
    }

    public void setMaNganSach(String maNganSach) {
        this.maNganSach = maNganSach;
    }

    @Override
    public String toString() {
        return "ChiTietNganSach{" +
                "maGiaoDich='" + maGiaoDich + '\'' +
                ", maNganSach='" + maNganSach + '\'' +
                '}';
    }
}
