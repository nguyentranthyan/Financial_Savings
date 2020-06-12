package com.example.financial_savings.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.interfaces.ICRUD;

import java.sql.Date;
import java.util.ArrayList;

public class TietKiemDAO implements ICRUD<TietKiem> {

    private static final String TABLE_NAME = "TietKiem";

    private static final String ID = "maTietKiem";
    private static final String NAME = "tenTietKiem";
    private static final String MONEY = "soTien";
    private static final String DATE_START = "ngayBatDau";
    private static final String DATE_END = "ngayKetThuc";

    @Override
    public boolean insert(SQLiteDatabase db, TietKiem tietKiem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, tietKiem.getMaTietKiem());
        contentValues.put(NAME, tietKiem.getTenTietKiem());
        contentValues.put(MONEY, tietKiem.getSoTien());
        contentValues.put(DATE_START, tietKiem.getNgayBatDau().toString());
        contentValues.put(DATE_END, tietKiem.getNgayKetThuc().toString());
        if (db.insert(TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    @Override
    public TietKiem getByID(SQLiteDatabase db, String id) {
        String query = "select * from " + TABLE_NAME + " where " + ID + " like '%" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        TietKiem tietKiem = new TietKiem(cursor.getString(0), cursor.getString(1),
                cursor.getDouble(2), Date.valueOf(cursor.getString(3)),
                Date.valueOf(cursor.getString(4)));
        cursor.close();
        return tietKiem;
    }

    public TietKiem getByName(SQLiteDatabase db, String name) {
        String query = "select * from " + TABLE_NAME + " where " + NAME + " like '%" + name + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        TietKiem tietKiem = new TietKiem(cursor.getString(0), cursor.getString(1),
                cursor.getDouble(2), Date.valueOf(cursor.getString(3)),
                Date.valueOf(cursor.getString(4)));
        cursor.close();
        return tietKiem;
    }

    @Override
    public ArrayList<TietKiem> getAll(SQLiteDatabase db) {
        ArrayList<TietKiem> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new TietKiem(cursor.getString(0), cursor.getString(1),
                    cursor.getDouble(2), Date.valueOf(cursor.getString(3)),
                    Date.valueOf(cursor.getString(4))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean delete(SQLiteDatabase db, TietKiem tietKiem) {
        String query = "delete from " + TABLE_NAME + " where " + ID + " = " + tietKiem.getMaTietKiem();
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
    public boolean update(SQLiteDatabase db, TietKiem tietKiem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, tietKiem.getTenTietKiem());
        contentValues.put(MONEY, tietKiem.getSoTien());
        contentValues.put(DATE_START, tietKiem.getNgayBatDau().toString());
        contentValues.put(DATE_END, tietKiem.getNgayKetThuc().toString());
        if (db.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{tietKiem.getMaTietKiem()}) > 0) {
            db.close();
            return true;
        }
        return false;
    }
}
