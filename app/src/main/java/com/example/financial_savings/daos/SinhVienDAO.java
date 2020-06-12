package com.example.financial_savings.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financial_savings.entities.SinhVien;
import com.example.financial_savings.interfaces.ICRUD;

import java.util.ArrayList;

public class SinhVienDAO implements ICRUD<SinhVien> {

    private static final String TABLE_NAME = "SinhVien";

    private static final String ID = "masv";
    private static final String NAME = "ten";
    private static final String IMAGE = "hinhAnh";

    private static final String ACCOUNT = "email";

    @Override
    public boolean insert(SQLiteDatabase db, SinhVien sinhVien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, sinhVien.getMasv());
        contentValues.put(NAME, sinhVien.getTen());
        contentValues.put(IMAGE, sinhVien.getHinhAnh());
        contentValues.put(ACCOUNT, sinhVien.getEmail());
        if (db.insert(TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    @Override
    public SinhVien getByID(SQLiteDatabase db, String id) {
        String query = "select * from " + TABLE_NAME + " where " + ID + " like '%" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        SinhVien sinhVien = new SinhVien(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3));
        cursor.close();
        return sinhVien;
    }

    @Override
    public ArrayList<SinhVien> getAll(SQLiteDatabase db) {
        ArrayList<SinhVien> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new SinhVien(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean delete(SQLiteDatabase db, SinhVien sinhVien) {
        String query = "delete from " + TABLE_NAME + " where " + ID + " = " + sinhVien.getMasv();
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
    public boolean update(SQLiteDatabase db, SinhVien sinhVien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, sinhVien.getTen());
        contentValues.put(IMAGE, sinhVien.getHinhAnh());
        contentValues.put(ACCOUNT, sinhVien.getEmail());
        if (db.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{sinhVien.getMasv()}) > 0) {
            db.close();
            return true;
        }
        return false;
    }
}
