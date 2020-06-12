package com.example.financial_savings.dbs;

public class ViCaNhanDBS {

    private static final String TABLE_NAME = "ViCaNhan";

    private static final String ID = "maVi";
    private static final String MONEY = "soTien";
    private static final String NAME = "tenVi";

    public static String createTable(){
        return "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " TEXT PRIMARY KEY, " +
                MONEY + " REAL, " +
                NAME + " TEXT" +
                ")";
    }

    public static String deleteTable(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
