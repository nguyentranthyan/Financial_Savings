package com.example.financial_savings.dbs;

public class SinhVienDBS {

    private static final String TABLE_NAME = "SinhVien";

    private static final String ID = "masv";
    private static final String NAME = "ten";
    private static final String IMAGE = "hinhAnh";

    private static final String ACCOUNT = "email";

    private static final String TABLE_FK_1 = "TaiKhoan";

    private static final String CASCADE = "ON DELETE CASCADE ON UPDATE CASCADE";

    public static String createTable(){
        return "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " TEXT PRIMARY KEY, " +
                NAME + " TEXT, " +
                IMAGE + " TEXT, " +
                ACCOUNT + " TEXT CONSTRAINT " + ACCOUNT + " REFERENCES " + TABLE_FK_1 + "(" + ACCOUNT + ") " + CASCADE +
                ")";
    }

    public static String deleteTable(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
