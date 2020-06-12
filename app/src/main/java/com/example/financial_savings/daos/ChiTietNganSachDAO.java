package com.example.financial_savings.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financial_savings.entities.ChiTietNganSach;
import com.example.financial_savings.interfaces.ICRUD;

import java.util.ArrayList;

public class ChiTietNganSachDAO implements ICRUD<ChiTietNganSach> {

    private static final String TABLE_NAME = "ChiTietNganSach";

    private static final String TRANS = "maGiaoDich";
    private static final String BUDGET = "maNganSach";

    @Override
    public boolean insert(SQLiteDatabase db, ChiTietNganSach chiTietNganSach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANS, chiTietNganSach.getMaGiaoDich());
        contentValues.put(BUDGET, chiTietNganSach.getMaNganSach());
        if (db.insert(TABLE_NAME, null, contentValues) > 0) {
            db.close();
            return true;
        }
        return false;
    }

    @Override
    public ChiTietNganSach getByID(SQLiteDatabase db, String id) {
        String query = "select * from " + TABLE_NAME + " where " + BUDGET + " like '%" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        ChiTietNganSach chiTietNganSach = new ChiTietNganSach(cursor.getString(0), cursor.getString(1));
        cursor.close();
        return chiTietNganSach;
    }

    public ArrayList<ChiTietNganSach> getByIDBudget(SQLiteDatabase db, String idBudget) {
        ArrayList<ChiTietNganSach> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + BUDGET + " like '%" + idBudget + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new ChiTietNganSach(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<ChiTietNganSach> getByIDTrans(SQLiteDatabase db, String idTrans) {
        ArrayList<ChiTietNganSach> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME + " where " + TRANS + " like '%" + idTrans + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new ChiTietNganSach(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public ArrayList<ChiTietNganSach> getAll(SQLiteDatabase db) {
        ArrayList<ChiTietNganSach> list = new ArrayList<>();
        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new ChiTietNganSach(cursor.getString(0), cursor.getString(1)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public boolean delete(SQLiteDatabase db, ChiTietNganSach chiTietNganSach) {
        String query = "delete from " + TABLE_NAME + " where " + BUDGET + " = " + chiTietNganSach.getMaNganSach();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor == null)
            return false;
        else {
            cursor.moveToFirst();
            cursor.close();
        }
        return true;
    }

    @Override
    public boolean update(SQLiteDatabase db, ChiTietNganSach chiTietNganSach) {
        return false;
    }
}
