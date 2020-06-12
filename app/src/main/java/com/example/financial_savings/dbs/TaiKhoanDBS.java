package com.example.financial_savings.dbs;

public class TaiKhoanDBS {

    private static final String TABLE_NAME = "TaiKhoan";

    private static final String ID = "email";
    private static final String PASSWORD = "matKhau";
    private static final String CODE = "code";

    public static String createTable(){
        return "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " TEXT PRIMARY KEY, " +
                PASSWORD + " TEXT, " +
                CODE + " INTEGER" +
                ")";
    }

    public static String deleteTable(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
