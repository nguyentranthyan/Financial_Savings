package com.example.financial_savings.dbs;

public class SoGiaoDichDBS {

    private static final String TABLE_NAME = "SoGiaoDich";

    private static final String ID = "maGiaoDich";
    private static final String MONEY = "soTien";
    private static final String NOTE = "ghiChu";
    private static final String DATE = "ngayGiaoDich";

    private static final String STUDENT = "masv";
    private static final String EVENT = "maSuKien";
    private static final String CATEGORY = "maDanhMuc";
    private static final String SAVING = "maTietKiem";
    private static final String WALLET = "maVi";

    private static final String STATUS = "status";

    private static final String TABLE_FK_1 = "SinhVien";
    private static final String TABLE_FK_2 = "DanhMuc";

    private static final String CASCADE = "ON DELETE CASCADE ON UPDATE CASCADE";

    public static String createTable(){
        return "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " TEXT PRIMARY KEY, " +
                MONEY + " REAL, " +
                NOTE + " TEXT, " +
                DATE + " TEXT, " +
                STUDENT + " TEXT CONSTRAINT " + STUDENT + " REFERENCES " + TABLE_FK_1 + "(" + STUDENT + ") " + CASCADE + ", " +
                EVENT + " TEXT, " +
                CATEGORY + " TEXT CONSTRAINT " + CATEGORY + " REFERENCES " + TABLE_FK_2 + "(" + CATEGORY + ") " + CASCADE + ", " +
                SAVING + " TEXT, " +
                WALLET + " TEXT, " +
                STATUS + " INTEGER" +
                ")";
    }

    public static String deleteTable(){
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
