package com.example.financial_savings.dbs;

public class NganSachDBS {

    private static final String TABLE_NAME = "NganSach";

    private static final String ID = "maNganSach";
    private static final String MONEY = "soTien";
    private static final String DATE_START = "ngayBatDau";
    private static final String DATE_END = "ngayKetThuc";

    public static String createTable(){
        return "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " TEXT PRIMARY KEY, " +
                MONEY + " REAL, " +
                DATE_START + " TEXT, " +
                DATE_END + " TEXT" +
                ")";
    }

    public static String deleteTable(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
