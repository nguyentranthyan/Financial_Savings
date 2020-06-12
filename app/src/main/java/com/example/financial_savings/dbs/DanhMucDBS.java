package com.example.financial_savings.dbs;

public class DanhMucDBS {

    private static final String TABLE_NAME = "DanhMuc";

    private static final String ID = "maDanhMuc";
    private static final String NAME = "tenDanhMuc";
    private static final String ICON = "bieuTuong";
    private static final String CATEGORY = "loaiDanhMuc";

    private static final String WALLET = "maVi";

    public static String createTable(){
        return "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " TEXT PRIMARY KEY, " +
                NAME + " TEXT, " +
                ICON + " TEXT, " +
                CATEGORY + " TEXT, " +
                WALLET + " TEXT" +
                ")";
    }

    public static String deleteTable(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
