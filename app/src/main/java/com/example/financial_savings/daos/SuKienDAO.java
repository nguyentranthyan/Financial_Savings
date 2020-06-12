package com.example.financial_savings.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.interfaces.ICRUD;

import java.sql.Date;
import java.util.ArrayList;

public class SuKienDAO implements ICRUD<SuKien> {

    private static final String TABLE_NAME = "SuKien";

    private static final String ID = "maSuKien";
    private static final String NAME = "tenSuKien";
    private static final String DATE_START = "ngayBatDau";
    private static final String DATE_END = "ngayKetThuc";

    @Override
    public boolean insert(SQLiteDatabase db, SuKien suKien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, suKien.getMaSuKien());
        contentValues.put(NAME, suKien.getTenSuKien());
        contentValues.put(DATE_START, suKien.getNgayBatDau().toString());
        contentValues.put(DATE_END, suKien.getNgayKetThuc().toString());
        if (db.insert(TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    @Override
    public SuKien getByID(SQLiteDatabase db, String id) {
        String query = "select * from " + TABLE_NAME + " where " + ID + " like '%" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        SuKien suKien = new SuKien(cursor.getString(0), cursor.getString(1),
                Date.valueOf(cursor.getString(2)), Date.valueOf(cursor.getString(3)));
        cursor.close();
        return suKien;
    }

    public SuKien getByName(SQLiteDatabase db, String name) {
        String query = "select * from " + TABLE_NAME + " where " + NAME + " like '%" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        SuKien suKien = new SuKien(cursor.getString(0), cursor.getString(1),
                Date.valueOf(cursor.getString(2)), Date.valueOf(cursor.getString(3)));
        cursor.close();
        return suKien;
    }

    @Override
    public ArrayList<SuKien> getAll(SQLiteDatabase db) {
        ArrayList<SuKien> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new SuKien(cursor.getString(0), cursor.getString(1),
                    Date.valueOf(cursor.getString(2)), Date.valueOf(cursor.getString(3))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean delete(SQLiteDatabase db, SuKien suKien) {
        String query = "delete from " + TABLE_NAME + " where " + ID + " = " + suKien.getMaSuKien();
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
    public boolean update(SQLiteDatabase db, SuKien suKien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, suKien.getTenSuKien());
        contentValues.put(DATE_START, suKien.getNgayBatDau().toString());
        contentValues.put(DATE_END, suKien.getNgayKetThuc().toString());
        if (db.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{suKien.getMaSuKien()}) > 0) {
            db.close();
            return true;
        }
        return false;
    }
}
