package com.example.financial_savings.dbs;

public class SuKienDBS {

    private static final String TABLE_NAME = "SuKien";

    private static final String ID = "maSuKien";
    private static final String NAME = "tenSuKien";
    private static final String DATE_START = "ngayBatDau";
    private static final String DATE_END = "ngayKetThuc";

    public static String createTable(){
        return "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " TEXT PRIMARY KEY, " +
                NAME + " TEXT, " +
                DATE_START + " TEXT, " +
                DATE_END + " TEXT" +
                ")";
    }

    public static String deleteTable(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
