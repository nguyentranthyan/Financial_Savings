package com.example.financial_savings.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financial_savings.entities.NganSach;
import com.example.financial_savings.interfaces.ICRUD;

import java.sql.Date;
import java.util.ArrayList;

public class NganSachDAO implements ICRUD<NganSach> {

    private static final String TABLE_NAME = "NganSach";

    private static final String ID = "maNganSach";
    private static final String MONEY = "soTien";
    private static final String DATE_START = "ngayBatDau";
    private static final String DATE_END = "ngayKetThuc";

    @Override
    public boolean insert(SQLiteDatabase db, NganSach nganSach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, nganSach.getMaNganSach());
        contentValues.put(MONEY, nganSach.getSoTien());
        contentValues.put(DATE_START, nganSach.getNgayBatDau().toString());
        contentValues.put(DATE_END, nganSach.getNgayKetThuc().toString());
        if (db.insert(TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    @Override
    public NganSach getByID(SQLiteDatabase db, String id) {
        String query = "select * from " + TABLE_NAME + " where " + ID + " like '%" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        NganSach nganSach = new NganSach(cursor.getString(0), cursor.getDouble(1),
                Date.valueOf(cursor.getString(2)), Date.valueOf(cursor.getString(3)));
        cursor.close();
        return nganSach;
    }

    @Override
    public ArrayList<NganSach> getAll(SQLiteDatabase db) {
        ArrayList<NganSach> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new NganSach(cursor.getString(0), cursor.getDouble(1),
                    Date.valueOf(cursor.getString(2)), Date.valueOf(cursor.getString(3))));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean delete(SQLiteDatabase db, NganSach nganSach) {
        String query = "delete from " + TABLE_NAME + " where " + ID + " = " + nganSach.getMaNganSach();
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
    public boolean update(SQLiteDatabase db, NganSach nganSach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MONEY, nganSach.getSoTien());
        contentValues.put(DATE_START, nganSach.getNgayBatDau().toString());
        contentValues.put(DATE_END, nganSach.getNgayKetThuc().toString());
        if (db.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{nganSach.getMaNganSach()}) > 0) {
            db.close();
            return true;
        }
        return false;
    }
}
