package com.example.financial_savings.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financial_savings.entities.SoGiaoDich;
import com.example.financial_savings.interfaces.ICRUD;

import java.sql.Date;
import java.util.ArrayList;

public class SoGiaoDichDAO implements ICRUD<SoGiaoDich> {

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

    @Override
    public boolean insert(SQLiteDatabase db, SoGiaoDich soGiaoDich) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, soGiaoDich.getMaGiaoDich());
        contentValues.put(MONEY, soGiaoDich.getSoTien());
        contentValues.put(NOTE, soGiaoDich.getGhiChu());
        contentValues.put(DATE, soGiaoDich.getNgayGiaoDich().toString());
        contentValues.put(STUDENT, soGiaoDich.getMasv());
        contentValues.put(EVENT, soGiaoDich.getMaSuKien());
        contentValues.put(CATEGORY, soGiaoDich.getMaDanhMuc());
        contentValues.put(SAVING, soGiaoDich.getMaTietKiem());
        contentValues.put(WALLET, soGiaoDich.getMaVi());
        contentValues.put(STATUS, soGiaoDich.getStatus());
        if (db.insert(TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    @Override
    public SoGiaoDich getByID(SQLiteDatabase db, String id) {
        String query = "select * from " + TABLE_NAME + " where " + ID + " like '%" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        SoGiaoDich soGiaoDich = new SoGiaoDich(cursor.getString(0), cursor.getDouble(1),
                cursor.getString(2), Date.valueOf(cursor.getString(3)),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8), cursor.getInt(9));
        cursor.close();
        return soGiaoDich;
    }

    public ArrayList<SoGiaoDich> getByEvent(SQLiteDatabase db, String idEvent) {
        ArrayList<SoGiaoDich> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + EVENT + " like '%" + idEvent + "' order by " + ID + " desc";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new SoGiaoDich(cursor.getString(0), cursor.getDouble(1),
                    cursor.getString(2), Date.valueOf(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<SoGiaoDich> getBySavings(SQLiteDatabase db, String idSavings) {
        ArrayList<SoGiaoDich> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + SAVING + " like '%" + idSavings + "' order by " + ID + " desc";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new SoGiaoDich(cursor.getString(0), cursor.getDouble(1),
                    cursor.getString(2), Date.valueOf(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<SoGiaoDich> getByWallet(SQLiteDatabase db, String idWallet) {
        ArrayList<SoGiaoDich> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + WALLET + " like '%" + idWallet + "' order by " + ID + " desc";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new SoGiaoDich(cursor.getString(0), cursor.getDouble(1),
                    cursor.getString(2), Date.valueOf(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<SoGiaoDich> getByStatus(SQLiteDatabase db, int status) {
        ArrayList<SoGiaoDich> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + STATUS + " = " + status + " order by " + ID + " desc";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new SoGiaoDich(cursor.getString(0), cursor.getDouble(1),
                    cursor.getString(2), Date.valueOf(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public ArrayList<SoGiaoDich> getAll(SQLiteDatabase db) {
        ArrayList<SoGiaoDich> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " order by " + ID + " desc";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new SoGiaoDich(cursor.getString(0), cursor.getDouble(1),
                    cursor.getString(2), Date.valueOf(cursor.getString(3)),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    cursor.getString(7), cursor.getString(8), cursor.getInt(9)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean delete(SQLiteDatabase db, SoGiaoDich soGiaoDich) {
        String query = "delete from " + TABLE_NAME + " where " + ID + " = " + soGiaoDich.getMaGiaoDich();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null)
            return false;
        else {
            cursor.moveToFirst();
            cursor.close();
        }
        return false;
    }

    @Override
    public boolean update(SQLiteDatabase db, SoGiaoDich soGiaoDich) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MONEY, soGiaoDich.getSoTien());
        contentValues.put(NOTE, soGiaoDich.getGhiChu());
        contentValues.put(DATE, soGiaoDich.getNgayGiaoDich().toString());
        contentValues.put(STUDENT, soGiaoDich.getMasv());
        contentValues.put(EVENT, soGiaoDich.getMaSuKien());
        contentValues.put(CATEGORY, soGiaoDich.getMaDanhMuc());
        contentValues.put(SAVING, soGiaoDich.getMaTietKiem());
        contentValues.put(WALLET, soGiaoDich.getMaVi());
        contentValues.put(STATUS, soGiaoDich.getStatus());
        if (db.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{soGiaoDich.getMaGiaoDich()}) > 0) {
            db.close();
            return true;
        }
        return false;
    }
}
